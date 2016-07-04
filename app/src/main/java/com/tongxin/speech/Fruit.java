package com.tongxin.speech;

public class Fruit {
    private String name;
    private int imageId;
    public Fruit(int imageId,String name){

        this.imageId=imageId;
        this.name=name;
    }

    public int getImageId(){
        return imageId;
    }


    public String getName(){
        return name;
    }}
