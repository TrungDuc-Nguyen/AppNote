package com.example.note.database;

import android.support.annotation.Nullable;

import com.example.note.PassWord.UserAndPass;
import com.example.note.RecyclerView.ListNote;

import java.util.List;
import java.util.Objects;

import io.realm.Realm;

public class RealmContext {
    private Realm realm;
    private static RealmContext instance;

    private RealmContext() {
        realm = Realm.getDefaultInstance();
    }

    // Tạo singleton để gọi trong lớp khác
    public static RealmContext getInstance() {
        if (instance == null) {
            instance = new RealmContext();
        }
        return instance;
    }

    public ListNote getNoteById(int id) {
        return realm.where(ListNote.class).equalTo("id", id).findFirst();
    }

    // Lấy ra tất cả các note trong bộ nhớ bằng findAll
    public List<ListNote> getAllNote() {
        return realm.copyFromRealm(realm.where(ListNote.class).findAll());
    }


    // Thêm một note mới
    public void addNote(ListNote note) {
        List<ListNote> noteList = getAllNote();
        if (noteList == null || noteList.isEmpty()) {
            note.setId(0);
        } else {
            note.setId(noteList.get(noteList.size() - 1).getId() + 1);
        }
        realm.beginTransaction();
        ListNote newNote = realm.createObject(ListNote.class);
        newNote.coppyFromNoteToRealm(note);
        realm.copyFromRealm(newNote);
        realm.commitTransaction();
    }

    // Xóa 1 note bằng id của note đó
    public void deleteNote(int id) {
        ListNote noteDelete = getNoteById(id);
        if (noteDelete == null) return;

        realm.beginTransaction();
        noteDelete.deleteFromRealm();
        realm.commitTransaction();
    }

    // Update note bằng cách lấy id note đó, thay nội dung mới vào
    public void updateNote(int oldId, ListNote noteUpdate) {
        ListNote oldNote = getNoteById(oldId);
        if (oldNote == null) return;

        realm.beginTransaction();
        oldNote.coppyFromNoteToRealm(noteUpdate);
        realm.commitTransaction();
    }

    // Lấy ra tài khoản và mật khẩu đã tạo.
    public List<UserAndPass> getAllUserAndPass() {
        return realm.copyFromRealm(realm.where(UserAndPass.class).findAll());
    }


    public void addUserAndPass(UserAndPass userAndPass) {
        realm.beginTransaction();
        UserAndPass newuserAndPass = realm.createObject(UserAndPass.class);
        newuserAndPass.coppyFromUserAndPassToRealm(userAndPass);
        realm.copyFromRealm(newuserAndPass);
        realm.commitTransaction();
    }
}
