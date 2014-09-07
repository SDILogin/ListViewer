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

    private int itemsCount = 0;
    public int getItemsCount(){return itemsCount;}

    private List<MainMenuItem> mainMenuItems = new ArrayList<MainMenuItem>();

    private String toParse = GlobalData.JSONDefault;

    public List<MainMenuItem> getMainMenuItems(){return mainMenuItems;}
    public void setJSONString (String json){
        toParse = json;
    }

    public List<Bitmap> getMainMenuBitmaps(){
        List<Bitmap> ret = new ArrayList<Bitmap>();
        for (MainMenuItem item : mainMenuItems){
            ret.add(item.getPic());
        }
        return ret;
    }
    public void addMainMenuBitmap(int mainMenuItemsIndex, Bitmap bmp){
        MainMenuItem item = mainMenuItems.get(mainMenuItemsIndex);
        String name = item.getName();
        addMainMenuBitmap(item, bmp, name);
    }
    public void addMainMenuBitmap(MainMenuItem item, Bitmap bmp){
        String name = item.getName();
        addMainMenuBitmap(item, bmp, name);
    }
    public void addMainMenuBitmap(MainMenuItem item, Bitmap bmp, String name){
        if (bmp != null) {
            item.setPic(bmp);
            saveBitmapToFile(bmp, name);
        } else{
            item.setPic(loadBitmapFromFile(name));
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

    public void parse(){
        try {
            mainMenuItems.clear();

            JSONArray rootArr = new JSONArray(toParse);

            int n = rootArr.length();
            for (int i=0;i<n;++i){
                String url = rootArr.getJSONObject(i).getString("thumb_url");
                String name = rootArr.getJSONObject(i).getString("name");
                String id = rootArr.getJSONObject(i).getString("id");

                MainMenuItem toAdd =
                        new MainMenuItem(name, Integer.parseInt(id), url, "main_menu_item"+name);
                mainMenuItems.add(toAdd);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public JSONParser (){
        mainMenuItems.clear();
    }
}
