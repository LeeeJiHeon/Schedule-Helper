package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentDeleteRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/CommentDelete.php";
    private Map<String, String> map;

    public CommentDeleteRequest(String comment_id, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("COMMENT_ID", comment_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}