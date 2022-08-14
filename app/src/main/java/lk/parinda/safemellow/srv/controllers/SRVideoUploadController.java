package lk.parinda.safemellow.srv.controllers;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.SyncHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import lk.parinda.safemellow.SharedPreferencesManager;
import lk.parinda.safemellow.db_controll.SRVideoUploadDBC;
import lk.parinda.safemellow.db_controll.module.SRVideoUploadModule;
import lk.parinda.safemellow.module.GameImageResponse;

public class SRVideoUploadController {

    private static int maxId = 0;
    public static boolean isUploading = false;
    private static int ongoingIndex = 0;
    private static ArrayList<Integer> indexes = new ArrayList<>();
    private Context context;

    public void init(Context context){
        this.context = context;
        ArrayList<Integer> data = new SRVideoUploadDBC(context).loadIndexes();
        if(!data.isEmpty()){
            maxId = data.get(data.size()-1);
            data.remove(data.size()-1);
            if(!data.isEmpty()){
                indexes.addAll(data);
            }
        }
    }

    public void onFileSave(String path){
       int id = new SRVideoUploadDBC(context).insert(ongoingIndex,path);
       if(!isUploading){
           uploadFile();
       }

    }

    private void uploadFile(){
        isUploading=true;
        ArrayList<SRVideoUploadModule> list = new SRVideoUploadDBC(context).getAllUnSyncList();
        if(list.size()>0) {
            SRVideoUploadModule temp = list.get(0);
            if (temp.getPath() != null) {
                File file = new File(temp.getPath());
                if (file.exists()) {
//                    String base64text = getBase64FromPath(file);
//                        StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+ SharedPreferencesManager.ipAddress+"/safemellow/upload.php",
//                            new Response.Listener<String>() {
//                                @Override
//                                public void onResponse(String response) {
//                                    System.out.println(response);
//                                    onFileUploadComplete(temp);
//                                    uploadFile();
//                                }
//                            },
//                            new Response.ErrorListener() {
//                                @Override
//                                public void onErrorResponse(VolleyError error) {
//                                    System.out.println(error.getMessage());
//                                    new SRVideoUploadDBC(context).setUpdateCompleteWithError(temp.getId());
//                                    uploadFile();
//                                }
//                            }) {
//                        @Override
//                        protected Map<String, String> getParams() {
//                            Map<String, String> params = new HashMap<String, String>();
//                            params.put("file", base64text);
//                            return params;
//                        }
//
//                    };
//                    MySingleton.getInstance(context).addToRequestQueue(stringRequest);
                    request(file,temp);
                }else{
                    uploadFile();
                }
            }else{
                uploadFile();
            }
        }else{
            isUploading=false;
        }
    }

    private void request(File file,SRVideoUploadModule temp){
        RequestParams params = new RequestParams();
        try {
            params.put("files", file);
        } catch(FileNotFoundException e) {
            e.printStackTrace();
        }

        AsyncHttpClient client = new AsyncHttpClient();
        client.post("http://54.91.4.180/abc/make-frame", params, new JsonHttpResponseHandler() {
            @Override
            public void onStart() {
                Log.e("request"," start");
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                onFileUploadComplete(temp);
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.e("request"," status "+statusCode);
                Log.e("request"," status "+response);
                onFileUploadComplete(temp);
            }

            @Override
            public void onFinish() {
                Log.e("request"," onFinish");
                uploadFile();
            }
        });
    }

    public String getBase64FromPath(File file) {
        String base64 = "";
        try {
            byte[] buffer = new byte[(int) file.length() + 100];
            int length = new FileInputStream(file).read(buffer);
            base64 = Base64.encodeToString(buffer, 0, length,
                    Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return base64;
    }

    public void onFileUploadComplete(@NonNull SRVideoUploadModule module){
        new SRVideoUploadDBC(context).setUpdateComplete(module.getId());
        try {
            File myFile = new File(module.getPath());
            if (myFile.exists()) {
                myFile.delete();
            }
        }catch (Exception e){
            Log.e("err",e.getMessage());
        }
        indexes.add(module.getIndex());
    }

    public String getFileName(){
        String name = "";
        if(indexes.isEmpty()){
            maxId += 1;
            name = "SRV-"+(maxId);
//            indexes.add(maxId);
            ongoingIndex = maxId;
        }else{
            name = "SRV-"+indexes.get(0);
            ongoingIndex = indexes.get(0);
            indexes.remove(0);
        }

        String path = Environment.getDataDirectory().getPath() + "/data/lk.parinda.safemellow/files/SRV";
        try {
            File myFile = new File(path+"/"+name+".mp4");
            if (myFile.exists()) {
                myFile.delete();
                File file2 = new File(myFile.getAbsolutePath());
                file2.delete();
                if(myFile.exists()){
                    Log.e("delete","error");
                }
            }else{
                Log.e("delete",path+"/"+name+".mp4");
            }
        }catch (Exception e){
            Log.e("err",e.getMessage());
        }
        return name;
    }

}
