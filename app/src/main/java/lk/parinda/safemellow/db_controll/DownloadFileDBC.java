package lk.parinda.safemellow.db_controll;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class DownloadFileDBC {

    private SQLiteDatabase db;
    private final DatabaseHelper dbHelper;
    private final String TAG="DownloadFileDBC";

    public DownloadFileDBC(Context context) {
        dbHelper = new DatabaseHelper(context);
    }

    public void open() throws SQLException {
        try {
            dbHelper.close();
        }catch (Exception e){
            System.err.println(e.getMessage());
        }finally {
            db = dbHelper.getWritableDatabase();
        }
    }

    public int insert(String path){
        int id=0;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DownloadTableStrings.path.toString(), path);
            id = (int) db.insert(DatabaseHelper.DownloadTableStrings.download_file_table.toString(), null, values);
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
        return id;
    }

    public int delete(String path){
        int id=0;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        try {
            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.DownloadTableStrings.path.toString(), path);
            id = (int) db.delete(DatabaseHelper.DownloadTableStrings.download_file_table.toString(),
                    DatabaseHelper.DownloadTableStrings.path + " =?",new String[]{String.valueOf(path)});
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
        return id;
    }

    public boolean isFileUploaded(String file){
        boolean id=false;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        try {

            String qur = "SELECT * FROM "+DatabaseHelper.DownloadTableStrings.download_file_table+" WHERE "
                    +DatabaseHelper.DownloadTableStrings.path+" = '"+file+"'";

            Cursor cursor = db.rawQuery(qur, null);
            id = cursor.getCount()>0;
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
        return id;
    }
}
