package com.example.e.Adapter;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.e.Activity.MainActivity;
import com.example.e.Model.Course;
import com.example.e.R;
import com.example.e.Request.AddRequest;
import com.example.e.Model.Schedule;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

public class CourseListAdapter extends BaseAdapter {

    private Context  context;
    private List<Course>  courseList;
    private Fragment parent;
    private String userID = MainActivity.userID;
    private Schedule schedule = new Schedule();
    private List<Integer> courseIDList;
    private ArrayList<Course> auto_couresList = new ArrayList<Course>();
    public static int totalCredit = 0;

    private String[] auto_major = {"건설융합학부", "기계시스템공학부", "소방방재학부", "신소재공학과", "에너지자원화학공학과", "AI소프트웨어학과", "전자정보통신공학과", "지구환경시스템공학과"};
    private String[][] auto_course = {
                                    {"응용역학", "건설재료기초", "철근콘크리트공학1", "구조역학1","건설시공학", "진로탐색과꿈-설계"}, //1
                                    {"고체역학", "기계진동학", "기계가공학", "3D모델링", "기계요소설계1", "창의적공학설계", "진로탐색과꿈-설계"}, //2
                                    {"소방학개론", "소방CAD", "응급처치론", "기초소방전기", "소방방재법규해설", "공업수학", "소방화학"}, //3
                                    {"에너지소재공학", "재료물리화학", "재료열역학1", "유기재료화학", "금속열처리및설계", "신소재금속조직학", "진로탐색과꿈설계"}, //4
                                    {"유기화학1", "재료과학", "일반화학", "석유생산공학", "에너지단위조작1", "석유생산공학", "반응공학1", "진로탐색과꿈설계"}, //5
                                    {"최적화및기계학습", "딥러닝을위한수치해석", "확률및통계", "머신러닝을위한선형대수", "AI소프트웨어개론", "진로탐색과꿈설계"}, //7
                                    {"벤처캡스톤디자인", "창의적입문설계", "확률과인공지능", "전자기학", "디지털시스템설계", "공업수학"}, //8
                                    {"대기오염방지공학1", "폐기물처리공학1", "폐기물처리공학1", "수질오염개론", "수질분석실험", "환경양론", "진로탐색과꿈설계"}, //9
                                };

    public CourseListAdapter(Context context, List<Course> courseList, Fragment parent){
        this.context = context;
        this.courseList = courseList;
        this.parent = parent;
        schedule = new Schedule();
        courseIDList = new ArrayList<Integer>();
        new BackgroundTask().execute();
        totalCredit = 0;
    }

    @Override
    public int getCount() { return courseList.size(); }

    @Override
    public Object getItem(int i) { return courseList.get(i); }

    @Override
    public long getItemId(int i) { return i; }

