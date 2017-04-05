package com.example.laubi.myapplication.Adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.backend.Dto.Emote;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by laubi on 4/4/2017.
 */

public class ListAdapter extends BaseAdapter{

    private Context mContext;

    public ListAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return emoteMap.size();
    }

    @Override
    public Object getItem(int position) {
        return emoteMap.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    // create a new ImageView for each item referenced by the Adapter
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView;
        if (convertView == null) {
            // if it's not recycled, initialize some attributes
            imageView = new ImageView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(40, 40));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            imageView.setPadding(4, 4, 4, 4);
        } else {
            imageView = (ImageView) convertView;
        }
        Emote e = emoteMap.get(position);
        java.io.File nf = new java.io.File(mContext.getFilesDir(), e.getFileName());
        Bitmap bitmap = BitmapFactory.decodeFile(nf.getAbsolutePath());

        Bitmap changeableBitmap = bitmap.copy(Bitmap.Config.ARGB_8888, true);
        imageView.setImageBitmap(changeableBitmap);

        return imageView;
    }

    private Map<Integer, Emote> emoteMap = new LinkedHashMap<>();

    public void addEmote(Emote e){
        emoteMap.put(this.getCount(), e);
    }
}
