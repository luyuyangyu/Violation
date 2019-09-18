package com.example.networkt02.beans;

public class FindInfoBean {

    /**
     * deduction : 4
     * car_number : 鲁B10001
     * dispose_number : 2
     * amerce : 300
     */

    private int deduction;
    private String car_number;
    private int dispose_number;
    private int amerce;

    public int getDeduction() {
        return deduction;
    }

    public void setDeduction(int deduction) {
        this.deduction = deduction;
    }

    public String getCar_number() {
        return car_number;
    }

    public void setCar_number(String car_number) {
        this.car_number = car_number;
    }

    public int getDispose_number() {
        return dispose_number;
    }

    public void setDispose_number(int dispose_number) {
        this.dispose_number = dispose_number;
    }

    public int getAmerce() {
        return amerce;
    }

    public void setAmerce(int amerce) {
        this.amerce = amerce;
    }

    @Override
    public String toString() {
        return "FindInfoBean{" +
                "deduction=" + deduction +
                ", car_number='" + car_number + '\'' +
                ", dispose_number=" + dispose_number +
                ", amerce=" + amerce +
                '}';
    }
}
