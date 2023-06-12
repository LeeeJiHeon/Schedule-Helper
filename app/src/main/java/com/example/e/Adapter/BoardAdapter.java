package com.example.e.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.e.Model.Board;
import com.example.e.R;

import java.util.ArrayList;

public class BoardAdapter extends BaseAdapter {
    private ArrayList<Board>    arrayList;
    private Context             context;

    public BoardAdapter(ArrayList<Board> boards){
        this.arrayList = boards;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int i) {
        return arrayList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        context = viewGroup.getContext();
        if(view == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.board_item_list, viewGroup, false);
        }

        Board board = arrayList.get(i);

        TextView tv_no      = view.findViewById(R.id.tv_board_list_num);
        TextView tv_date    = view.findViewById(R.id.tv_board_list_date);
        TextView tv_writer  = view.findViewById(R.id.tv_board_list_writer);
        TextView tv_title   = view.findViewById(R.id.tv_board_list_title);

        TextView tv_like_count = view.findViewById(R.id.tv_board_list_like_count);

        tv_no.setText(board.getNo());
        tv_date.setText(board.getDate());
        tv_writer.setText(board.getMayjor());
        tv_title.setText(board.getTitle());
        tv_like_count.setText(board.getLike_count());

        return view;
    }
}
