package lk.parinda.safemellow.parant_views.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
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

import java.util.ArrayList;

import lk.parinda.safemellow.CustomAdaptor;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;
import lk.parinda.safemellow.module.SCommentResponse;

public class ParentCommentView extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart chart;
    private Button button;


    ArrayList<SCommentResponse> noList = new ArrayList<>();
    ArrayList<SCommentResponse> yesList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_parent_comment_view);

        chart = findViewById(R.id.chart1);
        chart.setUsePercentValues(true);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 10, 5, 5);
        chart.setDragDecelerationFrictionCoef(0.95f);
        chart.setDrawHoleEnabled(false);
        chart.setHoleColor(Color.WHITE);
        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);
        chart.setHoleRadius(58f);
        chart.setTransparentCircleRadius(61f);
        chart.setDrawCenterText(false);
        chart.setRotationAngle(0);
        chart.setRotationEnabled(false);
        chart.setHighlightPerTapEnabled(true);
        chart.setOnChartValueSelectedListener(this);
        chart.setDrawSliceText(false);

        requestData();

        button = (Button) findViewById(R.id.button12);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("clicked");

                requestFBData();
            }
        });

    }


    public void requestFBData(){
        RequestQueue queue = Volley.newRequestQueue(ParentCommentView.this);
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/posts/?id=";
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.POST, url,
                response -> {
                    if (response!=null){
                        Toast.makeText(this,"Precess Success",Toast.LENGTH_LONG).show();
                    }
                },
                e -> {
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(ParentCommentView.this).addToRequestQueue(jsonObjectRequest);
    }



    private void setData() {
        ArrayList<PieEntry> entries = new ArrayList<>();

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(noList.size())),
                "Non-Bullying",
                getResources().getDrawable(R.drawable.star)));

        entries.add(new PieEntry(Float.parseFloat(String.valueOf(yesList.size())),
                "Bullying",
                getResources().getDrawable(R.drawable.star)));

        TextView positiveText = findViewById(R.id.textView3);
        TextView negativeText = findViewById(R.id.textView4);

        positiveText.setText("Bullying : "+ yesList.size());
        negativeText.setText("Non-Bullying : "+ noList.size());

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
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        chart.highlightValues(null);

        chart.invalidate();


        RecyclerView list = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(ParentCommentView.this, RecyclerView.VERTICAL, false);
        list.setLayoutManager(layoutManager);
        list.setItemAnimator(new DefaultItemAnimator());

        ArrayList<SCommentResponse> fullList = new ArrayList<>();
        fullList.addAll(yesList);
        fullList.addAll(noList);
        CustomAdaptor adaptor = new CustomAdaptor(ParentCommentView.this,null,fullList,null,null);
        list.setAdapter(adaptor);
        list.setHasFixedSize(true);
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void requestData(){
        noList.clear();
        yesList.clear();
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/posts?type=no";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        Gson gson = new GsonBuilder().create();
                        for (int i = 0; i < response.length(); i++) {
                            SCommentResponse item = gson.fromJson(response.getJSONObject(i).toString(), SCommentResponse.class);
                            noList.add(item);
                        }
                        System.out.println(noList.size());
                        String url1 = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/posts?type=yes";
                        JsonArrayRequest jsonObjectRequest1 = new JsonArrayRequest(Request.Method.GET, url1,null,
                                response1 -> {
                                    try {
                                        System.out.println(response1);
                                        Gson gson1 = new GsonBuilder().create();
                                        for (int i = 0; i < response1.length(); i++) {
                                            SCommentResponse item = gson1.fromJson(response1.getJSONObject(i).toString(), SCommentResponse.class);
                                            yesList.add(item);
                                        }
                                        System.out.println(yesList.size());
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
                        MySingleton.getInstance(ParentCommentView.this).addToRequestQueue(jsonObjectRequest1);
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
        MySingleton.getInstance(ParentCommentView.this).addToRequestQueue(jsonObjectRequest);
    }


}