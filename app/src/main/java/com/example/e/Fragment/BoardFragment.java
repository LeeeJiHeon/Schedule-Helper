package com.example.e.Fragment;

import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;
import com.example.e.Activity.RegisterActivity;
import com.example.e.Adapter.BoardAdapter;
import com.example.e.Model.Board;
import com.example.e.R;
import com.example.e.Activity.ViewActivity;
import com.example.e.Activity.WriteActivity;
import com.example.e.Request.BoardLikeCountRequest;
import com.example.e.Request.BoardListRequest;
import com.example.e.Request.ValidateRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link BoardFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class BoardFragment extends Fragment {
    final private String TAG = "BoardFragment";

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private long time= 0;
    private TextView tv_id;


    private BoardAdapter boardAdapter;
    //받아올 data(사용자가 쓴 글)
    private ArrayList<Board> arrayList;
    public BoardFragment() {
        // Required empty public constructor
    }

    public static BoardFragment newInstance(String param1, String param2) {
        BoardFragment fragment = new BoardFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    //새로고침 구현
    private void refreshListView(){
        getList();
    }
    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();
        if (view != null) {
            ListView listView = view.findViewById(R.id.listview_posts);
            arrayList = new ArrayList<Board>();
            boardAdapter = new BoardAdapter(arrayList);
            listView.setAdapter(boardAdapter);

            listView.setOnItemClickListener((parent, view1, position, id) -> {
                Board board = arrayList.get(position);
                Intent intent = new Intent(requireContext(), ViewActivity.class);

                intent.putExtra("board", board);
                startActivity(intent);
                //Toast.makeText(requireContext(), "게시글", Toast.LENGTH_SHORT).show();
            });

            Button btn_write = view.findViewById(R.id.btn_write);
            btn_write.setOnClickListener(v -> {
                Intent intent = new Intent(requireContext(), WriteActivity.class);
                startActivity(intent);
                Toast.makeText(requireContext(), "새 게시글 작성", Toast.LENGTH_SHORT).show();
            });

            getList();

        }
    }

    public void getList(){
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d(TAG,response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        JSONArray boardArray = jsonResponse.getJSONArray("board");
                        arrayList.clear();
                        for (int i = 0; i < boardArray.length(); i++) {
                            JSONObject boardObject = boardArray.getJSONObject(i);
                            int bbsNo = boardObject.getInt("BBS_NO");
                            String userID = boardObject.getString("userID");
                            String title = boardObject.getString("TITLE");
                            String content = boardObject.getString("CONTENT");
                            String regDate = boardObject.getString("REG_DATE");
                            String major = boardObject.getString("USER_Mayjor");
                            String like_count = boardObject.getString("like_count");

                            //get_Like(bbsNo);

                            Board board = new Board(String.valueOf(bbsNo), userID, title, content, regDate);
                            board.setMayjor(major);
                            board.setLike_count(like_count);

                            arrayList.add(board);
                            boardAdapter.notifyDataSetChanged();
                        }

                    }
                    else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        BoardListRequest boardListRequest = new BoardListRequest(responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(boardListRequest);
    }

    private void get_Like(int BBS_NO){
        Response.Listener<String> responseListener = new Response.Listener<String>() {

            @Override
            public void onResponse(String response) {
                try {
                    Log.d("LIKE",response);
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");
                    if (success) {
                        //String str = jsonResponse.getString("like");


                    }
                    else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        BoardLikeCountRequest boardLikeCountRequest = new BoardLikeCountRequest(BBS_NO, responseListener);
        RequestQueue queue = Volley.newRequestQueue(getContext());
        queue.add(boardLikeCountRequest);
    }
    @Override
    public void onResume() {
        super.onResume();
        getList();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_board, container, false);
    }
}