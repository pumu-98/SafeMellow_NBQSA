package lk.parinda.safemellow;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import lk.parinda.safemellow.module.DragAlcoholModule;
import lk.parinda.safemellow.module.ImageResponse;
import lk.parinda.safemellow.module.KeyLogerResponseItem;
import lk.parinda.safemellow.module.SCommentResponse;
import lk.parinda.safemellow.parant_views.RetrieveFeedTask;

public class CustomAdaptor extends RecyclerView.Adapter<CustomAdaptor.MyViewHolder>{

    private final ArrayList<KeyLogerResponseItem> keyLogerResponse;
    private final ArrayList<SCommentResponse> sCommentResponse;
    private final ArrayList<ImageResponse> imageResponses;
    private final ArrayList<DragAlcoholModule> drugAlcoholList;
    Context context;

    public CustomAdaptor(Context context,ArrayList<KeyLogerResponseItem> keyLogerResponse,ArrayList<SCommentResponse> sCommentResponse,ArrayList<ImageResponse> imageResponses,ArrayList<DragAlcoholModule> drugAlcoholList) {
        this.keyLogerResponse = keyLogerResponse;
        this.sCommentResponse = sCommentResponse;
        this.imageResponses=imageResponses;
        this.drugAlcoholList=drugAlcoholList;
        this.context=context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.adaper_view, parent, false);

        return new CustomAdaptor.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        if(keyLogerResponse!=null){
            holder.textL3.setVisibility(View.GONE);
            holder.imageView9.setVisibility(View.GONE);
            KeyLogerResponseItem item = keyLogerResponse.get(position);
            holder.textL1.setText("Id : "+item.getId());
            holder.textL2.setText("Result : "+item.getType());
            holder.textR1.setText("Word : "+item.getResult().split(",")[0]);
            holder.textR2.setText("Date : "+item.getDate());
        }

        if(sCommentResponse!=null){
            holder.textL3.setVisibility(View.GONE);
            holder.imageView9.setVisibility(View.GONE);
            SCommentResponse item = sCommentResponse.get(position);
            holder.textL1.setText("Id : "+item.getId());
            holder.textL2.setText("Result : "+item.getType());
            holder.textR1.setText("Word : "+item.getResult().split(",")[0]);
            holder.textR2.setText("Date : "+item.getDate());
        }

        if(drugAlcoholList!=null){
            holder.textL3.setVisibility(View.GONE);
            holder.textL2.setVisibility(View.GONE);
            holder.textR2.setVisibility(View.GONE);
            holder.imageView9.setVisibility(View.GONE);
            DragAlcoholModule item = drugAlcoholList.get(position);
            holder.textL1.setText("Type : "+item.getTagName());
            holder.textR1.setText("Probability : "+item.getMaxProbability());
        }

        if(imageResponses!=null){
            holder.textL3.setVisibility(View.GONE);
            holder.textR1.setVisibility(View.GONE);
            holder.textR2.setVisibility(View.GONE);
            holder.imageView9.setVisibility(View.VISIBLE);
            ImageResponse item = imageResponses.get(position);
            holder.textL1.setText("Id : "+item.getId());
            holder.textL2.setText("Date : "+item.getUploadedTime());
            try {
                Glide.with(context).load(item.getUserImg()).into(holder.imageView9);
                holder.imageView9.setOnClickListener(view ->{
                    Log.e("clicked","------");
                    new RetrieveFeedTask(context).execute(item.getUserImg());
                });
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        if(keyLogerResponse!=null){
            return keyLogerResponse.size();
        }
        if(sCommentResponse!=null){
            return sCommentResponse.size();
        }
        if(imageResponses!=null){
            return imageResponses.size();
        }
        if(drugAlcoholList!=null){
            return drugAlcoholList.size();
        }
        return 0;
    }

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView textL1,textL2,textL3,textR1,textR2;
        ImageView imageView9;

        public MyViewHolder(View view) {
            super(view);
            textL1 = view.findViewById(R.id.textL1);
            textL2 = view.findViewById(R.id.textL2);
            textL3 = view.findViewById(R.id.textL3);
            textR1 = view.findViewById(R.id.textR1);
            textR2 = view.findViewById(R.id.textR2);
            imageView9 = view.findViewById(R.id.imageView9);
        }
    }

    private File getImage(String url){
        try {
            URL imageurl = new URL(url);
            Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String ll = Environment.getDataDirectory()+ File.separator+"data"+ File.separator+"lk.parinda.safemellow"+ File.separator+"image"
                    + File.separator + "test.jpg";
            File f = new File(ll);
            if(f.exists()){
                f.getParentFile().delete();
            }
            f.getParentFile().mkdirs();
            Log.e("file size0","------"+ll);
            Log.e("file size1","------"+bytes.size());
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
            return f;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void request(String url) {
        File file = getImage(url);
        if(file==null){
            Log.e("clicked","null file");
            return;
        }

        RequestParams params = new RequestParams();
        try {
            params.put("img", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://54.80.17.37/test/upload", params, new JsonHttpResponseHandler() {
            ProgressDialog pd;
            @Override
            public void onStart() {
                pd = new ProgressDialog(context);
                pd.setTitle("uploading");
                pd.setMessage("uploadingMessage");
                pd.setIndeterminate(false);
                pd.show();
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(context, statusCode, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                pd.dismiss();
            }
        });
    }





}
