package com.tester.sdi.listviewer;

import android.app.ListFragment;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdi on 06.09.14.
 */
public class MainListFragment extends ListFragment{

    MainMenuAdapter adapter ;

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        Intent i = new Intent(getActivity(), SubMenu.class);
        i.putExtra("SubListNumber", position);
        i.putExtra("MenuType", ((TextView)v.findViewById(R.id.id_tv_mainmenu_name)).getText().toString());
        startActivity(i);
        getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        if (JSONParser.isFirstRun == true) {
            new ImageDownloader().execute();
            JSONParser.isFirstRun = false;
        } else {
            udpateListView();
        }
    }

    public void udpateListView(){
        adapter = new MainMenuAdapter(getActivity().getBaseContext());
        List<Integer> subListSizes = GlobalData.mParser.getMainMenuItemSize();
        List<String> names = GlobalData.mParser.getMainMenuNames();

        List<Bitmap> bitmaps = GlobalData.mParser.getMainMenuBitmaps();
        for (int i=0; i<bitmaps.size(); ++i){
            String name = names.get(i);
            int subMenuItemsCount = subListSizes.get(i);
            adapter.Add(new MainMenuItem(name, subMenuItemsCount, bitmaps.get(i)));
        }

        setListAdapter(adapter);
    }



    class MainMenuAdapter extends ArrayAdapter<MainMenuItem>{

        Context context;
        List<MainMenuItem> items = new ArrayList<MainMenuItem>();

        public void Add(MainMenuItem item){
            items.add(item);
        }

        public void Set(List<MainMenuItem> list){
            items = list;
        }

        public List<MainMenuItem> GetItems(){ return items;}

        public MainMenuAdapter(Context context, List<MainMenuItem> items){
            super(context, R.layout.main_menu_listview_row);
            this.context = context;
            this.items = items;
        }

        public MainMenuAdapter(Context context){
            super(context, R.layout.main_menu_listview_row);
            this.context = context;
        }


        @Override
        public int getCount() {
            return items.size();
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                LayoutInflater inflater = (LayoutInflater)
                        context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                convertView = inflater.inflate(R.layout.main_menu_listview_row, parent, false);
            }

            MainMenuItem current = items.get(position);

            ImageView ivPic = (ImageView) convertView.findViewById(R.id.id_iv_mainmenu_pic);
            if (ivPic != null && current.getPic() != null){
                ivPic.setImageBitmap(current.getPic());
            }

            TextView tvName = (TextView) convertView.findViewById(R.id.id_tv_mainmenu_name);
            if (tvName != null){
                tvName.setText(current.getName());
            }

            TextView tvCount = (TextView) convertView.findViewById(R.id.id_tv_mainmenu_count);
            if (tvCount != null){
                tvCount.setText(Integer.toString(current.getCount()));
            }

            return convertView;
        }


    }

    class ImageDownloader extends AsyncTask<Void, Void, Void>{

        List<String> urls = new ArrayList<String>();

        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Void doInBackground(Void... p) {
            while (GlobalData.mParser.isRefreshFinished == false){
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //parser = new GlobalData.mParser();
            GlobalData.mParser.Parse();
            urls = GlobalData.mParser.getMainMenuPicSource();


            // main menu
            for (String url : urls){
                GlobalData.mParser.AddMainMenuBitmap(download(url));
            }

            // submenu
            /*List<ArrayList<String>> subMenuUrls = GlobalData.mParser.getSubMenuPicSources();
            for (int i=0; i<subMenuUrls.size(); ++i){
                List<String> currentURLList = subMenuUrls.get(i);
                for (String url : currentURLList){
                    JSONParser.AddBitmap(download(url), i);
                }
            }
*/
            return null;
        }

        private Bitmap download(String url) {
            Bitmap bmp = null;

            DefaultHttpClient client = new DefaultHttpClient();
            HttpGet getRequest = new HttpGet(url);

            try {
                HttpResponse response = client.execute(getRequest);
                int statusCode = response.getStatusLine().getStatusCode();
                if (statusCode == HttpStatus.SC_OK) {
                    // 200+
                    InputStream is = null;
                    HttpEntity entity = response.getEntity();
                    try {
                        if (entity != null) {
                            is = entity.getContent();
                            bmp = BitmapFactory.decodeStream(is);
                            //complete.add(bmp);
                        }
                    } catch (Exception e) {
                        Log.e("error", e.getMessage());
                    } finally {
                        if (is != null)
                            is.close();

                        entity.consumeContent();
                        //return bmp;
                    }
                }
            } catch (IOException e) {
                Log.e("DOWNLOAD ERROR", e.getMessage());
            }

            return bmp;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
             udpateListView();
        }
    }
}
