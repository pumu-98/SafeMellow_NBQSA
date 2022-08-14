package lk.parinda.safemellow.parant_views;

import android.app.AlertDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import lk.parinda.safemellow.LoginActivity;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.module.GameImageResponse;
import lk.parinda.safemellow.module.GameImageSaveRequest;
import lk.parinda.safemellow.parant_views.views.FacedookLoginPage;

public class UploadTask extends AsyncTask<File, Void, GameImageResponse> {

    private final Context context;

    public UploadTask(Context context) {
        this.context = context;
    }

    protected GameImageResponse doInBackground(File... urls) {
        try {
            return request(urls[0]);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private GameImageResponse request(File file){
        final GameImageResponse[] feed = {null};
        RequestParams params = new RequestParams();
        try {
            params.put("img", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        SyncHttpClient client = new SyncHttpClient();
        client.post("http://54.80.17.37/test/upload", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.e("request"," start");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("request"," status "+statusCode);
                Log.e("request"," status "+response);
                Gson gson = new GsonBuilder().create();
                GameImageResponse item = gson.fromJson(response.toString(), GameImageResponse.class);
                feed[0] = item;
            }

            @Override
            public void onFinish() {
                Log.e("request"," onFinish");
            }
        });
        return feed[0];
    }

    protected void onPostExecute(GameImageResponse feed) {
        if(feed!=null) {
            Log.e("onPostExecute", feed.getAbout());
            requestData(feed);
            new AlertDialog.Builder(context)
                    .setTitle(feed.getGame())
                    .setMessage(feed.getDetails()+"\n\n"+feed.getAbout())
                    .setPositiveButton(android.R.string.yes, (dialog, which) -> dialog.dismiss())
                    .show();
        }else{
            Log.e("onPostExecute", "feed.getAbout()");
        }
    }

    public void requestData(GameImageResponse feed){
        try {
            GameImageSaveRequest gameImageSaveRequest = new GameImageSaveRequest();
            gameImageSaveRequest.setCId(Integer.parseInt(LoginActivity.pid));
            gameImageSaveRequest.setPId(Integer.parseInt(LoginActivity.cid));
            gameImageSaveRequest.setObj(feed);
            Log.e("wwwwww",gameImageSaveRequest.toString());
            JSONObject jsonObject = new JSONObject(gameImageSaveRequest.toString());
            Log.e("ooo",jsonObject.toString());
            String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/insert";
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                    response -> {
                        if (response != null) {
                            Toast.makeText(context, "Precess Success", Toast.LENGTH_LONG).show();
                        }
                    },
                    e -> {
                        System.err.println(e.getMessage());
                    }
            );
            MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}