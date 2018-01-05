package com.example.gzhang.shopifyplease;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText itemInputEditText;

    Button searchButton;

    ListView itemList;

    ArrayList<Product> products;
    ArrayList<Product> originalList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        products = new ArrayList<Product>();
        retrieveProducts();

        itemInputEditText = (EditText)findViewById(R.id.itemInputEditText);
        searchButton = (Button)findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                products = searchProducts(itemInputEditText.getText().toString());
                updateList();
            }
        });

        itemList = (ListView)findViewById(R.id.itemsListView);
        CustomListAdapter adapter = new CustomListAdapter(this, products);
        itemList.setAdapter(adapter);
        itemList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                Intent sendProduct = new Intent(getBaseContext(), ProductPage.class);
                sendProduct.putExtra("Product", products.get(i));
                startActivity(sendProduct);
            }
        });
    }

    private void updateList() {
        CustomListAdapter adapter = new CustomListAdapter(this, products);
        itemList.setAdapter(adapter);
    }

    private ArrayList<Product> searchProducts(String itemInput) {
        products = originalList;
        ArrayList<Product> filteredProducts = new ArrayList<Product>();

        for(int i = 0; i < products.size(); i++){
            Product p = products.get(i);

            if(candidate(p, itemInput)){
                filteredProducts.add(p);
            }
        }

        return filteredProducts;
    }

    private boolean candidate(Product p, String itemInput) {
        return p.title.toUpperCase().contains(itemInput.toUpperCase());
    }


    public void retrieveProducts() {
        String url = "https://shopicruit.myshopify.com/admin/products.json?page=1&access_token=c32313df0d0ef512ca64d5b336a0d7c6";

        String data = "";

        try {
            data = new DataRetrieverTask().execute(url).get();

            JSONObject jsonObjectProducts = new JSONObject(data.toString());
            JSONArray jsonArray = jsonObjectProducts.getJSONArray("products");

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                //get images
                String imageURL = jsonObject.getJSONArray("images").getJSONObject(0).get("src").toString();
                Bitmap imageBitmap = new ImageRetrieverTask().execute(imageURL).get();

                //get title
                String title = jsonObject.get("title").toString();
                //get description
                String description = jsonObject.get("body_html").toString();

                //get vendor
                String vendor = jsonObject.get("vendor").toString();

                //get product type
                String productType = jsonObject.get("product_type").toString();

                Product newProduct = new Product(imageBitmap, title, description, vendor, productType);
                products.add(newProduct);

                originalList = products;
            }
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    private class DataRetrieverTask extends AsyncTask<String,Void,String>{

        @Override
        protected String doInBackground(String... strings) {
            StringBuilder data = new StringBuilder();

            try{
                URL urlConnect = new URL(strings[0]);
                URLConnection urlConnection = urlConnect.openConnection();

                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

                data = new StringBuilder();

                String line;

                while((line = bufferedReader.readLine()) != null){
                    data.append(line);
                }

                bufferedReader.close();
            }
            catch(Exception e){
                e.printStackTrace();
            }

            return data.toString();
        }
    }

    private class ImageRetrieverTask extends AsyncTask<String,Void,Bitmap>{

        @Override
        protected Bitmap doInBackground(String... strings) {

            //TODO: use OkHttp instead
            Bitmap bitmap = null;

            try{
                URL urlConnection = new URL(strings[0]);
                HttpURLConnection connection = (HttpURLConnection) urlConnection.openConnection();
                connection.setDoInput(true);

                InputStream in = connection.getInputStream();
                bitmap = BitmapFactory.decodeStream(in);
            } catch (IOException e) {
                e.printStackTrace();
            }

            return bitmap;
        }
    }
}
