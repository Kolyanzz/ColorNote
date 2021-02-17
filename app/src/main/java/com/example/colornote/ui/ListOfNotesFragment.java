package com.example.colornote.ui;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colornote.MainActivity;
import com.example.colornote.MyBottomSheetDialogFragment;
import com.example.colornote.Navigation;
import com.example.colornote.OnDialogListener;
import com.example.colornote.R;
import com.example.colornote.data.Note;
import com.example.colornote.data.NoteSoursceFirebaseImp;
import com.example.colornote.data.NotesSourceInterface;
import com.example.colornote.data.NotesSourceInterfaceResponse;
import com.example.colornote.observer.Observer;
import com.example.colornote.observer.Publisher;

import java.util.Objects;

public class ListOfNotesFragment extends Fragment {

    private Note currentNote;
    private NoteSoursceFirebaseImp data;
    private MyAdapter adapter;
    private RecyclerView recyclerView;
    private Navigation navigation;
    private Publisher publisher;
    private boolean moveToFirstPosition;

    public static ListOfNotesFragment newInstance() {
        return new ListOfNotesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list_of_notes, container, false);
        recyclerView = view.findViewById(R.id.notes_recycler_view);
        data = new NoteSoursceFirebaseImp().init(new NotesSourceInterfaceResponse() {
            @Override
            public void initialized(NotesSourceInterface notesData) {
                adapter.notifyDataSetChanged();
            }
        });
        initRecyclerView(recyclerView, data);
        setHasOptionsMenu(true);
        adapter.setDataSource(data);
        return view;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        MainActivity activity = (MainActivity) context;
        navigation = activity.getNavigation();
        publisher = activity.getPublisher();
    }

    @Override
    public void onDetach() {
        navigation = null;
        publisher = null;
        super.onDetach();
    }

    private void initRecyclerView(RecyclerView recyclerView, NotesSourceInterface data) {
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);

        adapter = new MyAdapter(this);
        recyclerView.setAdapter(adapter);
        //декоратор
        DividerItemDecoration itemDecoration = new DividerItemDecoration
                (Objects.requireNonNull(getContext()), LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(Objects.requireNonNull
                (ContextCompat.getDrawable(getContext(), R.drawable.separator)));
        recyclerView.addItemDecoration(itemDecoration);

        if (moveToFirstPosition && data.size() > 0) {
            recyclerView.scrollToPosition(0);
            moveToFirstPosition = false;
        }
        NotesSourceInterface finalData = data;
        adapter.setOnItemClickListener((position, note) -> {
            navigation.addFragment(NoteFragment.newInstance(finalData.getNote(position)),
                    true);
            publisher.subscribe(new Observer() {
                @Override
                public void updateNote(Note note) {
                    finalData.changeNote(position, note);
                    adapter.notifyItemChanged(position);
                }
            });
        });
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        MenuItem search = menu.findItem(R.id.menu_search);
        MenuItem sort = menu.findItem(R.id.menu_sort);
        MenuItem addNote = menu.findItem(R.id.add);
        MenuItem send = menu.findItem(R.id.pereslat);
        MenuItem addPhoto = menu.findItem(R.id.foto);
        MenuItem clearNote = menu.findItem(R.id.clear);
        search.setVisible(true);
        sort.setVisible(true);
        send.setVisible(false);
        addPhoto.setVisible(false);
        addNote.setOnMenuItemClickListener(item -> {
            navigation.addFragment(NoteFragment.newInstance(), true);
            publisher.subscribe(new Observer() {
                @Override
                public void updateNote(Note note) {
                    data.addNote(note);
                    adapter.notifyItemInserted(data.size() - 1);
                    moveToFirstPosition = true;
                }
            });
            return true;
        });
        clearNote.setOnMenuItemClickListener(item -> {
            int position = adapter.getMenuPosition();
            if (item.getItemId() == R.id.clear) {
                //здесь добавим диалоговое окно
                MyBottomSheetDialogFragment deleteDlgFragment = new MyBottomSheetDialogFragment();
                deleteDlgFragment.setCancelable(false);
                deleteDlgFragment.setOnDialogListener(new OnDialogListener() {
                    @Override
                    public void onDelete() {
                        data.deleteNote(position);
                        adapter.notifyItemRemoved(position);
                        deleteDlgFragment.dismiss();
                    }

                    @Override
                    public void onCancelDelete() {
                        deleteDlgFragment.dismiss();
                    }
                });
                deleteDlgFragment.show(requireActivity().getSupportFragmentManager(),
                        "DeleteFragmentTag");
                return true;
            }
            return super.onContextItemSelected(item);
        });
    }
}