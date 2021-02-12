package com.example.colornote.data;

import java.util.HashMap;
import java.util.Map;

public class NoteMapping {
    public static Note toNote(String id, Map<String, Object> doc) {
        long receivedColor = (long) doc.get(Fields.COLOR);
        int parsedColor = (int) receivedColor;
        Note answer = new Note((String) doc.get(Fields.TITLE),
                (String) doc.get(Fields.CONTENT),
                (String) doc.get(Fields.DATE),
                parsedColor);
        answer.setId(id);
        return answer;
    }

    public static Map<String, Object> toDocument(Note note) {
        Map<String, Object> answer = new HashMap<>();
        answer.put(Fields.TITLE, note.getTitle());
        answer.put(Fields.CONTENT, note.getContent());
        answer.put(Fields.DATE, note.getCreationDate());
        answer.put(Fields.COLOR, note.getColor());
        return answer;
    }

    public static class Fields {
        public final static String DATE = "date";
        public final static String TITLE = "title";
        public final static String CONTENT = "content";
        public final static String COLOR = "color";
    }
}
