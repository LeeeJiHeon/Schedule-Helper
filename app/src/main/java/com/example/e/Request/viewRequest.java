package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class viewRequest extends StringRequest {

    //서버 URL 설정 (PHP 파일 연동)
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/post.php";
    private Map<String, String> map;


    public viewRequest(int BBS_NO, Response.Listener<String> listener) {
        super(Request.Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("BBS_NO",BBS_NO + "");

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }

}