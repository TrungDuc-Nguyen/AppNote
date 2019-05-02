package com.example.note.Home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.BackgroundColorSpan;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.note.Adapter.NoteAdapter;
import com.example.note.EditNote.EditNote;
import com.example.note.PassWord.Login;
import com.example.note.PassWord.Register;
import com.example.note.PassWord.UserAndPass;
import com.example.note.addNote.NewNote;
import com.example.note.R;
import com.example.note.RecyclerView.ListNote;
import com.example.note.RecyclerView.onItemClickRecyclerView;
import com.example.note.database.RealmContext;
import com.example.note.watchNote.watchNoteOnList;

import java.util.List;

public class Home extends AppCompatActivity implements onItemClickRecyclerView {
    RecyclerView rv_home_note;
    NoteAdapter noteAdapter;
    EditText et_search;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        init();
        onClickRecyclerView();
        onClickforFloattiingActionBar();
        et_search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                findHightText(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    private void init() {
        et_search = findViewById(R.id.et_home_search);
        rv_home_note = findViewById(R.id.rv_home_listnote);
    }

    private void findHightText(String textFind) {
        List<ListNote> noteList = RealmContext.getInstance().getAllNote();


        for (int i = 0; i < noteList.size(); i++) {
            SpannableString spannableStringTopic = new SpannableString(noteList.get(i).getTopic());
            SpannableString spannableStringContent = new SpannableString(noteList.get(i).getContent());
            BackgroundColorSpan[] backgroundColorSpansTopic = spannableStringTopic.getSpans(0,
                    spannableStringTopic.length(), BackgroundColorSpan.class);
            BackgroundColorSpan[] backgroundColorSpansContent = spannableStringTopic.getSpans(0,
                    spannableStringContent.length(), BackgroundColorSpan.class);
            for (BackgroundColorSpan backgroundColorSpan : backgroundColorSpansTopic) {
                spannableStringTopic.removeSpan(backgroundColorSpan);
            }
            for (BackgroundColorSpan backgroundColorSpan : backgroundColorSpansContent) {
                spannableStringContent.removeSpan(backgroundColorSpan);
            }
            int indexOfTextFindTopic = spannableStringTopic.toString().indexOf(textFind);
            int indexOfTextFindContent = spannableStringContent.toString().indexOf(textFind);
            while (indexOfTextFindTopic >= 0) {
                spannableStringTopic.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfTextFindTopic,
                        indexOfTextFindTopic + textFind.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                indexOfTextFindTopic = spannableStringTopic.toString().indexOf(textFind,
                        indexOfTextFindTopic + textFind.length());
            }
            while (indexOfTextFindContent >= 0) {
                spannableStringContent.setSpan(new BackgroundColorSpan(Color.YELLOW), indexOfTextFindContent,
                        indexOfTextFindContent + textFind.length(), Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                indexOfTextFindContent = spannableStringContent.toString().indexOf(textFind,
                        indexOfTextFindContent + textFind.length());
            }
            noteList.get(i).setTopic(spannableStringTopic.toString());
            noteList.get(i).setContent(spannableStringContent.toString());
            int ID = noteList.get(i).getId();
            RealmContext.getInstance().updateNote(ID, noteList.get(i));
            noteAdapter.updateData(RealmContext.getInstance().getAllNote());
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        noteAdapter.updateData(RealmContext.getInstance().getAllNote());
    }

    // Thiết lập hiện các note trên RecyclerView qua Adapter
    private void onClickRecyclerView() {

        noteAdapter = new NoteAdapter(RealmContext.getInstance().getAllNote(), Home.this, this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        rv_home_note.setLayoutManager(linearLayoutManager);

        rv_home_note.setAdapter(noteAdapter);
    }

    @Override
    public void onItemClick(ListNote note) {
        Intent intent = new Intent(Home.this, watchNoteOnList.class);
        Bundle bundle = new Bundle();
        bundle.putInt(watchNoteOnList.KEY_ID, note.getId());
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(final ListNote note) {
        final String[] itemClick = {"Xóa ghi chú.", "Xem ghi chú.", "Chỉnh sửa ghi chú."};
        android.app.AlertDialog.Builder builder = new android.app.AlertDialog.Builder(Home.this);
        builder.setTitle("Ghi chú.");
        builder.setCancelable(true);
        builder.setPositiveButton("Xong", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        builder.setItems(itemClick, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                String choose = itemClick[which];
                if (choose == "Xóa ghi chú.") {
                    RealmContext.getInstance().deleteNote(note.getId());
                    noteAdapter.updateData(RealmContext.getInstance().getAllNote());
                } else if (choose == "Xem ghi chú.") {
                    Intent intent = new Intent(Home.this, watchNoteOnList.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(watchNoteOnList.KEY_ID, note.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(Home.this, EditNote.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt(watchNoteOnList.KEY_ID, note.getId());
                    intent.putExtras(bundle);
                    startActivity(intent);
                }
            }
        });
        builder.show();
    }


    // Thiết lập sự kiện click cho floatingactionbutton -> gọi edittext nhập chủ đề và nội dung
    public void onClickforFloattiingActionBar() {
        FloatingActionButton fab_add = (FloatingActionButton) findViewById(R.id.fab_newnote);
        fab_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Home.this, NewNote.class);
                startActivity(intent);
            }
        });

        final Button btn_find_clear = (Button) findViewById(R.id.btn_home_search);
        final EditText et_find = (EditText) findViewById(R.id.et_home_search);

        et_find.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().isEmpty()) {
                    btn_find_clear.setBackground(getDrawable(R.drawable.ic_search));
                } else {
                    btn_find_clear.setBackground(getDrawable(R.drawable.ic_clear));
                    btn_find_clear.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            et_find.setText(null);
                        }
                    });
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    // Hàm tạo menu trên actionBar
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle("Ghi chú");
        return super.onCreateOptionsMenu(menu);
    }

    // Hàm bắt sự kiện click các button trên actionBar
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_ab_pass: {
                final String[] iTemPass = {"Cài mật khẩu", "Khóa", "Đổi mật khẩu"};
                AlertDialog.Builder builder = new AlertDialog.Builder(Home.this);
                builder.setTitle("Mật khẩu");
                builder.setCancelable(true);
                builder.setPositiveButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                builder.setItems(iTemPass, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String getItem = iTemPass[which];
                        if (getItem == "Cài mật khẩu") {
                            Intent intent = new Intent(Home.this, Register.class);
                            startActivity(intent);
                        } else if (getItem == "Khóa") {
                            Intent intent = new Intent(Home.this, Login.class);
                            finish();
                            startActivity(intent);
                        } else {

                        }
                    }
                });
                builder.show();
            }
            return true;
            case R.id.btn_ab_history:
                return true;
            case R.id.btn_ab_help:
                return true;
            case R.id.btn_ab_setting:
                return true;
            case R.id.btn_ab_lock: {
                List<UserAndPass> userAndPassList = RealmContext.getInstance().getAllUserAndPass();
                Log.d("Loi", userAndPassList.get(0).toString());
                if (userAndPassList.get(0) == null) {

                    Toast.makeText(this, "Loi", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(Home.this, Login.class);
                startActivity(intent);
                finish();
            }
            return true;
            case R.id.btn_ab_infor:
                return true;
            case R.id.btn_ab_chon:
                return true;
            case R.id.btn_ab_sapxep:
                return true;
            case R.id.btn_ab_timedesplay:
                return true;
            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
