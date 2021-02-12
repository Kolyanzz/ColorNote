package com.example.colornote.ui;

import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.colornote.R;
import com.example.colornote.data.Note;
import com.example.colornote.data.NotesSourceInterface;

import java.text.SimpleDateFormat;
import java.util.Locale;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private final Fragment fragment;
    private NotesSourceInterface dataSource;
    private MyClickListener myClickListener;
    private int menuPosition;

    public MyAdapter(Fragment fragment) {
        this.fragment = fragment;
    }

    public void setDataSource(NotesSourceInterface dataSource) {
        this.dataSource = dataSource;
        notifyDataSetChanged();
    }

    public int getMenuPosition() {
        return menuPosition;
    }

    public void setOnItemClickListener(MyClickListener itemClickListener) {
        myClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyAdapter.ViewHolder holder, int position) {
        holder.getItemLayout().setBackgroundColor(dataSource.getNote(position).getColor());
        holder.getTitleTextView().setText(dataSource.getNote(position).getTitle());
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss dd-MM-yyyy",
                Locale.getDefault());
        holder.getDateTextView().setText(dataSource.getNote(position).getCreationDate());
    }

    @Override
    public int getItemCount() {
        return dataSource.size();
    }

    public interface MyClickListener {
        void onItemClick(int position, Note note);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private final CardView cardView;
        private final LinearLayout itemLayout;
        private final TextView titleTextView;
        private final TextView dateTextView;

        public ViewHolder(@NonNull final View itemView) {
            super(itemView);
            cardView = (CardView) itemView;
            itemLayout = itemView.findViewById(R.id.element_of_recycler_view);
            titleTextView = itemView.findViewById(R.id.first_tv_of_item);
            dateTextView = itemView.findViewById(R.id.second_tv_of_item);

            registerContextMenu(itemView);

            itemLayout.setOnClickListener(v -> {
                int position = getAdapterPosition();
                myClickListener.onItemClick(position, dataSource.getNote(position));
            });

            itemLayout.setOnLongClickListener(new View.OnLongClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.N)
                @Override
                public boolean onLongClick(View v) {
                    menuPosition = getLayoutPosition();
                    itemView.showContextMenu(550, 10);
                    return true;
                }
            });
        }

        private void registerContextMenu(@NonNull View itemView) {
            if (fragment != null) {
                itemView.setOnLongClickListener(v -> {
                    menuPosition = getLayoutPosition();
                    return false;
                });
                fragment.registerForContextMenu(itemView);
            }
        }

        public LinearLayout getItemLayout() {
            return itemLayout;
        }

        public TextView getTitleTextView() {
            return titleTextView;
        }

        public TextView getDateTextView() {
            return dateTextView;
        }
    }
}
