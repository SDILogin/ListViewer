package com.tester.sdi.listviewer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sdi on 07.09.14.
 */
class MainMenuAdapter extends ArrayAdapter<MainMenuItem> {

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
