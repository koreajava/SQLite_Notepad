package com.tf.myapplication.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.tf.myapplication.DetailFragment;
import com.tf.myapplication.MainActivity;
import com.tf.myapplication.ModifyFragment;
import com.tf.myapplication.NoteFragment;
import com.tf.myapplication.R;
import com.tf.myapplication.datainterface.Datainterface;
import com.tf.myapplication.vo.NoteVo;

import java.util.ArrayList;

/**
 * Created by 82108 on 2020-06-15.
 */
public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {

    public NoteAdapter(ArrayList<NoteVo> arrayList, Context context, Datainterface datainterface) {
        this.datainterface = datainterface;
        this.arrayList = arrayList;
        this.context = context;
    }

    private Datainterface datainterface;
    private ArrayList<NoteVo> arrayList;
    Context context;
    MainActivity mainActivity = new MainActivity();


    @NonNull
    @Override
    public NoteAdapter.NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sqlnote, parent, false);
        NoteViewHolder holder = new NoteViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteAdapter.NoteViewHolder holder, int position) {
        holder.note_title.setText(arrayList.get(position).getTitle());
        holder.note_content.setText(arrayList.get(position).getContent());
        holder.note_date.setText(arrayList.get(position).getDate());


    }

    void addList(NoteVo vo) {
        arrayList.add(vo);
    }

    @Override
    public int getItemCount() {
        return (arrayList != null ? arrayList.size() : 0);
    }

    public class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView note_title;
        TextView note_content;
        TextView note_date;

        public NoteViewHolder(@NonNull View itemView) {
            super(itemView);
            this.note_title = itemView.findViewById(R.id.note_title);
            this.note_content = itemView.findViewById(R.id.note_content);
            this.note_date = itemView.findViewById(R.id.note_date);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int key = arrayList.get(getAdapterPosition()).getIdx();
                    datainterface.dataDetail(key);
                }
            });
            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    int key = arrayList.get(getAdapterPosition()).getIdx();
                    datainterface.dataRemove(key, getAdapterPosition());
                    return false;
                }
            });
        }

    }
}
