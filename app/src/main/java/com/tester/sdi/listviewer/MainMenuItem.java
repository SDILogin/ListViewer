package com.tester.sdi.listviewer;

import android.graphics.Bitmap;

/**
 * Created by sdi on 07.09.14.
 */
class MainMenuItem{
    private String name;
    private int count;
    private Bitmap pic;
    private String bmpFileName;
    private String bmpSource;


    public String getPathToBmp(){return bmpFileName;}
    public Bitmap getPic(){return pic;}
    public String getName(){return name;}
    public int getCount(){return count;}
    public String getBmpSource(){return bmpSource;}

    public void setBmpSource(String url){this.bmpSource = url;}
    public void setPathToBmp(String path){ bmpFileName = path;}
    public void setPic(Bitmap bmp){this.pic = bmp;}
    public void setName(String name){this.name = name;}
    public void setCount(int count){this.count = count;}

    public MainMenuItem(String name, int count, Bitmap bmp){
        this.name  = name;
        this.count = count;
        this.pic   = bmp;
    }

    public MainMenuItem(String name, int count, String bmpFileName){
        this.name = name;
        this.count = count;
        this.bmpFileName = bmpFileName;
    }

    public MainMenuItem(String name, int count, String url, String bmpFileName){
        this.name = name;
        this.count = count;
        this.bmpFileName = bmpFileName;
        this.bmpSource = url;
    }
}
