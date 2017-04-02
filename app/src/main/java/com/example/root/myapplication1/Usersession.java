package com.example.root.myapplication1;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by root on 1/4/17.
 */

class Usersession {
    SharedPreferences pref;
    SharedPreferences.Editor editor;
    Context context;
    int pm=0;
    private static final String prefername="Reg";
    private static final String isuserlogin="isuserlogin";
    private static final String keyname="name";
    private static final String keyemail="email";
    private static final String keyphonenumber="phonenumber";
    private static final String keyhr="homeradius";
    private static final String keylati="latitude";
    private static final String keylongi="longitude";
    public Usersession(Context context) {
        this.context = context;
        pref = context.getSharedPreferences(prefername, pm);
        // editor=pref.edit();
        editor = pref.edit();
    }
        public void createUserLoginSession(String uname,String umail,String uphn,String uhr,String ulati,String ulongi)
        {
            editor.putBoolean(isuserlogin,true);
            editor.putString(keyname,uname);
            editor.putString(keyemail,umail);
            editor.putString(keyphonenumber,uphn);
            editor.putString(keyhr,uhr);
            editor.putString(keylati,ulati);
            editor.putString(keylongi,ulongi);
            editor.commit();

        }
        public String getname(){
           String uname = pref.getString("name", "");
            return uname;
    }
    public String getemail(){
        String uemail = pref.getString("email", "");
        return uemail;
    }
    public void sethome(double d1,double d2){
        String s1=Double.toString(d1);
        String s2=Double.toString(d2);
        editor.putString(keylati,s1);
        editor.putString(keylongi,s2);
    }
    public String gethr(){
        String uhr=pref.getString("homeradius","");
        return uhr;
    }
    public String getlati(){
        String ulati=pref.getString("latitude","");
        return ulati;
    }
    public String getlongi(){
        String ulongi=pref.getString("longitude","");
        return ulongi;
    }
    public boolean checklogin(){
        if (!this.isuserlogin()) {
            return  true;
        }
        else
            return false;
    }
    public boolean isuserlogin(){
        return pref.getBoolean(isuserlogin,false);
    }
    public void logoutuser(){
        editor.clear();
        editor.commit();
    }
}

