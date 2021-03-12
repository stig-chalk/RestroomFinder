package com.Kinghao.controller;

import com.Kinghao.bean.Place;
import com.Kinghao.bean.Result;
import com.Kinghao.bean.User;
import com.Kinghao.service.FeedbackService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Api(tags = "Feedback Controller")
@Controller
@RequestMapping("/feedback")
public class FeedbackController {

    @Autowired
    private FeedbackService feedbackService;

    @PostMapping("/add")
    @ApiOperation(value="Add a feedback to a restroom")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "id",value = "Place id",required = true),
            @ApiImplicitParam(name = "busy",value = "Busy",required = true),
            @ApiImplicitParam(name = "clean",value = "Clean",required = true),
            @ApiImplicitParam(name = "accessTlt",value = "accessTlt",required = true),
            @ApiImplicitParam(name = "genInclus",value = "genInclus",required = true),
            @ApiImplicitParam(name = "paper",value = "Paper",required = true),
            @ApiImplicitParam(name = "soap",value = "Soap",required = true),
            @ApiImplicitParam(name = "rating",value = "Soap",required = true)
    })
    @ResponseBody
    public Result addFeedback(Place place, HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute("user");

        if (user != null) {
            feedbackService.addFeedback(place);
            result.setSuccess(true);
            result.setMsg("Successfully added one feedback for restroom: " + place.getId());
        }
        else {
            result.setSuccess(false);
            result.setMsg("Permission denied, please login before adding feedback");
        }
        return result;
    }
}
