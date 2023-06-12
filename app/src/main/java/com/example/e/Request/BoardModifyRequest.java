package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class BoardModifyRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/BBSModify.php";
    private Map<String, String> map;

    public BoardModifyRequest(String id, String bbs_no, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("userID", id);
        map.put("BBS_NO", bbs_no);
        map.put("CONTENT", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}