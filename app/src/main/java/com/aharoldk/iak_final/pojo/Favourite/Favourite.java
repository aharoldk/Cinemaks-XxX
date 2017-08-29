package com.aharoldk.iak_final.pojo.Favourite;

public class Favourite {
    private String backGroundPath, date, idDetail, language, overview, posterImage, titleDetail;
    private Double rate;

    public Favourite() {
    }

    public Favourite(String backGroundPath, String date, String idDetail, String language, String overview, String posterImage, String titleDetail, Double rate) {
        this.backGroundPath = backGroundPath;
        this.date = date;
        this.idDetail = idDetail;
        this.language = language;
        this.overview = overview;
        this.posterImage = posterImage;
        this.titleDetail = titleDetail;
        this.rate = rate;
    }

    public String getBackGroundPath() {
        return backGroundPath;
    }

    public void setBackGroungPath(String backGroungPath) {
        this.backGroundPath = backGroungPath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getIdDetail() {
        return idDetail;
    }

    public void setIdDetail(String idDetail) {
        this.idDetail = idDetail;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getOverview() {
        return overview;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public String getPosterImage() {
        return posterImage;
    }

    public void setPosterImage(String posterImage) {
        this.posterImage = posterImage;
    }

    public String getTitleDetail() {
        return titleDetail;
    }

    public void setTitleDetail(String titleDetail) {
        this.titleDetail = titleDetail;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }
}
