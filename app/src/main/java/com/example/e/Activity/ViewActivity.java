package com.example.e.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.e.Adapter.CommentAdapter;
import com.example.e.Fragment.BoardFragment;
import com.example.e.Model.Board;
import com.example.e.Model.Comment;
import com.example.e.R;
import com.example.e.Request.BoardDeleteRequest;
import com.example.e.Request.BoardLikeRequest;
import com.example.e.Request.BoardModifyRequest;
import com.example.e.Request.CommentInsertRequest;
import com.example.e.Request.CommentListRequest;
import com.example.e.Request.UserMayjorRequest;
import com.example.e.Request.viewRequest;
import com.example.e.Util.Comment_list_reload;
import com.example.e.Util.Util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewActivity extends AppCompatActivity {

    private EditText comment;
    private ImageButton comment_addbutton;
    private TextView titleView,writerView,dateView,contentView;
    private TextView tv_like;

    // 수정 삭제 레이아웃
    private LinearLayout ll_modify;
    private LinearLayout ll_tools;

    // 수정 을 위한 위젯
    private Button btn_modify;
    private Button btn_delete;
    private Button btn_modi_cancle;
    private Button btn_modi_submit;

    private EditText et_content;
    private ListView lv_comments;


    // 게시판 객체
    private Board board;
    int count = 0;

    // 댓글 리스트
    private ArrayList<Comment> commentArrayList;
    private CommentAdapter commentAdapter;

    // 댓글 삭제시 리로드를 위한 인터페이스
    Comment_list_reload comment_list_reload;

    private String user_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);

        //intent 에서 Board 객체 받아옴
        Intent intent = getIntent();
        board = (Board) intent.getSerializableExtra("board");

        // 로그인 유저 아이디 가져옴
        user_id = Util.getPreferences(getApplicationContext(), "login_id");

        // 위젯 바인드
        titleView       = (TextView)findViewById(R.id.titleview);
        writerView      = (TextView)findViewById(R.id.writerview);
        dateView        = (TextView)findViewById(R.id.dateview);
        contentView     = (TextView)findViewById(R.id.contentview);
        ll_modify       = (LinearLayout) findViewById(R.id.ll_board_view_modify);
        ll_tools        = (LinearLayout) findViewById(R.id.ll_board_view_tool);
        et_content      = (EditText) findViewById(R.id.et_board_content);
        btn_modify      = (Button) findViewById(R.id.btn_board_view_modify);
        btn_delete      = (Button) findViewById(R.id.btn_board_view_delete);
        btn_modi_cancle = (Button) findViewById(R.id.btn_board_view_cancle);
        btn_modi_submit = (Button) findViewById(R.id.btn_board_view_modi_submit);
        lv_comments     = (ListView) findViewById(R.id.lv_board_view_comment_list);
        tv_like         = (TextView) findViewById(R.id.tv_board_view_like_count);

        titleView.setText(board.getTitle());
        dateView.setText(board.getDate());
        contentView.setText(board.getContent());
        tv_like.setText(board.getLike_count());

        // 작성자의 학과 가져옴
        getMayjor(board.getWriter());

        // 리로드 인터페이스 선언
        comment_list_reload = new Comment_list_reload() {
            @Override
            public void reload() {
                get_comments();
            }
        };

        commentArrayList = new ArrayList<Comment>();
        commentAdapter = new CommentAdapter(commentArrayList, comment_list_reload);
        lv_comments.setAdapter(commentAdapter);

        // 만약 게시글 작성자와 로그인 유저가 같다면,
        if(user_id.equals(board.getWriter())){
            ll_tools.setVisibility(View.VISIBLE);
        }

        btn_modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_mode();
            }
        });

        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                board_delete();
            }
        });

        btn_modi_cancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_cancle();
            }
        });

        btn_modi_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                modify_submit();
            }
        });



        //좋아요 이미지 버튼
        final ImageButton btn_like = (ImageButton) findViewById(R.id.btn_like);

        btn_like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                board_insert_like();
            }
        });

        //댓글창과 업로드 버튼 키 값 배정
        comment = (EditText)findViewById(R.id.comment);
        comment_addbutton = (ImageButton)findViewById(R.id.add_comment_button);

        //comment_addbutton 클릭 시 수행
        comment_addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                comment_insert();
            }
        });

        // 댓글 가져옴
        get_comments();
    }

    public void getMayjor(String writer_id){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        String mayjor = jsonObject.getString("userMajor");
                        writerView.setText(mayjor);
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        UserMayjorRequest userMayjorRequest = new UserMayjorRequest(writer_id, responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(userMayjorRequest);
    }

    // 수정모드 이벤트
    private void modify_mode(){
        // 원래 Content 위젯의 텍스트를 가져옴
        et_content.setText(contentView.getText().toString());

        // 수정 창 보임
        contentView.setVisibility(View.GONE);
        et_content.setVisibility(View.VISIBLE);

        ll_modify.setVisibility(View.VISIBLE);
        ll_tools.setVisibility(View.GONE);
    }

    // 취소 이벤트
    private void modify_cancle(){
        // 수정 창 안보임
        contentView.setVisibility(View.VISIBLE);
        et_content.setVisibility(View.GONE);

        ll_modify.setVisibility(View.GONE);
        ll_tools.setVisibility(View.VISIBLE);
    }

    // 삭제 이벤트
    private void board_delete(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    Log.d("TEST_DELETE",response);
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        finish();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        BoardDeleteRequest boardDeleteRequest = new BoardDeleteRequest(user_id, board.getNo(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(boardDeleteRequest);
    }
    // 수정하기 이벤트
    private void modify_submit(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        contentView.setText(et_content.getText().toString());
                        modify_cancle();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        BoardModifyRequest boardModifyRequest = new BoardModifyRequest(user_id, board.getNo(), et_content.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(boardModifyRequest);
    }

    // 댓글 추가
    private void comment_insert(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    boolean success = jsonObject.getBoolean("success");
                    if (success) {
                        comment.setText("");
                        get_comments();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        CommentInsertRequest commentInsertRequest = new CommentInsertRequest( board.getNo(), user_id, comment.getText().toString(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(commentInsertRequest);
    }

    // 좋아요
    private void board_insert_like(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_COMMENTS", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        boolean exist = jsonResponse.getBoolean("exist");
                        if(exist){
                            Util.showMsg(getApplicationContext(), "좋아요가 취소되었습니다.");
                            int tmp_count = Integer.parseInt(board.getLike_count()) -1;
                            board.setLike_count(String.valueOf(tmp_count));
                            tv_like.setText(String.valueOf(tmp_count));
                        }else{
                            Util.showMsg(getApplicationContext(), "좋아요가 눌렸습니다.");
                            int tmp_count = Integer.parseInt(board.getLike_count()) +1;
                            board.setLike_count(String.valueOf(tmp_count));
                            tv_like.setText(String.valueOf(tmp_count));
                        }

                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        BoardLikeRequest boardLikeRequest = new BoardLikeRequest(user_id ,board.getNo(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(boardLikeRequest);
    }

    // 댓글 조회
    private void get_comments(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("GET_COMMENTS", response);
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        JSONArray boardArray = jsonResponse.getJSONArray("comments");
                        commentArrayList.clear();
                        for (int i = 0; i < boardArray.length(); i++) {

                            JSONObject boardObject = boardArray.getJSONObject(i);
                            int comment_id = boardObject.getInt("COMMENT_ID");
                            String bbs_no = boardObject.getString("BBS_NO");
                            String userID = boardObject.getString("USER_ID");
                            String content = boardObject.getString("CONTENT");
                            String regDate = boardObject.getString("REG_DATE");
                            String mayjor = boardObject.getString("userMajor");

                            Comment comment = new Comment(String.valueOf(comment_id), bbs_no, userID, content, regDate);
                            comment.setMayjor(mayjor);
                            commentArrayList.add(comment);
                            commentAdapter.notifyDataSetChanged();
                        }
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        //서버로부터 요청
        CommentListRequest commentListRequest = new CommentListRequest(board.getNo(), responseListener);
        RequestQueue queue = Volley.newRequestQueue(ViewActivity.this);
        queue.add(commentListRequest);
    }
}