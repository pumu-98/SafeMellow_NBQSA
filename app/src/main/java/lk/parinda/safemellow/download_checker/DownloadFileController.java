package lk.parinda.safemellow.download_checker;

import android.content.Context;
import android.os.Environment;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import lk.parinda.safemellow.LoginActivity;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.SharedPreferencesManager;
import lk.parinda.safemellow.db_controll.DatabaseHelper;
import lk.parinda.safemellow.db_controll.DownloadFileDBC;
import lk.parinda.safemellow.db_controll.SRVideoUploadDBC;
import lk.parinda.safemellow.db_controll.module.SRVideoUploadModule;

public class DownloadFileController {
    private Context context;

    public DownloadFileController(Context context) {
        this.context = context;
    }

    public void uploadFile(File file,String fileName){
        try {
            if (file.exists() && !new DownloadFileDBC(context).isFileUploaded(fileName)) {
                new DownloadFileDBC(context).insert(fileName);
                String base64text = getBase64FromPath(file);
                base64text = "data:image/jpeg;base64," + base64text;
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("base64",base64text);
                jsonObject.put("pId", LoginActivity.pid);
                jsonObject.put("cId",LoginActivity.cid);
                Log.e("ooo", jsonObject.toString());
                String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/upload";
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
            }
        }catch (Exception e){
            e.printStackTrace();
        }
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

}
