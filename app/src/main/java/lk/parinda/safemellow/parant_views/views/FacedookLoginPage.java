package lk.parinda.safemellow.parant_views.views;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

import lk.parinda.safemellow.MySingleton;
import lk.parinda.safemellow.R;

public class FacedookLoginPage extends AppCompatActivity {

    private static final String EMAIL = "email";
    CallbackManager callbackManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_facedook_login_page);

        callbackManager = CallbackManager.Factory.create();

        LoginButton loginButton = (LoginButton) findViewById(R.id.login_button);
        loginButton.setReadPermissions(Arrays.asList(
                "email","instagram_basic","instagram_basic",
                "business_management","ads_management","instagram_manage_comments","pages_show_list","pages_read_engagement"));
        LoginManager.getInstance().logOut();
        // Callback registration
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.e("ewre",loginResult.getAccessToken().getToken());
                getToken(loginResult.getAccessToken().getToken());
            }

            @Override
            public void onCancel() {
                // App code
            }

            @Override
            public void onError(FacebookException exception) {
                Log.e("ewre",exception.getMessage());
                // App code
            }
        });
    }

    public void requestData(String token){
        String url = "http://ec2-54-245-147-254.us-west-2.compute.amazonaws.com:4000/user/posts/?id="+token;
        StringRequest jsonObjectRequest = new StringRequest(Request.Method.GET, url,
                response -> {
                    if (response!=null){
                        Toast.makeText(this,"Precess Success",Toast.LENGTH_LONG).show();
                    }
                },
                e -> {
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(FacedookLoginPage.this).addToRequestQueue(jsonObjectRequest);
    }

    public void getToken(String token){
        String url = "https://graph.facebook.com/oauth/access_token?grant_type=fb_exchange_token&client_id="+getResources().getString(R.string.facebook_app_id)+"&client_secret="+getResources().getString(R.string.facebook_app_secret)+"&fb_exchange_token="+token;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url,null,
                response -> {
                    if (response!=null){
                        System.err.println(response);
                        Toast.makeText(this,"Precess Success",Toast.LENGTH_LONG).show();
                        if(response.has("access_token")){
                            try {
                                requestData(response.getString("access_token"));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }else{
                            System.err.println("response no tokemn");
                        }
                    }
                },
                e -> {
                    System.err.println(e.getMessage());
                }
        );
        MySingleton.getInstance(FacedookLoginPage.this).addToRequestQueue(jsonObjectRequest);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}