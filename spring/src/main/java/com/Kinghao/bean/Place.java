package com.Kinghao.bean;

public class Place {
    private String id;
    private double busy, clean, rating;
    private boolean accessTlt, genInclus, soap, paper;
    private int userTotalRatings;

    private String addr, imgUrl, name, type;
    private double lat, lon;


    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setAccessTlt(boolean accessTlt) {
        this.accessTlt = accessTlt;
    }

    public boolean isAccessTlt() { return accessTlt; }

    public void setBusy(double busy) {
        this.busy = busy;
    }

    public double getBusy() {
        return busy;
    }

    public void setClean(double clean) {
        this.clean = clean;
    }

    public double getClean() {
        return clean;
    }


    public boolean isGenInclus() {
        return genInclus;
    }

    public void setGenInclus(boolean genInclus) {
        this.genInclus = genInclus;
    }

    public boolean isSoap() {
        return soap;
    }

    public void setSoap(boolean soap) {
        this.soap = soap;
    }

    public boolean isPaper() {
        return paper;
    }

    public void setPaper(boolean paper) {
        this.paper = paper;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id='" + id + '\'' +
                ", busy=" + busy +
                ", clean=" + clean +
                ", accessTlt=" + accessTlt +
                ", genInclus=" + genInclus +
                ", soap=" + soap +
                ", paper=" + paper +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setAddr(String addr) {
        this.addr = addr;
    }

    public String getAddr() {
        return addr;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public double calculateDistance(double lat2, double lon2) {
        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat);
        double lonDistance = Math.toRadians(lon2 - lon);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c * 1000; // convert to meters
    }

    public int getUserTotalRatings() {
        return userTotalRatings;
    }

    public void setUserTotalRatings(int userTotalRatings) {
        this.userTotalRatings = userTotalRatings;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public double getRating() {
        return rating;
    }
}
