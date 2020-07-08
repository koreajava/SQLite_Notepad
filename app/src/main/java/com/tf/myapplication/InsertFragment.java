package com.tf.myapplication;


import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.tf.myapplication.db.dbHandler;
import com.tf.myapplication.vo.NoteVo;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class InsertFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String SELECT_KEY = "select_key";

    EditText insert_title, insert_content;
    Button insert_btn, cancle_btn;
    View view;
    MainActivity mainActivity;
    dbHandler handler;

    // TODO: Rename and change types of parameters
    private int key;

    public InsertFragment() {
        // Required empty public constructor
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_insert, container, false);
        mainActivity = (MainActivity) getActivity();
        insert_title = view.findViewById(R.id.insert_title);
        insert_content = view.findViewById(R.id.insert_content);
        insert_btn = view.findViewById(R.id.insert_btn);
        cancle_btn = view.findViewById(R.id.cancle_btn);

        //DB핸들러 불러오기

        if (handler == null) {
            handler = dbHandler.open(getContext());
        }

        insert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int idx = 0;
                String title = insert_title.getText().toString();
                String content = insert_content.getText().toString();

                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
                String date = df.format(c);

                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (content.isEmpty()) {
                    Toast.makeText(getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }


                handler.memo_insert(idx,title, content, date);


                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                mainActivity.replaceFragment(new NoteFragment());
            }
        });

        cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.replaceFragment(new NoteFragment());
            }
        });
        return view;
    }


}
