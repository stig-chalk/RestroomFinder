package com.Kinghao.controller;

import com.Kinghao.bean.User;
import com.Kinghao.bean.Result;
import com.Kinghao.mapper.UserMapper;
import com.Kinghao.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.apache.ibatis.annotations.Param;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Api(tags = "User Controller")
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserMapper userMapper;

    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);

    /**
     * Register
     * @param user
     * @return Result
     */
    @PostMapping(value = "/regist")
    @ApiOperation(value="Add a user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email",value = "Username--Length up to 255",required = true),
            @ApiImplicitParam(name = "password",value = "Password--Length up to 255",required = true),

    })

    @ResponseBody
    public Result regist(User user){
        logger.trace("regist was request");
        return userService.regist(user);
    }

    /**
     * Login
     * @param email
     * @param password
     * @return Result
     */
    @PostMapping(value = "/login")
    @ApiOperation("Check the login information of the user")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "email",value = "Email",required = true),
            @ApiImplicitParam(name = "password",value = "Password",required = true)
    })
    @ResponseBody
    public Result login(
            @Param("email") String email,
            @Param("password") String password,
            HttpServletRequest request){
        logger.trace("login was request");
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);
        Result R1=userService.login(user, request);

        return R1;
    }
    @PostMapping("/logout")
    public String logout(HttpServletRequest request){
        HttpSession session=request.getSession();
        session.invalidate();
        return "logout";
    }

    @PostMapping("/setPrefer")
    @ApiOperation("Update user preference setting")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "busy",value = "Busy",required = true),
            @ApiImplicitParam(name = "clean",value = "Clean",required = true),
            @ApiImplicitParam(name = "accessTlt",value = "accessTlt",required = true),
            @ApiImplicitParam(name = "genInclus",value = "genInclus",required = true),
            @ApiImplicitParam(name = "paper",value = "Paper",required = true),
            @ApiImplicitParam(name = "soap",value = "Soap",required = true)
    })
    @ResponseBody
    public Result setPrefer(User updatedPref, HttpServletRequest request) {
        Result result = new Result();
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            userService.setPrefer(user, updatedPref);

            request.getSession().setAttribute("user", user);
            System.out.println(user);

            result.setSuccess(true);
            result.setMsg("Preference updated for user: " + user.getEmail());
        }
        else {
            result.setSuccess(false);
            result.setMsg("Permission denied, please login before setting preferences");
        }
        return result;
    }


}
/**
 * Got api:
 * mvn swagger2markup:convertSwagger2markup
 * mvn generate-resources
 */
