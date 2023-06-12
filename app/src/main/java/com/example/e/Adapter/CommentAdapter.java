package com.example.e.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.e.Activity.ViewActivity;
import com.example.e.Model.Comment;
import com.example.e.R;
import com.example.e.Request.BoardModifyRequest;
import com.example.e.Request.CommentDeleteRequest;
import com.example.e.Request.CommentInsertRequest;
import com.example.e.Request.CommentModifyRequest;
import com.example.e.Util.Comment_list_reload;
import com.example.e.Util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CommentAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Comment> comments;
    Comment_list_reload comment_list_reload;

    public CommentAdapter(ArrayList<Comment> comments, Comment_list_reload comment_list_reload){
        this.comments = comments;
        this.comment_list_reload = comment_list_reload;
    }


    @Override
    public int getCount() {
        return comments.size();
    }

    @Override
    public Object getItem(int i) {
        return comments.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        if(view == null){
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = layoutInflater.inflate(R.layout.comment_item_list,viewGroup,false);
        }

        Comment comment = comments.get(i);

        TextView tv_writer  = view.findViewById(R.id.tv_board_comment_item_writer);
        TextView tv_date    = view.findViewById(R.id.tv_board_comment_item_date);
        TextView tv_content = view.findViewById(R.id.tv_board_comment_item_content);
        EditText et_content = view.findViewById(R.id.et_board_comment_item_content);

        LinearLayout ll_tool = view.findViewById(R.id.ll_board_comment_item_tool);
        LinearLayout ll_mody = view.findViewById(R.id.ll_board_comment_item_modify);

        Button btn_delete = view.findViewById(R.id.btn_board_comment_item_delete);
        Button btn_modify = view.findViewById(R.id.btn_board_comment_item_modify);
        Button btn_submit = view.findViewById(R.id.btn_board_comment_item_modi_submit);
        Button btn_cancle = view.findViewById(R.id.btn_board_comment_item_cancle);

        //tv_writer.setText(comment.getUser_id());
        tv_writer.setText(comment.getMayjor());
        tv_date.setText(comment.getDate());
        tv_content.setText(comment.getContent());

        if(Util.getPreferences(context,"login_id").equals(comment.getUser_id())){
            ll_tool.setVisibility(View.VISIBLE);
            tv_writer.getResources().getColor(R.color.colorPrimary);
        }

        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RequestQueue queue;
                switch (view.getId()){
                    case R.id.btn_board_comment_item_modify:
                        et_content.setText(tv_content.getText().toString());

                        tv_content.setVisibility(View.GONE);
                        et_content.setVisibility(View.VISIBLE);
                        ll_mody.setVisibility(View.VISIBLE);
                        ll_tool.setVisibility(View.GONE);
                        break;
                    case R.id.btn_board_comment_item_delete:
                        Response.Listener<String> responseListener_delete = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {
                                        comments.remove(i);

                                        //comment_list_reload.reload();
                                        notifyDataSetChanged();
                                    } else {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        //서버로부터 요청
                        CommentDeleteRequest commentDeleteRequest = new CommentDeleteRequest(   comment.getComment_no(),
                                                                                                responseListener_delete);
                        queue = Volley.newRequestQueue(context);
                        queue.add(commentDeleteRequest);

                        break;
                    case R.id.btn_board_comment_item_modi_submit:
                        Response.Listener<String> responseListener = new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                try {
                                    JSONObject jsonObject = new JSONObject(response);
                                    boolean success = jsonObject.getBoolean("success");
                                    if (success) {
                                        tv_content.setText(et_content.getText().toString());

                                        // 수정 삭제 레이아웃 감추기, 보이기
                                        tv_content.setVisibility(View.VISIBLE);
                                        et_content.setVisibility(View.GONE);
                                        ll_mody.setVisibility(View.GONE);
                                        ll_tool.setVisibility(View.VISIBLE);
                                    } else {

                                    }
                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        };

                        CommentModifyRequest commentModifyRequest = new CommentModifyRequest(   comment.getComment_no(),
                                                                                                et_content.getText().toString(),
                                                                                                responseListener);
                        queue = Volley.newRequestQueue(context);
                        queue.add(commentModifyRequest);
                        break;

                    case R.id.btn_board_comment_item_cancle:
                        tv_content.setVisibility(View.VISIBLE);
                        et_content.setVisibility(View.GONE);
                        ll_mody.setVisibility(View.GONE);
                        ll_tool.setVisibility(View.VISIBLE);
                        break;
                }
            }
        };

        btn_delete.setOnClickListener(onClickListener);
        btn_modify.setOnClickListener(onClickListener);
        btn_submit.setOnClickListener(onClickListener);
        btn_cancle.setOnClickListener(onClickListener);

        return view;
    }
}
