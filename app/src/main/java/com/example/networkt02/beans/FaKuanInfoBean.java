package com.example.networkt02.beans;

public class FaKuanInfoBean {


    /**
     * date : 13/8/2019 09:01:45
     * address : 潍莱高速
     * kouFen : 2
     * chuLi : 未处理
     * faKuan : 100
     * carId : 鲁B10001
     * info : 超速
     */

    private String date;
    private String address;
    private int kouFen;
    private String chuLi;
    private int faKuan;
    private String carId;
    private String info;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getKouFen() {
        return kouFen;
    }

    public void setKouFen(int kouFen) {
        this.kouFen = kouFen;
    }

    public String getChuLi() {
        return chuLi;
    }

    public void setChuLi(String chuLi) {
        this.chuLi = chuLi;
    }

    public int getFaKuan() {
        return faKuan;
    }

    public void setFaKuan(int faKuan) {
        this.faKuan = faKuan;
    }

    public String getCarId() {
        return carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    @Override
    public String toString() {
        return "FaKuanInfoBean{" +
                "date='" + date + '\'' +
                ", address='" + address + '\'' +
                ", kouFen=" + kouFen +
                ", chuLi='" + chuLi + '\'' +
                ", faKuan=" + faKuan +
                ", carId='" + carId + '\'' +
                ", info='" + info + '\'' +
                '}';
    }
}
