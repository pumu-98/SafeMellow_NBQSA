package lk.parinda.safemellow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        EditText pName = findViewById(R.id.p_name);
        EditText cName = findViewById(R.id.c_name);
        EditText pass = findViewById(R.id.p_password);
        EditText passCon = findViewById(R.id.p_repassword);

//        findViewById(R.id.swipeLeft).setOnClickListener(view -> {
//            finish();
//        });

        findViewById(R.id.login).setOnClickListener(view -> {
            Intent intent = new Intent(Register.this,LoginActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.btn_register).setOnClickListener(view -> {
            RequestParams params = new RequestParams();
            params.put("pname", pName.getText().toString().trim());
            params.put("cname", cName.getText().toString().trim());
            params.put("password", pass.getText().toString().trim());
            params.put("comfirmPassword", passCon.getText().toString().trim());

            AsyncHttpClient client = new AsyncHttpClient();
            client.post("http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/register", params, new JsonHttpResponseHandler() {
                @Override
                public void onStart() {
                    Log.e("request"," start");
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                    Log.e("request", String.valueOf(statusCode));
                    if(statusCode==200){
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Something Wrong! Please Check", Toast.LENGTH_LONG).show();
                    }
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    Log.e("request", String.valueOf(statusCode));
                    if(statusCode==200){
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Something Wrong! Please Check", Toast.LENGTH_LONG).show();
                    }
                    super.onFailure(statusCode, headers, responseString, throwable);
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    Log.e("request", String.valueOf(statusCode));
                    if(statusCode==200){
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Something Wrong! Please Check", Toast.LENGTH_LONG).show();
                    }
                    super.onFailure(statusCode, headers, throwable, errorResponse);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, String responseString) {
                    super.onSuccess(statusCode, headers, responseString);
                    Log.e("request",responseString);
                    if(statusCode==200){
                        finish();
                    }else{
                        Toast.makeText(Register.this, "Something Wrong! Please Check", Toast.LENGTH_LONG).show();
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