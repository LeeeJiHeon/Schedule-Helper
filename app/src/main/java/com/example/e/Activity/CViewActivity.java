package com.example.e.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import com.example.e.R;

import java.util.ArrayList;
import java.util.List;

public class CViewActivity extends AppCompatActivity {

    //받아올 data(사용자가 쓴 글)
    List<String> cdata = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cview);

        //intent 받아오기
        Intent cintent = getIntent();
        //xml에서 담아온 listview 정의
        final ListView listView = (ListView) findViewById(R.id.comment_list);
        //adapter 선언: 리스트 방식과 cdata의 정보를 adapter에
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, cdata);
        //listview와 adapter를 연결
        listView.setAdapter(adapter);

        //listview 테스트
        cdata.add("comm1 - Paul - 2020/6/6");
        cdata.add("comm2 - Mike - 2020/6/7");
        cdata.add("comm3 - Sarah - 2020/6/8");
        cdata.add("comm4 - alpha - 2020/6/9");
        cdata.add("comm5 - bravo - 2020/6/9");
        cdata.add("comm6 - charlie - 2020/6/9");
        cdata.add("comm7 - delta - 2020/6/20");
        cdata.add("comm8 - echo - 2020/6/20");
        cdata.add("comm9 - foxtrot - 2020/6/20");
        cdata.add("comm10 - golf - 2020/6/20");
        cdata.add("comm11 - hotel - 2020/6/20");
        cdata.add("comm12 - india - 2020/6/20");
        cdata.add("comm13 - juliet - 2020/6/20");
        cdata.add("comm14 - kilo - 2020/6/20");
        cdata.add("comm15 - lima - 2020/6/20");

        //상태 저장
        adapter.notifyDataSetChanged();
    }
}