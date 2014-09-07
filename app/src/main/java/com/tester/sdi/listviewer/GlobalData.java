package com.tester.sdi.listviewer;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;

import java.sql.Time;

/**
 * Created by sdi on 06.09.14.
 */
public class GlobalData {
    private static String SP_TAG_LASTUPDATE = "SP_TAG_LASTUPDATE";
    private static int deltaTimeSeconds = 60;
    public final static JSONParser mParser = new JSONParser();
    public static boolean isMustBeUpdated(Activity caller){
        if (caller == null)
            return true;

        long lastUpdate = caller.getPreferences(caller.MODE_PRIVATE).getLong(SP_TAG_LASTUPDATE, -1);
        long now = System.currentTimeMillis();
        if (now - lastUpdate > deltaTimeSeconds * 1000) {
            // must be updated
            SharedPreferences.Editor editor = caller.getPreferences(caller.MODE_PRIVATE).edit();
            editor.putLong(SP_TAG_LASTUPDATE, now);
            editor.commit();
            return true;
        }else {
            // actual data
            return false;
        }
    }

    public static String JSONDefault = "[\n" +
            "    {\n" +
            "      \"id\": \"2\",\n" +
            "      \"name\": \"ÐŸÐ¸Ñ†Ñ†Ð°\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/c2e11498-9b35-4ff1-a2d2-e994204d90d0.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"0\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"3\",\n" +
            "      \"name\": \"Ð\u009DÐ°Ð¿Ð¸Ñ‚ÐºÐ¸\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/e14b7d2e-82d9-40e3-94e9-39abd45a41e1.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"1\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"4\",\n" +
            "      \"name\": \"Ð‘Ð»ÑŽÐ´Ð° Ð¸Ð· ÐºÑƒÑ€Ð¸Ñ†Ñ‹\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/f3f5d61a-8296-11e3-bae4-001b21b8a590/15dfcc85-bd07-42fa-a28e-fe1d5a6c5d5f.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"2\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"5\",\n" +
            "      \"name\": \"Ð“Ð°Ñ€Ð½Ð¸Ñ€Ñ‹\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/40bf0afd-26fb-4bea-8cc8-292690f94f6c.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"3\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"6\",\n" +
            "      \"name\": \"Ð”ÐµÑ\u0081ÐµÑ€Ñ‚Ñ‹\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/ee3392fb-04cc-46a5-82b9-db754c406a63.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"4\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"7\",\n" +
            "      \"name\": \"Ð¡Ð°Ð»Ð°Ñ‚Ñ‹\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/7feba02b-3d50-4c60-992d-b6bb577b8e7a.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"5\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    },\n" +
            "    {\n" +
            "      \"id\": \"13\",\n" +
            "      \"name\": \"Ð¡Ð¿ÐµÑ†Ð¿Ñ€ÐµÐ´Ð»Ð¾Ð¶ÐµÐ½Ð¸Ñ\u008F\",\n" +
            "      \"thumb_url\": \"https://102922.selcdn.ru/nomenclature_images/2189038b-f6fe-11e3-8bac-50465d4d1d14/e0f36317-8ed2-4c5d-9cc7-cdb0f2d1aaff.png\",\n" +
            "      \"is_actual\": \"1\",\n" +
            "      \"sort_id\": \"6\",\n" +
            "      \"table_name\": \"categories\",\n" +
            "      \"action_type\": 1\n" +
            "    }\n" +
            "  ]";
}
