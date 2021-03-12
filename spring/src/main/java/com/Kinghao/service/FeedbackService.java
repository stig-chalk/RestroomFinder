package com.Kinghao.service;


import com.Kinghao.bean.Place;
import com.Kinghao.mapper.PlaceMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class FeedbackService {
    @Autowired
    private PlaceMapper placeMapper;

    public void addFeedback(Place feedback) {
        Place place = placeMapper.findPlaceById(feedback.getId());
        System.out.println(place);
        if (place == null) {
            feedback.setUserTotalRatings(1);
            placeMapper.insertRecord(feedback);
        }
        else {
            updateRatings(place, feedback);
            placeMapper.updateRecord(place);
        }
    }

    private void updateRatings(Place place, Place feedback) {
        int totalNum = place.getUserTotalRatings();
        place.setRating(getNewRate(place.getRating(), feedback.getRating(), totalNum));
        place.setClean(getNewRate(place.getClean(), feedback.getClean(), totalNum));
        place.setBusy(getNewRate(place.getBusy(), feedback.getBusy(), totalNum));
        place.setAccessTlt(feedback.isAccessTlt());
        place.setGenInclus(feedback.isGenInclus());
        place.setPaper(feedback.isPaper());
        place.setSoap(feedback.isSoap());
        place.setUserTotalRatings(totalNum+1);
    }

    private double getNewRate(double r1, double r2, int totalNum) {
        return r1 * totalNum / (totalNum+1) + r2 / (totalNum+1);
    }
}
