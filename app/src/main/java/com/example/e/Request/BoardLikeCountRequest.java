package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardLikeCountRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/BBS_LIKE_COUNT.php";
    private Map<String, String> map;

    public BoardLikeCountRequest( int bbs_no, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("BBS_NO", String.valueOf(bbs_no));
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}