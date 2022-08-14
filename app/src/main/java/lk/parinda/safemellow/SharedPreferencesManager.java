package lk.parinda.safemellow;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.io.File;
import java.util.Objects;

public class SharedPreferencesManager {

    public static String ipAddress = "192.168.43.173";

    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String Name = "fileNames";
    public static final String LastName = "lastFileName";
    SharedPreferences sharedpreferences;

    public SharedPreferencesManager(Context context) {
        this.sharedpreferences = context.getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);;
    }

    public void saveFile(String name){
        String all = sharedpreferences.getString(Name,"");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(Objects.equals(all, "")){
            all = name;
        }else {
            all += "," + name;
        }
        editor.putString(Name, all);
        editor.apply();
    }

    public void removeFile(String name){
        String all = sharedpreferences.getString(Name,"");
        SharedPreferences.Editor editor = sharedpreferences.edit();
        if(all.contains(name)){
            all.replace(name,"");
        }else if(all.contains(","+name)){
            all.replace(","+name,"");
        }
        editor.putString(Name, all);
        editor.apply();
    }

    public String readFileNames(){
        return sharedpreferences.getString(Name,"");
    }

    public void setLastName(String name){
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putString(LastName, name);
        editor.apply();
    }

    public String getLastName(){
        if(sharedpreferences.contains(LastName)){
            return sharedpreferences.getString(LastName, "");
        }
        return null;
    }
}
