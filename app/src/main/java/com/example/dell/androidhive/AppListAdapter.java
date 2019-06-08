package com.example.dell.androidhive;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;

public class AppListAdapter extends ArrayAdapter<ChildAppListModel> {
    Context context;
    ArrayList<ChildAppListModel> appList;

    public AppListAdapter(Context context, ArrayList<ChildAppListModel> appList) {
        super(context, R.layout.item_app_list, appList);

        this.context = context;
        this.appList = appList;
    }

    public class ViewHolder {
        TextView tvAppName;
        CheckBox cbSelected;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final ChildAppListModel obj_itemPos = getItem(position);

        final ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_app_list, parent, false);
            //saving id's
            viewHolder.tvAppName = convertView.findViewById(R.id.AppName);
            viewHolder.cbSelected = convertView.findViewById(R.id.isSelected);


            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tvAppName.setText(obj_itemPos.getName());
        viewHolder.cbSelected.setChecked(obj_itemPos.isLocked());

        viewHolder.cbSelected.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (viewHolder.cbSelected.isChecked())
                {
                    obj_itemPos.setLocked(true);
                    notifyDataSetChanged();
                }else {

                    obj_itemPos.setLocked(false);
                    notifyDataSetChanged();

                }
            }
        });


        return convertView;

    }


}
