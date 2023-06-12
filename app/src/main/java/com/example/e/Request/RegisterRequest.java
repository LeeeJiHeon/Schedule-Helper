package com.example.e.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;
import java.util.HashMap;
import java.util.Map;

public class RegisterRequest extends StringRequest{

    final static private String URL = "http://dlwfllgjs.dothome.co.kr/UserRegister.php";
    private Map<String, String> paramaters;

    public RegisterRequest(String userID, String userPassword, String userGender, String userMajor, String userEmail, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        paramaters = new HashMap<>();
        paramaters.put("userID", userID);
        paramaters.put("userPassword", userPassword);
        paramaters.put("userGender", userGender);
        paramaters.put("userMajor", userMajor);
        paramaters.put("userEmail", userEmail);
    }

    @Override
    public Map<String,String> getParams() {
        return paramaters;
    }
}
