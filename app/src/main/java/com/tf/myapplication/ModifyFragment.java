package com.tf.myapplication;

import android.database.Cursor;
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


/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ModifyFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ModifyFragment extends Fragment {
    private static final String SELECT_KEY = "select_key";

    View view;
    EditText modify_title, modify_content;
    Button update_btn, update_cancle_btn;
    dbHandler handler;
    Cursor cursor;
    MainActivity mainActivity;
    NoteVo vo;

    // TODO: Rename and change types of parameters
    private int key;

    public ModifyFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static ModifyFragment newInstance(int key) {
        ModifyFragment fragment = new ModifyFragment();
        Bundle args = new Bundle();
        args.putInt(SELECT_KEY, key);
        Log.e("key2", "key값" + key);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            key = getArguments().getInt(SELECT_KEY);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_modify, container, false);
        mainActivity = (MainActivity) getActivity();
        modify_title = view.findViewById(R.id.modify_title);
        modify_content = view.findViewById(R.id.modify_content);
        update_btn = view.findViewById(R.id.update_btn);
        update_cancle_btn = view.findViewById(R.id.update_cancle_btn);
        vo = new NoteVo();
        if (handler == null) {
            handler = dbHandler.open(getContext());
        }
        setting_item();

        update_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = modify_title.getText().toString();
                String content = modify_content.getText().toString();
                Date c = Calendar.getInstance().getTime();
                SimpleDateFormat df = new SimpleDateFormat("yyyy년 MM월 dd일");
                String date = df.format(c);

                if (title.isEmpty()) {
                    Toast.makeText(getContext(), "제목을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                if (content.isEmpty()) {
                    Toast.makeText(getContext(), "내용을 입력해주세요", Toast.LENGTH_SHORT).show();
                }
                handler.memo_update(key,title,content,date);

                FragmentManager fm = getActivity().getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.replace(R.id.main_frame, DetailFragment.newInstance(key)).commit();
            }
        });
        update_cancle_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainActivity.replaceFragment(new DetailFragment());
            }
        });

        return view;
    }

    //DB테이블에 저장된 짬정보 넣기
    public void setting_item() {

        //커서를 이용해서 데이터 조회
        cursor = handler.memo_select(key);

        //커서를 이용해서 컬럼값 단위로 넘김
        int idx = cursor.getInt(cursor.getColumnIndex("idx"));
        String s_title = cursor.getString(cursor.getColumnIndex("title"));
        String s_content = cursor.getString(cursor.getColumnIndex("content"));
        String s_date = cursor.getString(cursor.getColumnIndex("date"));

        modify_title.setText(s_title);
        modify_content.setText(s_content);
        Log.e("tad","key와idx확인"+key+idx);
        Log.e("corona2", "title : " + s_title + "content : " + s_content + "date : " + s_date);

    }


}
