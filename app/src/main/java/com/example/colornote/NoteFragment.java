package com.example.colornote;

import android.content.res.Configuration;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NoteFragment extends Fragment {

    public static final String CURRENT_NOTE = "currentNote";
    private Note note;
    private boolean isLandscape;

    public static NoteFragment newInstance(Note note) {
        NoteFragment fragment = new NoteFragment();
        Bundle args = new Bundle();
        args.putParcelable(CURRENT_NOTE, note);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            note = getArguments().getParcelable(CURRENT_NOTE);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_note, container, false);
        TextInputEditText titleText = view.findViewById(R.id.note_title);
        TextInputEditText contentText = view.findViewById(R.id.note_content);
        TextView dateOfCreationText = view.findViewById(R.id.note_date_of_creation);
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        dateOfCreationText.setText(String.format("%s: %s", "Дата создания",
                formatter.format(note.getCreationDate().getTime())));
        titleText.setText(note.getTitle());
        contentText.setText(note.getContent());
        view.setBackgroundColor(note.getColor());
        setHasOptionsMenu(true);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.note_fragment_menu, menu);
        MenuItem search = menu.findItem(R.id.menu_search);
        MenuItem sort = menu.findItem(R.id.menu_sort);
        MenuItem send = menu.findItem(R.id.menu_send);
        MenuItem addPhoto = menu.findItem(R.id.menu_add_photo);

        isLandscape = getResources().getConfiguration().orientation ==
                Configuration.ORIENTATION_LANDSCAPE;
        FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
        Fragment fragment = getVisibleFragment(fragmentManager);
        if (!isLandscape && fragment instanceof NoteFragment) {
            search.setVisible(false);
            sort.setVisible(false);
            send.setVisible(true);
            addPhoto.setVisible(true);
        } else if (!isLandscape && fragment instanceof ListOfNotesFragment) {
            search.setVisible(true);
            sort.setVisible(true);
            send.setVisible(false);
            addPhoto.setVisible(false);
        }
        send.setOnMenuItemClickListener(item -> {
            Toast.makeText(getContext(), R.string.menu_send, Toast.LENGTH_SHORT).show();
            return true;
        });
        addPhoto.setOnMenuItemClickListener(item -> {
            Toast.makeText(getContext(), R.string.menu_add_photo, Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private Fragment getVisibleFragment(FragmentManager fragmentManager) {
        List<Fragment> fragments = fragmentManager.getFragments();
        int countFragments = fragments.size();
        for (int i = countFragments - 1; i >= 0; i--) {
            Fragment fragment = fragments.get(i);
            if (fragment.isVisible())
                return fragment;
        }
        return null;
    }
}