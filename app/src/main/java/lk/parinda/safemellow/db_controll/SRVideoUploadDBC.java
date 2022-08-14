package lk.parinda.safemellow.db_controll;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.util.ArrayList;

import lk.parinda.safemellow.db_controll.module.SRVideoUploadModule;

public class SRVideoUploadDBC {

    private Context context;
    private SQLiteDatabase db;
    private final DatabaseHelper dbHelper;
    private final String TAG="SRVideoUploadDBC";

    public SRVideoUploadDBC(Context context) {
        this.context = context;
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

    public int insert(int index,String path){
        int id=0;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SRVideoUploadTableStrings.is_upload.toString(), 0);
            values.put(DatabaseHelper.SRVideoUploadTableStrings.index_id.toString(), index);
            values.put(DatabaseHelper.SRVideoUploadTableStrings.path.toString(), path);

            String qur = "SELECT * FROM "+DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table+" WHERE "
                    +DatabaseHelper.SRVideoUploadTableStrings.index_id+" = "+index;

            Cursor cursor = db.rawQuery(qur, null);
            if(cursor.getCount()>0){
                id = db.update(DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table.toString(), values,
                        DatabaseHelper.SRVideoUploadTableStrings.index_id + " =?",new String[]{String.valueOf(index)});
            }else{
                id = (int) db.insert(DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table.toString(), null, values);
            }
            cursor.close();
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
        return id;
    }

    public void setUpdateComplete(int id){
        int newId=0;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }
        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SRVideoUploadTableStrings.is_upload.toString(), 1);

            newId = db.update(DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table.toString(), values,
                    DatabaseHelper.SRVideoUploadTableStrings.id + " =?",new String[]{String.valueOf(id)});

        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
    }

    public void setUpdateCompleteWithError(int id){
        int newId=0;

        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }
        try {

            ContentValues values = new ContentValues();
            values.put(DatabaseHelper.SRVideoUploadTableStrings.is_upload.toString(), 2);

            newId = db.update(DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table.toString(), values,
                    DatabaseHelper.SRVideoUploadTableStrings.id + " =?",new String[]{String.valueOf(id)});

        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            db.close();
        }
    }

    @SuppressLint("Range")
    public ArrayList<Integer> loadIndexes(){

        ArrayList<Integer> value = new ArrayList<>();
        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        Cursor cursor = null;
        Cursor cursor2 = null;

        try {

            String sql_indexes = "SELECT "+DatabaseHelper.SRVideoUploadTableStrings.index_id
                    +" FROM "+ DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table+ " WHERE "+DatabaseHelper.SRVideoUploadTableStrings.is_upload+"=1";

            String sql_maxId = "SELECT max("+DatabaseHelper.SRVideoUploadTableStrings.index_id+") as max_index "
                    +" FROM "+ DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table;

            System.out.println("QURY  " +  sql_indexes);
            cursor = db.rawQuery(sql_indexes, null);
            while (cursor.moveToNext()) {
                int index = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SRVideoUploadTableStrings.index_id.toString()));
                value.add(index);
            }

            System.out.println("QURY  " +  sql_maxId);
            cursor2 = db.rawQuery(sql_maxId, null);
            int max = 0;
            while (cursor2.moveToNext()) {
                max = cursor2.getInt(cursor2.getColumnIndex("max_index"));
            }
            value.add(max);

        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            if(cursor2!=null){
                cursor2.close();
            }
            db.close();
        }

        return value;
    }

    @SuppressLint("Range")
    public ArrayList<SRVideoUploadModule> getAllUnSyncList(){

        ArrayList<SRVideoUploadModule> value = new ArrayList<>();
        if(db == null){
            open();
        }else if(!db.isOpen()){
            open();
        }

        Cursor cursor = null;

        try {

            String sql_weight = "SELECT * FROM "+ DatabaseHelper.SRVideoUploadTableStrings.sr_video_upload_table+ " WHERE "+DatabaseHelper.SRVideoUploadTableStrings.is_upload+"=0";

            cursor = db.rawQuery(sql_weight, null);

            while (cursor.moveToNext()) {
                SRVideoUploadModule module = new SRVideoUploadModule(
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SRVideoUploadTableStrings.id.toString())),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SRVideoUploadTableStrings.is_upload.toString())),
                        cursor.getString(cursor.getColumnIndex(DatabaseHelper.SRVideoUploadTableStrings.path.toString())),
                        cursor.getInt(cursor.getColumnIndex(DatabaseHelper.SRVideoUploadTableStrings.index_id.toString()))
                );
                value.add(module);
            }
        } catch (Exception e) {
            Log.v(TAG, e.toString());
            System.out.println("Error  " +  e);
        }finally {
            if(cursor!=null){
                cursor.close();
            }
            db.close();
        }

        return value;
    }
}
