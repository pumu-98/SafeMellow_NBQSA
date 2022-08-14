package lk.parinda.safemellow.parant_views;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Environment;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;

public class RetrieveFeedTask extends AsyncTask<String, Void, File> {

    Context context;

    public RetrieveFeedTask(Context context) {
        this.context = context;
    }

    protected File doInBackground(String... urls) {
        File f = null;
        try {
            URL imageurl = new URL(urls[0]);
            Bitmap bitmap = BitmapFactory.decodeStream(imageurl.openConnection().getInputStream());
            ByteArrayOutputStream bytes = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
            String ll = Environment.getDataDirectory()+ File.separator+"data"+ File.separator+"lk.parinda.safemellow"+ File.separator+"image"
                    + File.separator + "test.jpg";
            f = new File(ll);
            if(f.exists()){
                f.getParentFile().delete();
            }
            f.getParentFile().mkdirs();
            Log.e("file size0","------"+ll);
            Log.e("file size1","------"+bytes.size());
            FileOutputStream fo = new FileOutputStream(f);
            fo.write(bytes.toByteArray());
            fo.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return f;
    }

    protected void onPostExecute(File feed) {
        Log.e("onPostExecute", String.valueOf(feed.length()));
        new UploadTask(context).execute(feed);
    }
}