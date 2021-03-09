package com.Kinghao.service;

import com.Kinghao.bean.User;
import com.Kinghao.bean.Result;
import com.Kinghao.mapper.UserMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jasypt.util.password.PasswordEncryptor;
import org.jasypt.util.password.StrongPasswordEncryptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Service
@Transactional(rollbackFor = RuntimeException.class)
public class UserService {

    @Autowired
    private UserMapper userMapper;

    private static Logger logger= LogManager.getLogger(LogManager.ROOT_LOGGER_NAME);
    /**
     * Register
     * @param user
     * @return Result
     */
    public Result regist(User user) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User existUser = userMapper.findUserByEmail(user.getEmail());
            if(existUser != null){
                //username has existed
                result.setMsg("The email has been used.");
                logger.trace("For email:"+existUser.getEmail()+";"+result.getMsg());
            }
            else{
                PasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
                user.setPassword(passwordEncryptor.encryptPassword(user.getPassword()));
                userMapper.regist(user);
                userMapper.insertPrefRecord(user);
                System.out.println(user.getId());
                result.setMsg("Register successfully.");
                logger.trace("For email:"+user.getEmail()+";"+result.getMsg());
                result.setSuccess(true);
                result.setDetail(user);
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }
    /**
     * Login
     * @param user
     * @return Result
     */
    public Result login(User user, HttpServletRequest request) {
        Result result = new Result();
        result.setSuccess(false);
        result.setDetail(null);
        try {
            User fullUserInfo = userMapper.login(user);
            Boolean passMatch = new StrongPasswordEncryptor().checkPassword(user.getPassword(), fullUserInfo.getPassword());
            if(!passMatch){
                result.setMsg("Wrong username or password.");
                logger.trace(user.getEmail()+" login failed: "+result.getMsg());
            }else{
                result.setMsg("Login successfully");
                logger.trace(fullUserInfo.getEmail()+" login success: "+result.getMsg());
                user = userMapper.getUserPref(fullUserInfo);
                user.setEmail(fullUserInfo.getEmail());

                result.setSuccess(true);
                result.setDetail(user);
                HttpSession session=request.getSession();
                session.setAttribute("user",user);
                result.setMsg(user.getEmail()+", Hello!");
            }
        } catch (Exception e) {
            result.setMsg(e.getMessage());
            e.printStackTrace();
        }
        return result;
    }


    public void setPrefer(User user, User updatedPref) {
        user.setAccessTlt(updatedPref.getAccessTlt());
        user.setBusy(updatedPref.getBusy());
        user.setClean(updatedPref.getClean());
        user.setGenInclus(updatedPref.getGenInclus());
        user.setPaper(updatedPref.getPaper());
        user.setSoap(updatedPref.getSoap());
        userMapper.updatePrefRecord(user);
    }
}
