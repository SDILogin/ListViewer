package com.tester.sdi.listviewer;

import android.graphics.Bitmap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdi on 06.09.14.
 */
public class JSONParser {
    public static boolean isRefreshFinished = false;
    public static boolean isFirstRun = true;

    private static List<String> mainMenuNames = new ArrayList<String>();
    private static List<String> mainMenuPicSource = new ArrayList<String>();
    private static List<Integer> mainMenuItemSize = new ArrayList<Integer>();
    private static List<Bitmap> mainMenuBitmaps = new ArrayList<Bitmap>();

    private static List<ArrayList<String>> subMenuNames = new ArrayList<ArrayList<String>>();
    private static List<ArrayList<Float>> subMenuPrices = new ArrayList<ArrayList<Float>>();
    private static List<ArrayList<String>> subMenuChunks = new ArrayList<ArrayList<String>>();
    private static List<ArrayList<Bitmap>> subMenuBitmaps = new ArrayList<ArrayList<Bitmap>>();
    private static List<ArrayList<String>> subMenuPicSources = new ArrayList<ArrayList<String>>();

    private static String toParse = "{'menu':{'count':2,'menuitems':["+
            "{'name':'pizza','src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg',"+
                " 'count':3,'submenu':[{'name':'italia', 'price':55, 'chunk':'chunk', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'},"+
                                      "{'name':'prima', 'price':54, 'chunk':'full', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'},"+
                                      "{'name':'assorti', 'price':54, 'chunk':'chunk', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg'}]},"+
            "{'name':'drink', 'src':'http://cs402330.vk.me/v402330401/9760/pV6sZ5wRGxE.jpg',"+
                "'count':0,'submenu':[]}]}}";

    public static List<String> getMainMenuNames(){return mainMenuNames;}
    public static List<String> getMainMenuPicSource(){return mainMenuPicSource;}
    public static List<Integer> getMainMenuItemSize(){return mainMenuItemSize;}

    public static List<ArrayList<String>> getSubMenuNames(){return subMenuNames;}
    public static List<ArrayList<Float>> getSubMenuPrices(){return subMenuPrices;}
    public static List<ArrayList<String>> getSubMenuChunks(){return subMenuChunks;}
    public static List<ArrayList<String>> getSubMenuPicSources(){return subMenuPicSources;}
    public static List<ArrayList<Bitmap>> getSubMenuBitmaps(){return subMenuBitmaps;}

    public static void AddMainMenuBitmap(Bitmap bmp){
        mainMenuBitmaps.add(bmp);
    }
    public static List<Bitmap> getMainMenuBitmaps(){return mainMenuBitmaps;}

    public static void setJSONString (String json){
        toParse = json;
    }
    public static void AddBitmap(Bitmap bmp ,int pos){
        subMenuBitmaps.get(pos).add(bmp);
    }

    public static void Parse(){
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
