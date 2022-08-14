package lk.parinda.safemellow.parant_views.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import lk.parinda.safemellow.CustomAdaptor;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;
import lk.parinda.safemellow.module.GameImageResponse;
import lk.parinda.safemellow.module.ImageResponse;

public class DownloadImageActivity extends AppCompatActivity implements OnChartValueSelectedListener {

    ArrayList<ImageResponse> imageResponses = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_download_image);


        requestData();
    }

    private void setData() {

        RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DownloadImageActivity.this, RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());

        CustomAdaptor adaptor = new CustomAdaptor(DownloadImageActivity.this,null,null,imageResponses,null);
        list.setAdapter(adaptor);
        list.setHasFixedSize(true);
        adaptor.notifyDataSetChanged();
    }

    public void showDialog(GameImageResponse response){
        new AlertDialog.Builder(DownloadImageActivity.this)
                .setTitle(response.getGame())
                .setMessage(response.getDetails()+"\n\n"+response.getAbout())
                .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                .show();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void requestData(){
        imageResponses.clear();
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/images";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        Gson gson = new GsonBuilder().create();
                        for (int i = 0; i < response.length(); i++) {
                            ImageResponse item = gson.fromJson(response.getJSONObject(i).toString(), ImageResponse.class);
                            imageResponses.add(item);
                        }
                        System.out.println(imageResponses.size());
                        setData();
                    }catch (Exception e){
                        setData();
                        e.printStackTrace();
                    }
                },
                e -> {
                    setData();
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(DownloadImageActivity.this).addToRequestQueue(jsonObjectRequest);
    }
}