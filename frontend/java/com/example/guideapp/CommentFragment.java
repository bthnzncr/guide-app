package com.example.guideapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.example.guideapp.databinding.CommentsRecyclerViewBinding;



import java.util.List;
import java.util.concurrent.ExecutorService;

public class CommentFragment extends Fragment implements AddCommentDialogFragment.CommentDialogListener {
    CommentsRecyclerViewBinding binding;
    Handler reviewsHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Review> reviews = (List<Review>) msg.obj;
            CommentAdapter adp = new CommentAdapter(getActivity(), reviews);
            binding.commentsRecyclerView.setAdapter(adp);
            return true;

        }
    });


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = CommentsRecyclerViewBinding.inflate(inflater, container, false);

        Bundle arguments = getArguments();
        String placeId = null;
        if (arguments != null && arguments.containsKey("placeId")) {
            placeId = arguments.getString("placeId");
            ((MainActivity) requireActivity()).getToolBar().setTitle("Review");
        }
         binding.makeComment.setOnClickListener(v -> showAddCommentDialog());


        binding.commentsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        AppGuideRepo repo = new AppGuideRepo();
        ExecutorService srv = ((GuideApp)getActivity().getApplication()).srv;
        repo.getReviewsForPlace(srv, reviewsHandler, placeId);

        return binding.getRoot();
    }

    private void showAddCommentDialog() {
        AddCommentDialogFragment dialog = new AddCommentDialogFragment();
        dialog.setTargetFragment(this, 0);
        dialog.show(getParentFragmentManager(), "AddCommentDialogFragment");
    }

    public void onCommentSubmitted(String nickname, String commentText, float rating) {
        String placeId = getArguments().getString("placeId");
        Review review = new Review(null, placeId, nickname, rating, commentText);


        ExecutorService srv = ((GuideApp)getActivity().getApplication()).srv;
        Handler uiHandler = new Handler(Looper.getMainLooper());

        AppGuideRepo repo = new AppGuideRepo();
        repo.postReviewForPlace(srv, uiHandler, placeId, review);
        AppGuideRepo repo_rew = new AppGuideRepo();
        ExecutorService srv_rew = ((GuideApp)getActivity().getApplication()).srv;
        repo.getReviewsForPlace(srv_rew, reviewsHandler, placeId);

    }
}
