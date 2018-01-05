package com.example.gzhang.shopifyplease;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by GZhang on 2018-01-04.
 */

public class ProductPage extends Activity{

    TextView pageTitleTextView, pageDescTextView, vendorTextView, productTypeTextView;
    ImageView pageImageView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.product_page_layout);

        pageTitleTextView = (TextView)findViewById(R.id.pageTitleTextView);
        pageDescTextView = (TextView)findViewById(R.id.pageDescTextView);
        vendorTextView = (TextView) findViewById(R.id.vendorTextView);
        productTypeTextView = (TextView)findViewById(R.id.productTypeTextView);
        pageImageView = (ImageView)findViewById(R.id.pageImageView);

        Intent intent = getIntent();

        Product product = intent.getParcelableExtra("Product");

        pageTitleTextView.setText(product.title);
        pageDescTextView.setText(product.description);
        vendorTextView.setText("Vendor: " + product.vendor);
        productTypeTextView.setText("Product type: " + product.productType);
        pageImageView.setImageBitmap(product.image);

    }
}
