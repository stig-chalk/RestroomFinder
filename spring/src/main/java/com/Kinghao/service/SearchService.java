package com.Kinghao.service;

import com.Kinghao.bean.Place;
import com.Kinghao.bean.User;
import com.Kinghao.mapper.PlaceMapper;
import com.google.maps.GeoApiContext;
import com.google.maps.TextSearchRequest;
import com.google.maps.model.LatLng;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class SearchService {
    @Autowired
    private PlaceMapper placeMapper;

    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    public ArrayList<Place> apiSearch(double lat, double lon, int radius) {
        ArrayList<Place> placeList = new ArrayList<Place>();

//        google maps api setup
        GeoApiContext context =  new GeoApiContext.Builder()
                .apiKey("")
                .build();

        TextSearchRequest apiRequest = new TextSearchRequest(context);
        apiRequest.openNow(true);
        apiRequest.location(new com.google.maps.model.LatLng(lat, lon));
        apiRequest.radius(radius);
        apiRequest.custom("strictBounds", "true");
        PlacesSearchResponse searchRes;
        apiRequest.query("fastfood");
        searchRes = apiRequest.awaitIgnoreError();
        cleanUpResults(placeList, searchRes.results, "Fast Food");

        apiRequest = new TextSearchRequest(context);
        apiRequest.openNow(true);
        apiRequest.location(new com.google.maps.model.LatLng(lat, lon));
        apiRequest.radius(radius);
        apiRequest.custom("strictBounds", "true");
        apiRequest.query("restroom");
        searchRes = apiRequest.awaitIgnoreError();
        cleanUpResults(placeList, searchRes.results, "Public Restroom");

        apiRequest = new TextSearchRequest(context);
        apiRequest.openNow(true);
        apiRequest.location(new com.google.maps.model.LatLng(lat, lon));
        apiRequest.radius(radius);
        apiRequest.custom("strictBounds", "true");
        apiRequest.query("supermarket");
        searchRes = apiRequest.awaitIgnoreError();
        cleanUpResults(placeList, searchRes.results, "Supermarket");

        return placeList;
    }

    private void cleanUpResults(ArrayList<Place> placeList, PlacesSearchResult[] searchRes, String type) {
        for (PlacesSearchResult r : searchRes) {
            String placeId = r.placeId;
            Place p = placeMapper.findPlaceById(placeId);
            if (p == null) {
                p = new Place();
                p.setId(placeId);
            }
//            if (r.photos.length > 0)
//                p.setImgUrl(r.photos[0].photoReference);
            p.setName(r.name);
            p.setAddr(r.formattedAddress);
            LatLng latLng = r.geometry.location;
            p.setLat(latLng.lat);
            p.setLon(latLng.lng);
            p.setType(type);
            if (p.getUserTotalRatings() == 0) {
                p.setRating(r.rating);
                p.setUserTotalRatings(r.userRatingsTotal);
            }
            placeList.add(p);
        }
    }

    public void sortByDistance(ArrayList<Place> places, double lat, double lon) {
        places.sort((p1, p2) -> {
            double d1 = p1.calculateDistance(lat, lon);
            double d2 = p2.calculateDistance(lat, lon);
            return Double.compare(d1, d2);
        });
    }

    public void sortByPrefer(ArrayList<Place> places, User user, double lat, double lon, int radius) {
        places.sort((p1, p2) -> {
            Double score1 = getScore(p1, user, lat, lon, radius);
            Double score2 = getScore(p2, user, lat, lon, radius);
            return Double.compare(score1, score2);
        });
    }

    public Double getScore(Place p, User user, double lat, double lon, int radius) {
        int totalWeight = user.totalWeight();
        if (totalWeight == 0) totalWeight = 16;
        // set distance's and rating's weight to be 10% each
        double drWeight = totalWeight / 8.0;
        double distance = p.calculateDistance(lat, lon);

        return -(drWeight * ((radius - distance) / radius) +
                drWeight * (p.getRating() / 5.0) +
                user.getBusy() * ((5 - p.getBusy()) / 5.0) +
                user.getClean() * (p.getClean() / 5.0) +
                user.getGenInclus() * (p.isGenInclus() ? 1 : 0) +
                user.getPaper() * (p.isPaper() ? 1 : 0) +
                user.getAccessTlt() * (p.isAccessTlt() ? 1 : 0) +
                user.getSoap() * (p.isSoap() ? 1 : 0));
    }
}
