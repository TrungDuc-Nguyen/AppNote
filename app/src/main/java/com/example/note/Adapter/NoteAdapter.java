package com.example.note.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.example.note.R;
import com.example.note.RecyclerView.ListNote;
import com.example.note.RecyclerView.onItemClickRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.NoteViewHolder> {
    private List<ListNote> listNotes;
    private Context context;
    private onItemClickRecyclerView itemClickRecyclerView;
    private  String textFind;

    public NoteAdapter(List<ListNote> listNotes, Context context, onItemClickRecyclerView itemClickRecyclerView) {
        this.listNotes = listNotes;
        this.context = context;
        this.itemClickRecyclerView = itemClickRecyclerView;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_rv_note, viewGroup, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder viewHolder, int i) {
        viewHolder.bindView(listNotes.get(i));
    }

    @Override
    public int getItemCount() {
        return listNotes.size();
    }

    public void updateData(List<ListNote> listNote) {
        listNotes.clear();
        listNotes.addAll(listNote);
        this.notifyDataSetChanged();
    }

    class NoteViewHolder extends RecyclerView.ViewHolder {
        TextView tv_topic;
        TextView tv_content;
        TextView tv_date;
        TextView tv_time;
        ListNote note;

        NoteViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_topic = itemView.findViewById(R.id.tv_home_topic);
            tv_content = itemView.findViewById(R.id.tv_home_content);
            tv_date = itemView.findViewById(R.id.tv_home_date);
            tv_time = itemView.findViewById(R.id.tv_home_time);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickRecyclerView.onItemClick(note);
                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    itemClickRecyclerView.onLongItemClick(note);
                    return false;
                }
            });
        }

        void bindView(ListNote note) {
            this.note = note;
            tv_topic.setText(note.getTopic());
            tv_content.setText(note.getContent());
            tv_date.setText(note.getDate());
            tv_time.setText(note.getTime());
        }
    }
}
