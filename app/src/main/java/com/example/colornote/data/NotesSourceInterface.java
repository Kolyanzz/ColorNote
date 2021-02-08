package com.example.colornote.data;

import com.example.colornote.data.Note;

public interface NotesSourceInterface {
    Note getNote(int position);

    int size();

    void deleteNote(int position);

    void changeNote(int position, Note note);

    void addNote(Note note);
}
