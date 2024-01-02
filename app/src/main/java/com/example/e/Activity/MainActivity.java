package com.example.e.Activity;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.e.Adapter.NoticeListAdapter;
import com.example.e.Fragment.ScheduleFragment;
import com.example.e.Fragment.BoardFragment;
import com.example.e.Fragment.CourseFragment;
import com.example.e.Fragment.StatisticsFragment;
import com.example.e.Model.Notice;
import com.example.e.R;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ListView noticeListView;
    private NoticeListAdapter adapter;
    private List<Notice> noticeList;
    public static String userID;
    private boolean isEnglish = false;
    private int page = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT); //스마트폰 화면을 세로로 고정시킴

        userID = getIntent().getStringExtra("userID");
        noticeListView = (ListView) findViewById(R.id.noticeListView);
        noticeList = new ArrayList<Notice>();
        adapter = new NoticeListAdapter(getApplicationContext(), noticeList);
        noticeListView.setAdapter(adapter);


        final Button courseButton = (Button) findViewById(R.id.courseButton);
        final Button scheduleButton = (Button) findViewById(R.id.scheduleButton);
        final Button statisticsButton = (Button) findViewById(R.id.statisticsButton);
        final Button boardButton = (Button) findViewById(R.id.boardButton);
        final ImageButton languageButton = (ImageButton) findViewById(R.id.languageButton);
        final LinearLayout notice = (LinearLayout) findViewById(R.id.notice);
        TextView map_information = (TextView)findViewById(R.id.map_information);
        map_information.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, Pop2.class));
            }
        });

        courseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setTextColor(getResources().getColor(R.color.white));
                scheduleButton.setTextColor(getResources().getColor(R.color.gray));
                boardButton.setTextColor(getResources().getColor(R.color.gray));
                statisticsButton.setTextColor(getResources().getColor(R.color.gray));

                Drawable courseIcon = getResources().getDrawable(R.drawable.ic_course);
                courseIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                courseButton.setCompoundDrawablesWithIntrinsicBounds(null, courseIcon, null, null);

                Drawable scheduleIcon = getResources().getDrawable(R.drawable.ic_schedule);
                scheduleIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                scheduleButton.setCompoundDrawablesWithIntrinsicBounds(null, scheduleIcon, null, null);

                Drawable boardIcon = getResources().getDrawable(R.drawable.ic_board);
                boardIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                boardButton.setCompoundDrawablesWithIntrinsicBounds(null, boardIcon, null, null);

                Drawable statisticsIcon = getResources().getDrawable(R.drawable.ic_statistics);
                statisticsIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                statisticsButton.setCompoundDrawablesWithIntrinsicBounds(null, statisticsIcon, null, null);


                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new CourseFragment());
                fragmentTransaction.commit();
                page = 1;
            }
        });
        scheduleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setTextColor(getResources().getColor(R.color.gray));
                scheduleButton.setTextColor(getResources().getColor(R.color.white));
                boardButton.setTextColor(getResources().getColor(R.color.gray));
                statisticsButton.setTextColor(getResources().getColor(R.color.gray));

                Drawable courseIcon = getResources().getDrawable(R.drawable.ic_course);
                courseIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                courseButton.setCompoundDrawablesWithIntrinsicBounds(null, courseIcon, null, null);

                Drawable scheduleIcon = getResources().getDrawable(R.drawable.ic_schedule);
                scheduleIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                scheduleButton.setCompoundDrawablesWithIntrinsicBounds(null, scheduleIcon, null, null);

                Drawable boardIcon = getResources().getDrawable(R.drawable.ic_board);
                boardIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                boardButton.setCompoundDrawablesWithIntrinsicBounds(null, boardIcon, null, null);

                Drawable statisticsIcon = getResources().getDrawable(R.drawable.ic_statistics);
                statisticsIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                statisticsButton.setCompoundDrawablesWithIntrinsicBounds(null, statisticsIcon, null, null);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new ScheduleFragment());
                fragmentTransaction.commit();
                page = 2;
            }
        });

        boardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setTextColor(getResources().getColor(R.color.gray));
                scheduleButton.setTextColor(getResources().getColor(R.color.gray));
                boardButton.setTextColor(getResources().getColor(R.color.white));
                statisticsButton.setTextColor(getResources().getColor(R.color.gray));

                Drawable courseIcon = getResources().getDrawable(R.drawable.ic_course);
                courseIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                courseButton.setCompoundDrawablesWithIntrinsicBounds(null, courseIcon, null, null);

                Drawable scheduleIcon = getResources().getDrawable(R.drawable.ic_schedule);
                scheduleIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                scheduleButton.setCompoundDrawablesWithIntrinsicBounds(null, scheduleIcon, null, null);

                Drawable boardIcon = getResources().getDrawable(R.drawable.ic_board);
                boardIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                boardButton.setCompoundDrawablesWithIntrinsicBounds(null, boardIcon, null, null);

                Drawable statisticsIcon = getResources().getDrawable(R.drawable.ic_statistics);
                statisticsIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                statisticsButton.setCompoundDrawablesWithIntrinsicBounds(null, statisticsIcon, null, null);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new BoardFragment());
                fragmentTransaction.commit();
                page = 3;
            }
        });
        statisticsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                notice.setVisibility(View.GONE);
                courseButton.setTextColor(getResources().getColor(R.color.gray));
                scheduleButton.setTextColor(getResources().getColor(R.color.gray));
                boardButton.setTextColor(getResources().getColor(R.color.gray));
                statisticsButton.setTextColor(getResources().getColor(R.color.white));

                Drawable courseIcon = getResources().getDrawable(R.drawable.ic_course);
                courseIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                courseButton.setCompoundDrawablesWithIntrinsicBounds(null, courseIcon, null, null);

                Drawable scheduleIcon = getResources().getDrawable(R.drawable.ic_schedule);
                scheduleIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                scheduleButton.setCompoundDrawablesWithIntrinsicBounds(null, scheduleIcon, null, null);

                Drawable boardIcon = getResources().getDrawable(R.drawable.ic_board);
                boardIcon.setColorFilter(getResources().getColor(R.color.gray), PorterDuff.Mode.SRC_ATOP);
                boardButton.setCompoundDrawablesWithIntrinsicBounds(null, boardIcon, null, null);

                Drawable statisticsIcon = getResources().getDrawable(R.drawable.ic_statistics);
                statisticsIcon.setColorFilter(getResources().getColor(R.color.white), PorterDuff.Mode.SRC_ATOP);
                statisticsButton.setCompoundDrawablesWithIntrinsicBounds(null, statisticsIcon, null, null);

                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.fragment, new StatisticsFragment());
                fragmentTransaction.commit();
                page = 4;
            }
        });

        languageButton.setOnClickListener(new View.OnClickListener() {
            private void updateScreenLanguage() {
                if (isEnglish) {
                    // 메인 화면
                    Button courseButton = findViewById(R.id.courseButton);
                    courseButton.setText(getString(R.string.korean_course_button_text));
                    courseButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    Button scheduleButton = findViewById(R.id.scheduleButton);
                    scheduleButton.setText(getString(R.string.korean_schedule_button_text));
                    scheduleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    Button boardButton = findViewById(R.id.boardButton);
                    boardButton.setText(getString(R.string.korean_board_button_text));
                    boardButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    Button statisticsButton = findViewById(R.id.statisticsButton);
                    statisticsButton.setText(getString(R.string.korean_statistics_button_text));
                    statisticsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);

                    TextView noticeText = findViewById(R.id.main_notice);
                    noticeText.setText(getString(R.string.korean_notice_text));

                    TextView map_information = findViewById(R.id.map_information);
                    map_information.setText(getString(R.string.korean_map_information_text));

                    //강의 목록
                    /*TextView monday = findViewById(R.id.monday);
                    monday.setText(getString(R.string.korean_monday_text));

                    TextView tuesday = findViewById(R.id.tuesday);
                    tuesday.setText(getString(R.string.korean_tuesday_text));

                    TextView wednesday = findViewById(R.id.wednesday);
                    wednesday.setText(getString(R.string.korean_wednesday_text));

                    TextView thursday = findViewById(R.id.thursday);
                    thursday.setText(getString(R.string.korean_thursday_text));

                    TextView friday = findViewById(R.id.friday);
                    friday.setText(getString(R.string.korean_friday_text));*/


                    // Repeat the above steps for all other views you want to update

                    // Set isEnglish to false to indicate the language is Korean
                    isEnglish = false;
                } else {
                    //메인 화면
                    Button courseButton = findViewById(R.id.courseButton);
                    courseButton.setText(getString(R.string.english_course_button_text));
                    courseButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                    Button scheduleButton = findViewById(R.id.scheduleButton);
                    scheduleButton.setText(getString(R.string.english_schedule_button_text));
                    scheduleButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                    Button boardButton = findViewById(R.id.boardButton);
                    boardButton.setText(getString(R.string.english_board_button_text));
                    boardButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                    Button statisticsButton = findViewById(R.id.statisticsButton);
                    statisticsButton.setText(getString(R.string.english_statistics_button_text));
                    statisticsButton.setTextSize(TypedValue.COMPLEX_UNIT_SP, 11);

                    TextView noticeText = findViewById(R.id.main_notice);
                    noticeText.setText(getString(R.string.english_notice_text));

                    TextView map_information = findViewById(R.id.map_information);
                    map_information.setText(getString(R.string.english_map_information_text));
                    map_information.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);


                    // 강의 목록
                    /*TextView monday = findViewById(R.id.monday);
                    monday.setText(getString(R.string.english_monday_text));

                    TextView tuesday = findViewById(R.id.tuesday);
                    tuesday.setText(getString(R.string.english_tuesday_text));

                    TextView wednesday = findViewById(R.id.wednesday);
                    wednesday.setText(getString(R.string.english_wednesday_text));

                    TextView thursday = findViewById(R.id.thursday);
                    thursday.setText(getString(R.string.english_thursday_text));

                    TextView friday = findViewById(R.id.friday);
                    friday.setText(getString(R.string.english_friday_text));*/
                    // Repeat the above steps for all other views you want to update

                    // Set isEnglish to true to indicate the language is English
                    isEnglish = true;
                }
            }

            public void onClick(View v) {
                updateScreenLanguage();
            }
        });
        new BackgroundTask().execute();

    }

    @Override
    protected void onResume() {
        super.onResume();
        if (page == 3){
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragment, new BoardFragment());
            fragmentTransaction.commit();
        }
    }

    class BackgroundTask extends AsyncTask<Void, Void, String> {
        String target;

        @Override
        protected void onPreExecute() {
            target = "http://dlwfllgjs.dothome.co.kr/NoticeList.php";
        }

        @Override
        protected String doInBackground(Void... voids) {
            try {
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while ((temp = bufferedReader.readLine()) != null) {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();

            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate();
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String noticeContent, noticeName, noticeDate;
                while (count < jsonArray.length()) {
                    JSONObject object = jsonArray.getJSONObject(count);
                    noticeContent = object.getString("noticeContent");
                    noticeName = object.getString("noticeName");
                    noticeDate = object.getString("noticeDate");
                    Notice notice = new Notice(noticeContent, noticeName, noticeDate);
                    noticeList.add(notice);
                    adapter.notifyDataSetChanged();
                    count++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    private long lastTimeBackPressed;


    @Override
    public void onBackPressed(){
        if(System.currentTimeMillis()-lastTimeBackPressed<1500){
            finish();
            return;
        }
        Toast.makeText(this, "'뒤로' 버튼을 한번 더 눌러 종료합니다.", Toast.LENGTH_SHORT).show();
        lastTimeBackPressed = System.currentTimeMillis();

    }

}