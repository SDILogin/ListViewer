package com.tester.sdi.listviewer;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdi on 06.09.14.
 */
public class JSONParser {
    public static boolean isRefreshFinished = false;
    public static boolean isFirstRun = true;

    private List<String> mainMenuNames = new ArrayList<String>();
    private List<String> mainMenuPicSource = new ArrayList<String>();
    private List<Integer> mainMenuItemSize = new ArrayList<Integer>();
    private List<Bitmap> mainMenuBitmaps = new ArrayList<Bitmap>();

    private List<ArrayList<String>> subMenuNames = new ArrayList<ArrayList<String>>();
    private List<ArrayList<Float>> subMenuPrices = new ArrayList<ArrayList<Float>>();
    private List<ArrayList<String>> subMenuChunks = new ArrayList<ArrayList<String>>();
    private List<ArrayList<Bitmap>> subMenuBitmaps = new ArrayList<ArrayList<Bitmap>>();
    private List<ArrayList<String>> subMenuPicSources = new ArrayList<ArrayList<String>>();

    private String toParse = "{'menu':{'count':2,'menuitems':["+
            "{'name':'pizza','src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg',"+
                " 'count':3,'submenu':[{'name':'italia', 'price':55, 'chunk':'chunk', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'},"+
                                      "{'name':'prima', 'price':54, 'chunk':'full', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'},"+
                                      "{'name':'assorti', 'price':54, 'chunk':'chunk', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'}]},"+
            "{'name':'drink', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg',"+
                "'count':0,'submenu':[]}]}}";

    public List<String> getMainMenuNames(){return mainMenuNames;}
    public List<String> getMainMenuPicSource(){return mainMenuPicSource;}
    public List<Integer> getMainMenuItemSize(){return mainMenuItemSize;}

    public List<ArrayList<String>> getSubMenuNames()     {return subMenuNames;}
    public List<ArrayList<Float>>  getSubMenuPrices()    {return subMenuPrices;}
    public List<ArrayList<String>> getSubMenuChunks()    {return subMenuChunks;}
    public List<ArrayList<String>> getSubMenuPicSources(){return subMenuPicSources;}
    public List<ArrayList<Bitmap>> getSubMenuBitmaps()   {return subMenuBitmaps;}

    public void setJSONString (String json){
        toParse = json;
    }
    public void AddBitmap(Bitmap bmp ,int pos){
        subMenuBitmaps.get(pos).add(bmp);
    }

    public List<Bitmap> getMainMenuBitmaps(){return mainMenuBitmaps;}
    public void AddMainMenuBitmap(Bitmap bmp){
        mainMenuBitmaps.add(bmp);
    }
    public void AddMainMenuBitmap(Bitmap bmp, String name){
        if (bmp != null) {
            mainMenuBitmaps.add(bmp);
            saveBitmapToFile(bmp, name);
        } else{
            mainMenuBitmaps.add(loadBitmapFromFile(name));
        }
    }

    private void saveBitmapToFile(Bitmap bmp, String name){
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/images";
        File baseDir = new File(basePath);
        if(!baseDir.exists()){
            // directory not exists
            baseDir.mkdirs();
        }

        File fBmp = new File(baseDir, name + ".png");
        try {
            FileOutputStream fout = new FileOutputStream(fBmp);
            bmp.compress(Bitmap.CompressFormat.PNG, 90, fout);

            fout.flush();
            fout.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Bitmap loadBitmapFromFile(String name){
        String basePath = Environment.getExternalStorageDirectory().getAbsolutePath()+"/images";
        File baseDir = new File(basePath);
        if(baseDir.exists()) {
            File fBmp = new File(baseDir, name+".png");
            Bitmap bmp = null;
            if (fBmp.exists())
                bmp = BitmapFactory.decodeFile(fBmp.getAbsolutePath());

            return bmp;
        } else {
            return null;
        }
    }

    public void Parse(){
        try {
            mainMenuPicSource.clear();

             JSONArray rootArr = new JSONArray(toParse);

            int n = rootArr.length();
            for (int i=0;i<n;++i){
                String url = rootArr.getJSONObject(i).getString("thumb_url");
                String name = rootArr.getJSONObject(i).getString("name");
                String id = rootArr.getJSONObject(i).getString("id");

                mainMenuNames.add(name);
                mainMenuItemSize.add(Integer.parseInt(id));
                mainMenuPicSource.add(url);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONParser (){
        try {
            mainMenuNames = new ArrayList<String>();
            mainMenuPicSource = new ArrayList<String>();
            mainMenuItemSize = new ArrayList<Integer>();

            subMenuNames = new ArrayList<ArrayList<String>>();
            subMenuPrices = new ArrayList<ArrayList<Float>>();
            subMenuChunks = new ArrayList<ArrayList<String>>();
            subMenuBitmaps = new ArrayList<ArrayList<Bitmap>>();
            subMenuPicSources = new ArrayList<ArrayList<String>>();

            JSONObject root = new JSONObject(toParse);

            JSONObject menuObj = root.getJSONObject("menu");

            int mainMenuItemsCount = menuObj.getInt("count");
            JSONArray mainMenuItems = menuObj.getJSONArray("menuitems");
            for (int i=0;i<mainMenuItemsCount; ++i){
                subMenuChunks.add(new ArrayList<String>());
                subMenuPrices.add(new ArrayList<Float>());
                subMenuNames.add(new ArrayList<String>());
                subMenuPicSources.add(new ArrayList<String>());
                subMenuBitmaps.add(new ArrayList<Bitmap>());

                String name = mainMenuItems.getJSONObject(i).getString("name");
                mainMenuNames.add(name);

                String src = mainMenuItems.getJSONObject(i).getString("src");
                mainMenuPicSource.add(src);

                int subMenuCount = mainMenuItems.getJSONObject(i).getInt("count");
                mainMenuItemSize.add(subMenuCount);
                for (int j=0; j<subMenuCount; ++j){
                    JSONArray subMenuItems = mainMenuItems.getJSONObject(i).getJSONArray("submenu");
                    String subName = subMenuItems.getJSONObject(j).getString("name");
                    float subPrice = (float)subMenuItems.getJSONObject(j).getDouble("price");
                    String subChunk = subMenuItems.getJSONObject(j).getString("chunk");
                    String subSrc = subMenuItems.getJSONObject(j).getString("src");

                    subMenuChunks.get(i).add(subChunk);
                    subMenuPrices.get(i).add(subPrice);
                    subMenuNames.get(i).add(subName);
                    subMenuPicSources.get(i).add(subSrc);
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
