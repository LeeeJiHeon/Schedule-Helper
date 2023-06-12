package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardListRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/BBSBoard.php";
    private Map<String, String> map;

    public BoardListRequest(Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}