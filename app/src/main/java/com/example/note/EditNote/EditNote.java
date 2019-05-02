package com.example.note.EditNote;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.R;
import com.example.note.RecyclerView.ListNote;
import com.example.note.database.RealmContext;
import com.example.note.watchNote.watchNoteOnList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditNote extends AppCompatActivity {
    EditText et_topic;
    EditText et_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_note);

        init();
    }

    private void init() {
        et_topic = findViewById(R.id.et_edit_topic);
        et_content = findViewById(R.id.et_edit_content);

        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt(watchNoteOnList.KEY_ID, 0);
        ListNote note = RealmContext.getInstance().getNoteById(id);
        et_topic.setText(note.getTopic());
        et_content.setText(note.getContent());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editnote, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Chỉnh sửa ghi chú");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:{
                onBackPressed();
            }
            break;
            case R.id.btn_edit_cancel: {
                AlertDialog.Builder builder = new AlertDialog.Builder(EditNote.this);
                builder.setTitle("Hủy chỉnh sửa.");
                builder.setMessage("Bạn có chắc muốn dừng chỉnh sửa?");
                builder.setCancelable(true);
                builder.setPositiveButton("Dừng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        onBackPressed();
                    }
                });
                builder.setNegativeButton("Tiếp tục chỉnh sửa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.show();
            }
            break;
            case R.id.btn_edit_save: {
                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt(watchNoteOnList.KEY_ID, 0);
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                @SuppressLint("SimpleDateFormat") DateFormat timeFormat = new SimpleDateFormat("HH:mm:SS");
                Date date = new Date();
                ListNote newNote = new ListNote(et_topic.getText().toString(), et_content.getText().toString(),id ,
                        dateFormat.format(date), timeFormat.format(date));
                RealmContext.getInstance().updateNote(id, newNote);
                onBackPressed();
                Toast.makeText(EditNote.this, "Đã lưu thay đổi.", Toast.LENGTH_LONG).show();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
