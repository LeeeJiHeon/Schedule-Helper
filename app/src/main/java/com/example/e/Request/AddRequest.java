package com.example.e.Request;

import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class AddRequest extends StringRequest {//회원가입 요청을 보내는 클래스

    final static private String URL="http://dlwfllgjs.dothome.co.kr/CourseAdd.php";
    private Map<String, String> parameters;

    public AddRequest(String userID, String courseID, Response.Listener<String> listener){
        super(Method.POST, URL, listener, null);
        parameters =new HashMap<>();
        parameters.put("userID", userID);
        parameters.put("courseID",courseID);
    } //특정 사용자가 특정 강의를 들었다고 전달해줌

    @Override
    public Map<String, String> getParams() {
        return parameters;
    }
}