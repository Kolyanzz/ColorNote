package com.example.colornote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;

import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class MyBottomSheetDialogFragment extends BottomSheetDialogFragment {

    private OnDialogListener dialogListener;

    public static MyBottomSheetDialogFragment newInstance() {
        return new MyBottomSheetDialogFragment();
    }

    // Установим слушатель диалога
    public void setOnDialogListener(OnDialogListener dialogListener) {
        this.dialogListener = dialogListener;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_custom, container,
                false);

        // Запретим пользователю выходить без выбора
        setCancelable(false);

        view.findViewById(R.id.btnNo).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (dialogListener != null) dialogListener.onCancelDelete();
            }
        });

        view.findViewById(R.id.btnYes).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
                if (dialogListener != null) dialogListener.onDelete();
            }
        });

        return view;
    }
}