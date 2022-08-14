package lk.parinda.safemellow.parant_views.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

import lk.parinda.safemellow.CustomAdaptor;
import lk.parinda.safemellow.DashBoard;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;
import lk.parinda.safemellow.module.DragAlcoholModule;
import lk.parinda.safemellow.module.KeyLogerResponseItem;
import lk.parinda.safemellow.module.SCommentResponse;

public class ParentDashBoard extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart chart;

    int dragCount = 0,commentCount = 0,gameCount = 0, keyCount = 0;

    @Override
    protected void onResume() {
        if(chart!=null) {
            requestDataDrag();
        }
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_dash_board);

        getHashkey();

        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.BLACK);
        chart.setTransparentCircleColor(Color.BLACK);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(false);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(this);
        chart.setEntryLabelColor(Color.BLACK);
        chart.setCenterTextColor(Color.BLACK);
        chart.setDrawSliceText(false);


        requestDataDrag();

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentDashBoard.this, DashBoard.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.textView5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentDashBoard.this, DragDitecterView.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.textView6).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentDashBoard.this, ParentCommentView.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.textView7).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentDashBoard.this, DownloadImageActivity.class);
                startActivity(intent);
            }
        });

        findViewById(R.id.textView8).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ParentDashBoard.this, ParentKeyboardView.class);
                startActivity(intent);
            }
        });
    }

    public void getHashkey(){
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getApplicationContext().getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                Log.i("Base64", Base64.encodeToString(md.digest(),Base64.NO_WRAP));
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.d("Name not found", e.getMessage(), e);

        } catch (NoSuchAlgorithmException e) {
            Log.d("Error", e.getMessage(), e);
        }
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(keyCount)),
                "Detected Words",
                getResources().getDrawable(R.drawable.star)));

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(dragCount)),
                "Drug and Alcohol",
                getResources().getDrawable(R.drawable.star)));

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(gameCount)),
                "Gaming Activities",
                getResources().getDrawable(R.drawable.star)));

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(commentCount)),
                "Detected Comments",
                getResources().getDrawable(R.drawable.star)));

        TextView drag = findViewById(R.id.textView5);
        TextView comment = findViewById(R.id.textView6);
        TextView game = findViewById(R.id.textView7);
        TextView key = findViewById(R.id.textView8);

        key.setText("Detected Words\n"+keyCount);
        drag.setText("Detected Drug and Alcohol Searching\n"+dragCount);
        game.setText("Detected Games\n"+gameCount);
        comment.setText("Detected Comments\n"+commentCount);

        PieDataSet dataSet = new PieDataSet(entries, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors

        ArrayList<Integer> colors = new ArrayList<>();

        for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.JOYFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);
        data.setValueTextColor(Color.BLACK);
        chart.setData(data);

        chart.highlightValues(null);

        chart.invalidate();

    }

    public void requestDataDrag(){
        String url = "http://54.91.4.180/abc/pred";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        dragCount = response.length();
                    }catch (Exception e){
                        dragCount = 0;
                        e.printStackTrace();
                    }finally {
                        requestDataKey();
                    }
                },
                e -> {
                    dragCount = 0;
                    requestDataKey();
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(ParentDashBoard.this).addToRequestQueue(jsonObjectRequest);
    }

    public void requestDataKey(){
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/words?type=negative";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        keyCount = response.length();
                    }catch (Exception e){
                        keyCount = 0;
                        e.printStackTrace();
                    }finally {
                        requestDataComment();
                    }
                },
                e -> {
                    keyCount = 0;
                    requestDataComment();
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(ParentDashBoard.this).addToRequestQueue(jsonObjectRequest);
    }

    public void requestDataComment(){
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/posts?type=yes";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        commentCount = response.length();
                    }catch (Exception e){
                        commentCount=0;
                        e.printStackTrace();
                    }finally {
                        requestDataGame("pubg");
                    }
                },
                e -> {
                    commentCount=0;
                    requestDataGame("pubg");
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(ParentDashBoard.this).addToRequestQueue(jsonObjectRequest);
    }

    public void requestDataGame(String code){
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/list?type="+code;
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        gameCount += response.length();
                    }catch (Exception e){
                        gameCount+=0;
                        e.printStackTrace();
                    }finally {
                        if(code.equals("pubg")){
                            requestDataGame("cod");
                        }else{
                            setData();
                        }
                    }
                },
                e -> {
                    gameCount+=0;

                    if(code.equals("pubg")){
                        requestDataGame("cod");
                    }else{
                        setData();
                    }
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(ParentDashBoard.this).addToRequestQueue(jsonObjectRequest);
    }
}