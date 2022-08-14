package lk.parinda.safemellow.parant_views.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.Bundle;
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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lk.parinda.safemellow.CustomAdaptor;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;
import lk.parinda.safemellow.module.DragAlcoholModule;
import lk.parinda.safemellow.module.SCommentResponse;

public class DragDitecterView extends AppCompatActivity implements OnChartValueSelectedListener {

    private PieChart chart;
    ArrayList<DragAlcoholModule> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_ditecter_view);

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
    }

    private void setData() {
        Map<String,Integer> map = new HashMap<>();
        ArrayList<PieEntry> entries = new ArrayList<>();
        ArrayList<String> mapKeys = new ArrayList<>();
        for(DragAlcoholModule temp : list){
            if(map.containsKey(temp.getTagName())){
                map.put(temp.getTagName(),map.get(temp.getTagName())+1);
            }else{
                map.put(temp.getTagName(),1);
                mapKeys.add(temp.getTagName());
            }
        }

        for(String name : mapKeys) {
            entries.add(new PieEntry(Float.parseFloat(String.valueOf(map.get(name))), name,
                    getResources().getDrawable(R.drawable.star)));
        }

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


        RecyclerView listView = findViewById(R.id.list);
        LinearLayoutManager layoutManager = new LinearLayoutManager(DragDitecterView.this, RecyclerView.VERTICAL, false);
        listView.setLayoutManager(layoutManager);
        listView.setItemAnimator(new DefaultItemAnimator());

        CustomAdaptor adaptor = new CustomAdaptor(DragDitecterView.this,null,null,null,list);
        listView.setAdapter(adaptor);
        listView.setHasFixedSize(true);
        adaptor.notifyDataSetChanged();
    }

    @Override
    public void onValueSelected(Entry e, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }

    public void requestData(){
        list.clear();
        String url = "http://54.91.4.180/abc/pred";
        JsonArrayRequest jsonObjectRequest = new JsonArrayRequest(Request.Method.GET, url,null,
                response -> {
                    try {
                        System.out.println(response);
                        Gson gson = new GsonBuilder().create();
                        for (int i = 0; i < response.length(); i++) {
                            DragAlcoholModule item = gson.fromJson(response.getJSONObject(i).toString(), DragAlcoholModule.class);
                            list.add(item);
                        }
                        System.out.println(list.size());
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
        MySingleton.getInstance(DragDitecterView.this).addToRequestQueue(jsonObjectRequest);
    }
}