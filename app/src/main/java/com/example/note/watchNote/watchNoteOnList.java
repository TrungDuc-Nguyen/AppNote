package com.example.note.watchNote;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import com.example.note.EditNote.EditNote;
import com.example.note.Home.Home;
import com.example.note.R;
import com.example.note.RecyclerView.ListNote;
import com.example.note.database.RealmContext;

import static com.example.note.R.id.btn_ab_watch_edit;

public class watchNoteOnList extends AppCompatActivity {
    public static final String KEY_ID = "ID";
    TextView tv_topic;
    TextView tv_content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_watch_note_on_list);

        watchNote();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        int id = bundle.getInt(KEY_ID, 0);
        ListNote note = RealmContext.getInstance().getNoteById(id);
        tv_topic.setText(note.getTopic());
        tv_content.setText(note.getContent());
        tv_content.setMovementMethod(new ScrollingMovementMethod());
    }

    private void watchNote() {
        tv_topic = findViewById(R.id.tv_watch_topic);
        tv_content = findViewById(R.id.tv_watch_content);
//        Intent intent = getIntent();
//        Bundle bundle = intent.getExtras();
//        int id = bundle.getInt(KEY_ID, 0);
//        ListNote note = RealmContext.getInstance().getNoteById(id);
//        tv_topic.setText(note.getTopic());
//        tv_content.setText(note.getContent());
//        tv_content.setMovementMethod(new ScrollingMovementMethod());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_watch, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Xem ghi chú");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                onBackPressed();
            }
            break;
            case R.id.btn_ab_watch_edit: {
                Intent intentEdit = new Intent(watchNoteOnList.this, EditNote.class);
                Bundle bundleEdit = new Bundle();

                Intent intent = getIntent();
                Bundle bundle = intent.getExtras();
                int id = bundle.getInt(KEY_ID, 0);

                bundleEdit.putInt(watchNoteOnList.KEY_ID, id);
                intentEdit.putExtras(bundle);
                startActivity(intentEdit);
            }
            break;
            case R.id.btn_ab_watch_delete: {
                AlertDialog.Builder builder = new AlertDialog.Builder(watchNoteOnList.this, R.style.Theme_AppCompat_Dialog_Alert);
                builder.setTitle("Xóa ghi chú.");
                builder.setMessage("Bạn có chắc muốn xóa ghi chú này?");
                builder.setCancelable(true);
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setNegativeButton("Xóa", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = getIntent();
                        Bundle bundle = intent.getExtras();
                        int id = bundle.getInt(KEY_ID, 0);
                        RealmContext.getInstance().deleteNote(id);
                        onBackPressed();
                    }
                });
                builder.show();
            }
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
