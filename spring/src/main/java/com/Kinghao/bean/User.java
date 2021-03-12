package com.Kinghao.bean;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * 用户信息
 */


public class User {

    private Long id;

    private String email;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;

    private int busy, clean, accessTlt, paper, soap, genInclus;


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

    public void setClean(Integer clean) {
        this.clean = clean;
    }

    public Integer getClean() {
        return clean;
    }

    public void setBusy(Integer busy) {
        this.busy = busy;
    }

    public Integer getBusy() {
        return busy;
    }

    public Integer getAccessTlt() {
        return accessTlt;
    }

    public void setAccessTlt(Integer accessTlt) {
        this.accessTlt = accessTlt;
    }

    public Integer getPaper() {
        return paper;
    }

    public void setPaper(Integer paper) {
        this.paper = paper;
    }

    public Integer getSoap() {
        return soap;
    }

    public void setSoap(Integer soap) {
        this.soap = soap;
    }

    public Integer getGenInclus() {
        return genInclus;
    }

    public void setGenInclus(Integer genInclus) {
        this.genInclus = genInclus;
    }

    public int totalWeight() {
        return soap+clean+busy+accessTlt+genInclus+paper;
    }
}
