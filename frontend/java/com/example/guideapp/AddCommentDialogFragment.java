package com.example.guideapp;

import android.app.AlertDialog;
import android.app.Dialog;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class AddCommentDialogFragment extends DialogFragment {
    public interface CommentDialogListener {
        void onCommentSubmitted(String nickname, String comment, float rating);
    }

    private EditText nicknameEditText;
    private EditText commentEditText;
    private RatingBar ratingBar;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.make_comment, null);

        nicknameEditText = view.findViewById(R.id.nickname);
        commentEditText = view.findViewById(R.id.comment);
        ratingBar = view.findViewById(R.id.rating);

        builder.setView(view)
                .setTitle("Add Comment")
                .setPositiveButton("Submit", (dialog, id) -> {
                    String nickname = nicknameEditText.getText().toString();
                    String comment = commentEditText.getText().toString();
                    float rating = ratingBar.getRating();

                    CommentDialogListener listener = (CommentDialogListener) getTargetFragment();
                    if (listener != null) {
                        listener.onCommentSubmitted(nickname, comment, rating);
                    }
                })
                .setNegativeButton("Cancel", (dialog, id) -> {

                });
        return builder.create();
    }
}
