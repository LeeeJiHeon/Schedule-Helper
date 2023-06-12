package com.example.e.Request;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class CommentInsertRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/CommentInsert.php";
    private Map<String, String> map;

    public CommentInsertRequest(String board_no, String user_id, String content, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);


        map = new HashMap<>();
        map.put("BBS_NO", board_no);
        map.put("USER_ID", user_id);
        map.put("CONTENT", content);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}