package com.example.e.Util;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.Toast;

public class Util {
    // 값 불러오기
    public static String getPreferences(Context context, String key){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        return pref.getString(key, "");
    }

    // 값 저장하기
    public static void savePreferences(Context context, String key, String value){
        SharedPreferences pref = context.getSharedPreferences("pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        editor.putString(key, value);
        editor.commit();
    }

    // 토스트 메시지 출력
    public static void showMsg(Context context, String str){
        Toast.makeText(context, str, Toast.LENGTH_SHORT).show();
    }
}
