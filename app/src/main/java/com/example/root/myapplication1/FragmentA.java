package com.example.root.myapplication1;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by root on 30/3/17.
 */

public class FragmentA extends Fragment {
    String tag="abc";
    public TextView t1;
    MapViewFragment fr;

    @Override
    public View onCreateView(LayoutInflater inflater,  ViewGroup container,  Bundle savedInstanceState) {

        View view =inflater.inflate(R.layout.fragment_one,container,false);
        t1=(TextView)view.findViewById(R.id.textView3);
        fr=new MapViewFragment();
        double d=fr.distance;
        double e=fr.rad;

        setdist(d-e);


       /* view.setFocusableInTouchMode(true);
        view.requestFocus();
        view.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                Log.i(tag, "keyCode: " + keyCode);
                if( keyCode == KeyEvent.KEYCODE_BACK ) {
                    Log.i(tag, "onKey Back listener is working!!!");
                    getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
                    return true;
                } else {
                    return false;
                }
            }
        });*/
        return view;
    }
    public void setdist(double a){
        String s=Double.toString(a);
       t1.setText(s+ " metres");
    }

}
