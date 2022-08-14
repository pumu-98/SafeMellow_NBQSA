package lk.parinda.safemellow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.FileObserver;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import lk.parinda.safemellow.db_controll.DownloadFileDBC;
import lk.parinda.safemellow.download_checker.DownloadFileController;

public class StartDownloadCheckActivity extends AppCompatActivity {

    int WRITE_CODE = 100;
    static FileObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_download_check);

        findViewById(R.id.button10).setOnClickListener(view -> {
            checkPermissions();
            String sdcardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
            if(observer!=null){
                observer.stopWatching();
            }
            observer = createFileObserver(sdcardPath);
            observer.startWatching();
            Toast.makeText(getApplicationContext(), "service stared", Toast.LENGTH_SHORT).show();
        });

        findViewById(R.id.button11).setOnClickListener(view -> {
            if(observer!=null){
                observer.stopWatching();
                Toast.makeText(getApplicationContext(), "service stopped", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private FileObserver createFileObserver(String path) {
        return new FileObserver(path) {
            @Override
            public void onEvent(int event, String fileName) {

                if(event == FileObserver.CLOSE_NOWRITE){
                    File file2 = new File(path, fileName);
                    long fileSizeInBytes = file2.length();
                    long fileSizeInKB = fileSizeInBytes / 1024;
                    long fileSizeInMB = fileSizeInKB / 1024;
                    if(file2.exists() && file2.canRead()) {
                        new DownloadFileController(StartDownloadCheckActivity.this).uploadFile(file2,fileName);
                    }
                }

                if(event == FileObserver.DELETE || event == FileObserver.DELETE_SELF){
                    new DownloadFileDBC(StartDownloadCheckActivity.this).delete(fileName);
                }
            }
        };
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == WRITE_CODE) {
            checkPermissions();
        }
    }

    private void checkPermissions() {

        String[] permissions = new String[3];
        permissions[0]= Manifest.permission.READ_EXTERNAL_STORAGE;
        permissions[1]=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
            permissions[1]=Manifest.permission.MANAGE_EXTERNAL_STORAGE;
        }

        List<String> listPermissionsNeeded = new ArrayList<>();
        for (String p : permissions) {
            if (p!=null  && ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
                listPermissionsNeeded.add(p);
            }
        }
        if (!listPermissionsNeeded.isEmpty()) {
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), WRITE_CODE);
        }
    }
}