package lk.parinda.safemellow;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import lk.parinda.safemellow.keylog.latin.settings.SettingsActivity;
import lk.parinda.safemellow.parant_views.views.FacedookLoginPage;

public class DashBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board);

        findViewById(R.id.button6).setOnClickListener(view -> {
            Intent intent = new Intent(DashBoard.this, SettingsActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button9).setOnClickListener(view -> {
            Intent intent = new Intent(DashBoard.this, StartDownloadCheckActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button7).setOnClickListener(view -> {
            Intent intent = new Intent(DashBoard.this, ScreenRecodeServiceActivity.class);
            startActivity(intent);
        });

        findViewById(R.id.button8).setOnClickListener(view -> {
            Intent intent = new Intent(DashBoard.this, FacedookLoginPage.class);
            startActivity(intent);
        });
    }
}