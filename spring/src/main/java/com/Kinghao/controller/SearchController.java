package com.Kinghao.controller;

import com.Kinghao.bean.Place;
import com.Kinghao.bean.User;
import com.Kinghao.service.SearchService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.maps.FindPlaceFromTextRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.PendingResult;
import com.Kinghao.bean.Result;
import com.google.maps.TextSearchRequest;
import com.google.maps.errors.ApiException;
import com.google.maps.model.FindPlaceFromText;
import com.google.maps.model.PlacesSearchResponse;
import com.google.maps.model.PlacesSearchResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;

@Api(tags = "Search Controller")
@Controller
@RequestMapping("/search")
public class SearchController {
    @Autowired
    private SearchService searchService;
    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);


    @GetMapping(value = "/weighted")
    @ApiOperation(value="Search for restrooms under regular mode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lat", value = "Latitude",required = true),
            @ApiImplicitParam(name = "lon", value = "Longitude",required = true),
            @ApiImplicitParam(name = "radius", value = "Radius",required = true),
    })
    @ResponseBody
    public Result searchWeighted(@RequestParam("lat") double lat,
                                 @RequestParam("lon") double lon,
                                 @RequestParam("radius") int radius,
                                 HttpServletRequest request) {
        Result result = new Result();
        User user = (User)request.getSession().getAttribute("user");

        if (user != null) {
            ArrayList<Place> apiResults = searchService.apiSearch(lat, lon, radius);
            searchService.sortByPrefer(apiResults, user, lat, lon, radius);

            for (Place p : apiResults) {
                System.out.println(p.getId());
                System.out.println(p.getName());
                System.out.println(searchService.getScore(p, user, lat, lon, radius));
                System.out.println(p.calculateDistance(lat, lon));
                System.out.println(p.getRating());
            }
            result.setSuccess(true);
            result.setDetail(apiResults);
        }
        else {
            result.setSuccess(false);
            result.setMsg("Permission denied, please login before requesting the recommendation list");
        }
        return result;
    }

    @GetMapping(value = "/unweighted")
    @ApiOperation(value="Search for restrooms under emergency mode")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "lat", value = "Latitude",required = true),
            @ApiImplicitParam(name = "lon", value = "Longitude",required = true),
    })
    @ResponseBody
    public Result searchUnweighted(
                        @RequestParam("lat") double lat,
                        @RequestParam("lon") double lon) {
        Result result = new Result();
        ArrayList<Place> apiResults = searchService.apiSearch(lat, lon, 2000);
        searchService.sortByDistance(apiResults, lat, lon);
        result.setSuccess(true);
        result.setDetail(apiResults);
        return result;
    }

}
