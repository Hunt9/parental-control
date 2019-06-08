package com.nephi.getoffyourphone;

import android.content.Context;
import android.graphics.drawable.Drawable;
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class SelectedAppsAdapter extends BaseAdapter {

    private final String[] values;
    private final String[] numbers;
    private final Drawable[] images;
    private Context context;

    public SelectedAppsAdapter(Context context, String[] values, String[] numbers, Drawable[] images) {
        //super(context, R.layout.single_list_app_item, utilsArrayList);
        this.context = context;
        this.values = values;
        this.numbers = numbers;
        this.images = images;
    }


    @Override
    public int getCount() {
        return values.length;
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {


        ViewHolder viewHolder;

        final View result;

        if (convertView == null) {

            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(context);
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
            viewHolder.txtName = convertView.findViewById(R.id.name);
            viewHolder.txtVersion = convertView.findViewById(R.id.version);
            viewHolder.icon = convertView.findViewById(R.id.icon);

            result = convertView;

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        viewHolder.txtName.setText(values[position]);
        viewHolder.txtVersion.setText(context.getString(R.string.custom_list_version) + numbers[position]);
        viewHolder.icon.setImageDrawable(images[position]);

        return convertView;
    }

    private static class ViewHolder {

        TextView txtName;
        TextView txtVersion;
        ImageView icon;

    }
}