    @Override
    public View getView(final int i, View view, ViewGroup viewGroup) {
        View v = View.inflate(context, R.layout.course, null); //xml 에 씌여져 있는 view 의 정의를 실제 view 객체로 만드는 역할
        TextView courseGrade=(TextView)v.findViewById(R.id.courseGrade);
        TextView courseTitle=(TextView)v.findViewById(R.id.courseTitle);
        TextView courseCredit=(TextView) v.findViewById(R.id.courseCredit);
        TextView courseDivide=(TextView)v.findViewById(R.id.courseDivide);
        TextView coursePersonnel=(TextView)v.findViewById(R.id.coursePersonnel);
        TextView courseProfessor=(TextView)v.findViewById(R.id.courseProfessor);
        TextView courseTime=(TextView)v.findViewById(R.id.courseTime);

        if(i == 0){
            courseTitle.setText("자동완성");
            courseGrade.setText("");
            courseDivide.setText("");
            coursePersonnel.setText("");
            courseProfessor.setText("자동으로 강의가 등록됩니다.");
            courseCredit.setText("");
            courseTime.setText("");
        }else{
            if(courseList.get(i).getCourseGrade().equals("제한 없음")|| courseList.get(i).getCourseGrade().equals(""))
            {
                courseGrade.setText("모든 학년");
            }
            else
            {
                courseGrade.setText(courseList.get(i).getCourseGrade() + "학년");
            }
            courseTitle.setText(courseList.get(i).getCourseTitle());
            courseCredit.setText(courseList.get(i).getCourseCredit() + "학점");
            courseDivide.setText(courseList.get(i).getCourseDivide() + "분반");

            if(courseList.get(i).getCoursePersonnel() == 0)
            {
                coursePersonnel.setText("인원 제한 없음");
            }
            else
            {
                coursePersonnel.setText("제한 인원 : " + courseList.get(i).getCoursePersonnel()+"명");
            }
            if (courseList.get(i).getCourseProfessor().equals(""))
            {
                courseProfessor.setText("개인 연구");
            }
            else
            {
                courseProfessor.setText(courseList.get(i).getCourseProfessor() + "교수");
            }
            courseTime.setText(courseList.get(i).getCourseTime() + "");
        }
        

        v.setTag(courseList.get(i).getCourseID());

        Button addButton = (Button)v.findViewById(R.id.addButton); //addButton 레이아웃을 가져옴
        addButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                if(i == 0){

                    String major = courseList.get(1).getCourseMajor();
                    int major_index = -1;
                    for (int i = 0; i < auto_major.length; i++){
                        if(major.equals(auto_major[i])){
                            major_index = i;
                        }
                    }

                    auto_couresList.clear();
                    for(int j = 0; j < auto_course[major_index].length -1; j++){
                        auto_couresList.add(courseList.get(find_course(auto_course[major_index][j])));
                    }
                    auto_add();
                }else{
                    add_course(courseList.get(i));
                }

            }
        });
        return v;
    }

    class BackgroundTask extends AsyncTask<Void, Void, String>
    {
        String target;

        @Override
        protected void onPreExecute() {
            try
            {
                target = "http://dlwfllgjs.dothome.co.kr/ScheduleList.php?userID=" + URLEncoder.encode(userID, "UTF-8");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        protected String doInBackground(Void... voids) {
            try{
                URL url = new URL(target);
                HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
                InputStream inputStream = httpURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                String temp;
                StringBuilder stringBuilder = new StringBuilder();
                while((temp = bufferedReader.readLine())!=null)
                {
                    stringBuilder.append(temp + "\n");
                }
                bufferedReader.close();
                inputStream.close();
                httpURLConnection.disconnect();
                return stringBuilder.toString().trim();
            }catch(Exception e) {
                e.printStackTrace();
            }
            return null;
        }


        @Override
        public void onProgressUpdate(Void... values)
        {
            super.onProgressUpdate();
        }
        @Override
        public void onPostExecute(String result)
        {
            try{
                JSONObject jsonObject = new JSONObject(result);
                JSONArray jsonArray = jsonObject.getJSONArray("response");
                int count = 0;
                String courseProfessor;
                String courseTime;
                int courseID;
                totalCredit = 0;
                while(count < jsonArray.length()){
                    JSONObject object = jsonArray.getJSONObject(count);
                    courseID = object.getInt("courseID");
                    courseProfessor = object.getString("courseProfessor");
                    courseTime = object.getString("courseTime");
                    totalCredit += object.getInt("courseCredit");
                    courseIDList.add(courseID); //현재 해당 사용자가 가지고 있는 모든 시간표 데이터에 있는 강의 ID 값이 담김
                    schedule.addSchedule(courseTime);
                    count++;
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }


    public boolean alreadyIn(List<Integer> courseIDList, int item) {
        for(int i = 0; i < courseIDList.size(); i++)
        {
            if(courseIDList.get(i) == item)
            {
                return false;
            }
        }
        return true;
    }

    // 원하는 course의 index를 찾기위한 메소드
    private int find_course(String title){
        for (int i = 1; i < courseList.size(); i++){
            if(courseList.get(i).getCourseTitle().equals(title)){
                //Log.d("TEST", String.valueOf(i));
                //Log.d("TEST", courseList.get(i).getCourseTitle());
                return i;
            }
        }
        return -1;
    }

    private void add_course(Course course){
        boolean validate = false;
        validate = schedule.validate(course.getCourseTime()); //현재 시간표에 강의를 넣음으로써 유효성을 검증
        if (!alreadyIn(courseIDList, course.getCourseID()))
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
            AlertDialog dialog = builder.setMessage("이미 추가한 강의입니다.")
                    .setPositiveButton("다시 시도", null)
                    .create();
            dialog.show();
        }
        else if(totalCredit + course.getCourseCredit() > 20)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
            AlertDialog dialog = builder.setMessage("20학점을 초과할 수 없습니다.")
                    .setPositiveButton("다시 시도", null)
                    .create();
            dialog.show();
        }
        else if (validate == false)
        {
            AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
            AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                    .setPositiveButton("다시 시도", null)
                    .create();
            dialog.show();
        }
        else {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {
                            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(parent.getActivity());
                            //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                            AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                    .setPositiveButton("확인", null)
                                    .create();
                            dialog.show();
                            courseIDList.add(course.getCourseID());
                            schedule.addSchedule(course.getCourseTime());
                            totalCredit += course.getCourseCredit();
                        } else {
                            androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                            //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                            AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                    .setNegativeButton("확인", null)
                                    .create();
                            dialog.show();
                        } //중복체크에 실패했다면(사용할 수 없는 아이디라면)
                    } catch (Exception e) {
                        e.printStackTrace();
                    } //response를 다시 받을 수 있도록
                }
            };
            AddRequest addRequest = new AddRequest(userID, course.getCourseID() + "", responseListener);
            RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
            queue.add(addRequest);
        }
    }

    public void auto_add(){

        for ( int i = 0 ; i < auto_couresList.size(); i++){
            Course course = auto_couresList.get(i);
            boolean validate = false;
            validate = schedule.validate(course.getCourseTime()); //현재 시간표에 강의를 넣음으로써 유효성을 검증
            if (!alreadyIn(courseIDList, course.getCourseID()))
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                AlertDialog dialog = builder.setMessage(course.getCourseTitle() + "\n이미 추가한 강의입니다.")
                        .setPositiveButton("다시 시도", null)
                        .create();
                dialog.show();
            }
            else if(totalCredit + course.getCourseCredit() > 20)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                AlertDialog dialog = builder.setMessage("20학점을 초과할 수 없습니다.")
                        .setPositiveButton("다시 시도", null)
                        .create();
                dialog.show();

            }
            else if (validate == false)
            {
                AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                AlertDialog dialog = builder.setMessage("시간표가 중복됩니다.")
                        .setPositiveButton("다시 시도", null)
                        .create();
                dialog.show();
            }
            else {
                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");
                            if (success) {
                                androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(parent.getActivity());
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                AlertDialog dialog = builder.setMessage("강의가 추가되었습니다.")
                                        .setPositiveButton("확인", null)
                                        .create();
                                dialog.show();
                                courseIDList.add(course.getCourseID());
                                schedule.addSchedule(course.getCourseTime());
                                totalCredit += course.getCourseCredit();
                            } else {
                                androidx.appcompat.app.AlertDialog.Builder builder = new AlertDialog.Builder(parent.getActivity());
                                //아이디가 빈공간일때 예외를 처리해 다시 작성하게끔 해줌
                                AlertDialog dialog = builder.setMessage("강의 추가에 실패하였습니다.")
                                        .setNegativeButton("확인", null)
                                        .create();
                                dialog.show();
                            } //중복체크에 실패했다면(사용할 수 없는 아이디라면)
                        } catch (Exception e) {
                            e.printStackTrace();
                        } //response를 다시 받을 수 있도록
                    }
                };
                AddRequest addRequest = new AddRequest(userID, course.getCourseID() + "", responseListener);
                RequestQueue queue = Volley.newRequestQueue(parent.getActivity());
                queue.add(addRequest);
            }
        }

    }

}
