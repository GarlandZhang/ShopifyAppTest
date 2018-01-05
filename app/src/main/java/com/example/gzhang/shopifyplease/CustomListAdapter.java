package com.example.gzhang.shopifyplease;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by GZhang on 2018-01-04.
 */

public class CustomListAdapter extends BaseAdapter {

    private Context context;
    List<Product> productList;

    public CustomListAdapter(Activity context, List<Product> productList){
        super();
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return productList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    public class ViewHolder{
        ImageView productImageView;
        TextView titleTextView;
        TextView descriptionTextView;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {

        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        ViewHolder viewHolder;


        if(view == null){
            view = layoutInflater.inflate(R.layout.row_layout, null);

            viewHolder = new ViewHolder();
            viewHolder.productImageView = (ImageView) view.findViewById(R.id.itemImageView);
            viewHolder.titleTextView = (TextView) view.findViewById(R.id.titleTextView);
            viewHolder.descriptionTextView = (TextView) view.findViewById(R.id.descriptionTextView);

            view.setTag(viewHolder);
        }
        else{
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.productImageView.setImageBitmap(productList.get(i).image);
        viewHolder.titleTextView.setText(productList.get(i).title);
        viewHolder.descriptionTextView.setText(productList.get(i).description);

        return view;
    }
}
