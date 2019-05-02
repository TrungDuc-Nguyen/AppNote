package com.example.note.addNote;

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
import android.widget.TextView;
import android.widget.Toast;

import com.example.note.R;
import com.example.note.RecyclerView.ListNote;
import com.example.note.database.RealmContext;
import com.example.note.watchNote.watchNoteOnList;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewNote extends AppCompatActivity {
    EditText et_topic;
    EditText et_content;
    TextView tv_date;
    TextView tv_time;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_note);

        init();
    }

    private void init() {
        et_topic = findViewById(R.id.et_newnote_topic);
        et_content = findViewById(R.id.et_newnote_content);
        tv_date = findViewById(R.id.tv_home_date);
        tv_time = findViewById(R.id.tv_home_time);
    }

    // Tạo sự kiện nút back cứng
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ghi chú mới");
        getMenuInflater().inflate(R.menu.menu_newnote, menu);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    // Tạo sự kiện menu trên action bar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                break;
            case R.id.btn_ab_done: {
                if (et_content.getText().toString().isEmpty()) {
                    Toast.makeText(this, "Bạn chưa nhập nội dung, không thể lưu.", Toast.LENGTH_LONG).show();
                } else {
//                    Intent intent = getIntent();
//                    Bundle bundle = intent.getExtras();
//                    int id = bundle.getInt(watchNoteOnList.KEY_ID, 0);
                    @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
                    @SuppressLint("SimpleDateFormat") DateFormat timeFormat = new SimpleDateFormat("HH:mm:SS");
                    Date date = new Date();
                    ListNote newNote = new ListNote(et_topic.getText().toString(), et_content.getText().toString(),0
                            , dateFormat.format(date), timeFormat.format(date));
                    RealmContext.getInstance().addNote(newNote);
                    finish();
                }
            }
            break;
            case R.id.btn_ab_delete: {
                if (!et_content.getText().toString().isEmpty()) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(NewNote.this, R.style.Theme_AppCompat_Dialog_Alert);
                    builder.setTitle("Hủy ghi chú này.");
                    builder.setMessage("Bạn chưa lưu ghi chú, bạn có chắc muốn hủy.");
                    builder.setCancelable(true);
                    builder.setPositiveButton("Hủy.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    });
                    builder.setNegativeButton("Tiếp tục với ghi chú.", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    builder.show();
                } else {
                    finish();
                }
            }
            break;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
}
