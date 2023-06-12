package com.example.e.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest{

    final static private String URL = "http://dlwfllgjs.dothome.co.kr/UserValidate.php";
    private Map<String, String> paramaters;

    public ValidateRequest(String userID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        paramaters = new HashMap<>();
        paramaters.put("userID", userID);
    }

    @Override
    public Map<String,String> getParams() {
        return paramaters;
    }
}
