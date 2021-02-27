package com.Kinghao.bean;

import com.fasterxml.jackson.annotation.JsonProperty;


import org.springframework.boot.autoconfigure.domain.EntityScan;

import javax.persistence.*;

/**
 * 用户信息
 */


public class User {

    private Long id;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
