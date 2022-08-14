package lk.parinda.safemellow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.FileNotFoundException;

import cz.msebera.android.httpclient.Header;
import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;
import lk.parinda.safemellow.parant_views.views.ParentDashBoard;

public class LoginActivity extends AppCompatActivity {

    public static String pid = "",cid="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        EditText uName = findViewById(R.id.p_name);
        EditText pass = findViewById(R.id.c_name);

        findViewById(R.id.singup).setOnClickListener(view -> {
            Intent intent = new Intent(LoginActivity.this,Register.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_register).setOnClickListener(view -> {
            
            RequestParams params = new RequestParams();
            params.put("pname", uName.getText().toString().trim());
            params.put("password", pass.getText().toString().trim());

            AsyncHttpClient client = new AsyncHttpClient();
            client.post("http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/login", params, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    Log.e("request"," start");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    Log.e("request",response.toString());
                    if(statusCode==200) {
                        try {
                            JSONObject object = response.getJSONObject(0);
                            if (object.has("pId")) {
                                pid = object.getString("pId");
                            }
                            if (object.has("cId")) {
                                cid = object.getString("cId");
                            }
                            Intent intent = new Intent(LoginActivity.this, ParentDashBoard.class);
                            startActivity(intent);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }else{
                        Toast.makeText(LoginActivity.this, "Something Wrong! Please Check", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFinish() {
                    Log.e("request"," onFinish");
                }
            });
        });
    }
}