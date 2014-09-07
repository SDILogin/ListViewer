package com.tester.sdi.listviewer;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdi on 06.09.14.
 */
public class SubMenu extends Activity{

    int subMenuIndex = -1;
    ArrayList<String> names = new ArrayList<String>();
    ArrayList<Float> prices = new ArrayList<Float>();
    ArrayList<Bitmap> bitmaps = new ArrayList<Bitmap>();
    ArrayList<String> chunks = new ArrayList<String>();

    SubMenuListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_submenu);

        overridePendingTransition(R.anim.slide_in, R.anim.slide_out);

        getActionBar().setDisplayShowTitleEnabled(true);
        getActionBar().setSubtitle("Курск");
        getActionBar().setTitle(getIntent().getExtras().getString("MenuType","Menu"));

        int c = getResources().getColor(R.color.actionbar_background);
        getActionBar().setBackgroundDrawable(new ColorDrawable(c));

        getActionBar().setDisplayHomeAsUpEnabled(true);

        if (true) return;

/*        subMenuIndex = getIntent().getExtras().getInt("SubListNumber",subMenuIndex);
        chunks = JSONParser.getSubMenuChunks().get(subMenuIndex);
        names = JSONParser.getSubMenuNames().get(subMenuIndex);
        prices = JSONParser.getSubMenuPrices().get(subMenuIndex);
        bitmaps = JSONParser.getSubMenuBitmaps().get(subMenuIndex);

        adapter = new SubMenuListViewAdapter(this);
        int n = bitmaps.size();
        for (int i=0; i<n; ++i){
            String name = names.get(i);
            String chunk = chunks.get(i);
            float price = prices.get(i);
            Bitmap bmp = bitmaps.get(i);
            adapter.Add(new SubMenuItem(name, price, chunk, bmp));
        }

        ((ListView) findViewById(R.id.id_lv_submenu)).setAdapter(adapter);
*/
    }

    class SubMenuItem{
        private Bitmap pic   = null;
        private String name  = "";
        private Float price  = 0f;
        private String chunk = "";

        public SubMenuItem(String name, Float price, String chunk, Bitmap bmp){
            this.pic = bmp;
            this.name = name;
            this.chunk = chunk;
            this.price  = price;
        }

        public void SetPic(Bitmap bmp){this.pic = bmp;}
        public void SetName(String name){this.name = name;}
        public void SetPrice(float price){this.price = price;}
        public void SetChunk(String chunk){this.chunk = chunk;}

        public Bitmap GetBitmap(){return pic;}
        public String GetName(){return name;}
        public String GetChunk(){return chunk;}
        public float  GetPrice(){return price;}
    }

    class SubMenuListViewAdapter extends ArrayAdapter<SubMenuItem>{
        List<SubMenuItem> items = new ArrayList<SubMenuItem>();

        Context context;

        public void Add(SubMenuItem item){
            items.add(item);
        }

        public SubMenuListViewAdapter(Context context){
            super(context, R.layout.submenu_listview_row);
            this.context = context;
        }

        public SubMenuListViewAdapter(Context context, List<SubMenuItem> items){
            super(context, R.layout.submenu_listview_row);
            this.context = context;
            this.items = items;
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
                convertView = inflater.inflate(R.layout.submenu_listview_row, parent, false);
            }

            SubMenuItem current = items.get(position);

            ImageView pic = (ImageView) convertView.findViewById(R.id.id_iv_food_pic_submenu);
            if (pic != null && current.GetBitmap() != null){
                pic.setImageBitmap(current.GetBitmap());
            }

            TextView name = (TextView) convertView.findViewById(R.id.id_tv_food_name);
            if (name != null){
                name.setText(current.GetName());
            }

            TextView chunk = (TextView) convertView.findViewById(R.id.id_tv_food_chunk);
            if (name != null){
                chunk.setText(current.GetChunk());
            }

            TextView price = (TextView) convertView.findViewById(R.id.id_tv_food_price);
            if (name != null){
                String s = Integer.toString((int)current.GetPrice());
                price.setText(s);
            }

            return convertView;
        }
    }
}
