package com.tf.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.tf.myapplication.adapter.NoteAdapter;
import com.tf.myapplication.datainterface.Datainterface;
import com.tf.myapplication.db.dbHandler;
import com.tf.myapplication.vo.NoteVo;

import java.util.ArrayList;


public class NoteFragment extends Fragment implements Datainterface {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    View view;
    FloatingActionButton insert_fab;
    MainActivity mainActivity;
    FragmentManager fm;
    FragmentTransaction ft;
    NoteAdapter adapter;
    ArrayList<NoteVo> list;
    RecyclerView notePad;
    dbHandler handler;
    Cursor cursor;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        view = inflater.inflate(R.layout.fragment_note, container, false);
        list = new ArrayList<>();

        mainActivity = (MainActivity) getActivity();
        notePad = view.findViewById(R.id.NotePad);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        notePad.setLayoutManager(layoutManager);
        notePad.setHasFixedSize(true);//리사이클러뷰 기존 성능 강화
        //adapter = new NoteAdapter(list,getContext(),this);
        NoteVo noteVo = new NoteVo();


        if (handler == null) {
            handler = dbHandler.open(getContext());
        }


       /* noteVo.setTitle(mParam1);
        noteVo.setContent(mParam2);
        noteVo.setDate(mParam3);*/

        //notePad.setAdapter(adapter);
        //list = new ArrayList<>();

        list.add(noteVo);
        adapter = new NoteAdapter(list, getContext(), this);
        notePad.setAdapter(adapter);
        //list.add(new NoteVo("title","content","date"));

        insert_fab = view.findViewById(R.id.insert_fab);
        insert_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mainActivity.replaceFragment(new InsertFragment());
            }
        });

        setting_item();
        return view;
    }

    @Override
    public void dataRemove(final int key, final int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("삭제");
        builder.setMessage("삭제하시겠습니까");
        builder.setPositiveButton("삭제", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                handler.memo_delete(key);
                setting_item();
            }
        })
        .setNegativeButton("취소", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();

    }

    @Override
    public void dataDetail(int key) {
        FragmentManager fm = getActivity().getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.main_frame, DetailFragment.newInstance(key)).commit();
    }


    //리스트 항목을Adapter통해 리사이클러뷰에 넣어주기
    public void add_item(int idx, String title, String content, String date) {
        NoteVo vo = new NoteVo();

        vo.setIdx(idx);
        vo.setTitle(title);
        vo.setContent(content);
        vo.setDate(date);

        Log.e("find", "title : " + title + "content : " + content + "date : " + date);
        list.add(vo);
    }

    //DB에 저장된 내용을 불러와서 리스트 항목에 넣기
    public void load_item(int idx, String title, String content, String date) {
        add_item(idx, title, content, date);

        notePad.setAdapter(new NoteAdapter(list, getContext(), this));
    }

    //DB테이블에 저장된 짬정보 넣기
    public void setting_item() {
        list.clear();

        try {
            //커서를 이용해서 데이터 조회
            cursor = handler.select();

            //커서를 이용해서 컬럼값 단위로 넘김
            while (cursor.moveToNext()) {
                int idx = cursor.getInt(cursor.getColumnIndex("idx"));
                String s_title = cursor.getString(cursor.getColumnIndex("title"));
                String s_content = cursor.getString(cursor.getColumnIndex("content"));
                String s_date = cursor.getString(cursor.getColumnIndex("date"));

                Log.e("item", "title : " + s_title + "content : " + s_content + "date : " + s_date);
                load_item(idx, s_title, s_content, s_date);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
