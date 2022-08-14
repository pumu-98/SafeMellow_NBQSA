package lk.parinda.safemellow.db_controll;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "safe_mellow_v1.db";

    public static final int DATABASE_VERSION = 1; // add employee key
    Context context = null;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    public enum SRVideoUploadTableStrings{
        sr_video_upload_table,
        id,
        is_upload,
        path,
        index_id
    }

    public enum DownloadTableStrings{
        download_file_table,
        id,
        path
    }

    private static final String CREATE_SR_VIDEO_UPLOAD_TABLE = "CREATE  TABLE IF NOT EXISTS " + SRVideoUploadTableStrings.sr_video_upload_table + " ("
            + SRVideoUploadTableStrings.id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + SRVideoUploadTableStrings.index_id + " INTEGER, "
            + SRVideoUploadTableStrings.is_upload + " INTEGER, "
            + SRVideoUploadTableStrings.path + " TEXT ); ";

    private static final String CREATE_DOWNLOAD_FILE_TABLE = "CREATE  TABLE IF NOT EXISTS " + DownloadTableStrings.download_file_table + " ("
            + DownloadTableStrings.id + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + DownloadTableStrings.path + " TEXT ); ";


//    private static final String DATABASE_ALTER_TEAM_30_1 = "ALTER TABLE "
//            + TABLE_SESSION + " ADD COLUMN " + SESSION_DEDUCTION_MODE + " string  DEFAULT  'HIGH MODE';";


    @Override
    public void onCreate(SQLiteDatabase arg0) {

        String[] queries = new String[]{
                CREATE_SR_VIDEO_UPLOAD_TABLE,CREATE_DOWNLOAD_FILE_TABLE};

        for (String sql : queries)
            arg0.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase arg0, int oldVersion, int newVersion) {

//        if (oldVersion < 30) {
//            arg0.execSQL(DATABASE_ALTER_TEAM_30_1);
//        }

    }

}
