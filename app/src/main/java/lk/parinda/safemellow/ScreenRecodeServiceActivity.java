package lk.parinda.safemellow;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.projection.MediaProjectionManager;
import android.os.Bundle;
import android.os.Environment;

import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lk.parinda.safemellow.srv.controllers.SRVideoUploadController;

public class ScreenRecodeServiceActivity extends AppCompatActivity implements HBRecorderListener {

    SRVideoUploadController srVideoUploadController = new SRVideoUploadController();
    HBRecorder hbRecorder;
    int SCREEN_RECORD_REQUEST_CODE = 102;
    static boolean needStop=false;
    int resultCode=0;
    Intent data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_screen_recode_service);

        checkPermissions();
        hbRecorder = new HBRecorder(this, this);

        findViewById(R.id.button10).setOnClickListener(view -> {
            File myFile = new File(Environment.getDataDirectory().getPath() + "/data/lk.parinda.safemellow/files/SRV");
            if (!myFile.exists()) {
                myFile.mkdirs();
            }
            srVideoUploadController.init(ScreenRecodeServiceActivity.this);
            hbRecorder.recordHDVideo(false);
            hbRecorder.setOutputPath(Environment.getDataDirectory().getPath() + "/data/lk.parinda.safemellow/files/SRV");
            hbRecorder.isAudioEnabled(false);
            hbRecorder.setVideoFrameRate(24);
            hbRecorder.setScreenDimensions(426, 240);
            hbRecorder.setVideoBitrate(1000000);
            hbRecorder.setVideoEncoder("H264");
            hbRecorder.setOutputFormat("MPEG_4");
            needStop=false;
            startRecode();
        });

        findViewById(R.id.button11).setOnClickListener(view -> {
            needStop=true;
            hbRecorder.stopScreenRecording();
        });
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
            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), SCREEN_RECORD_REQUEST_CODE);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                hbRecorder.startScreenRecording(data, resultCode);
                this.resultCode=resultCode;
                this.data=data;
            }
        }
    }

    @Override
    public void HBRecorderOnStart() {
    }

    @Override
    public void HBRecorderOnComplete() {
        srVideoUploadController.onFileSave(hbRecorder.getFilePath());
        if(!needStop){
            startRecode();
        }
    }

    @Override
    public void HBRecorderOnError(int errorCode, String reason) {
    }


    void startRecode(){
        hbRecorder.setFileName(srVideoUploadController.getFileName());
        if(resultCode==0 || data==null){
            startRecordingScreen();
        }else{
            hbRecorder.startScreenRecording(data, resultCode);
        }
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                hbRecorder.stopScreenRecording();
            }
        }, 10000);
    }

    private void startRecordingScreen() {
        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE);
    }
}