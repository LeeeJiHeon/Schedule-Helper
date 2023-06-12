package com.example.e.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class LoginRequest extends StringRequest {
    final static private String URL = "http://dlwfllgjs.dothome.co.kr/UserLogin.php";
    private Map<String, String> paramaters;

    public LoginRequest (String userID, String userPassword, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        paramaters = new HashMap<>();
        paramaters.put("userID", userID);
        paramaters.put("userPassword", userPassword);
    }

    @Override
    public Map<String,String> getParams() {
        return paramaters;
    }
}
