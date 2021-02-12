package com.example.colornote.data;

public interface NotesSourceInterface {
    NotesSourceInterface init(NotesSourceInterfaceResponse notesSourceInterfaceResponse);

    Note getNote(int position);

    int size();

    void deleteNote(int position);

    void changeNote(int position, Note note);

    void addNote(Note note);

}
