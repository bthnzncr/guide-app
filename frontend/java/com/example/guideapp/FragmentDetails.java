package com.example.guideapp;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.example.guideapp.databinding.FragmentDetailsBinding;


import java.util.concurrent.ExecutorService;

public class FragmentDetails extends Fragment {

    FragmentDetailsBinding binding;

    Handler handler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            Place place = (Place) msg.obj;
            binding.textNeighbourhood.setText(place.getNeighborhood());
            binding.textText.setText(place.getText());
            binding.textCategory.setText(place.getCategory());

            String url = place.getSources();
            String linkText = "<a href='" + url + "'>" + place.getName() + "</a>";
            binding.textLink.setText(Html.fromHtml(linkText, Html.FROM_HTML_MODE_COMPACT));
            binding.textLink.setMovementMethod(LinkMovementMethod.getInstance());

            ((MainActivity) getActivity()).getToolBar().setTitle(place.getName());

            AppGuideRepo repo = new AppGuideRepo();
            ExecutorService srv = ((GuideApp) getActivity().getApplication()).srv;
            repo.downloadImage(srv, imgHandler, place.getImages());

            return true;
        }
    });

    Handler imgHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            binding.imgDetails.setImageBitmap((Bitmap) msg.obj);
            return true;
        }
    });



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentDetailsBinding.inflate(getLayoutInflater());

        String placeId = getArguments().getString("placeId");

        AppGuideRepo repo = new AppGuideRepo();
        ExecutorService srv = ((GuideApp) getActivity().getApplication()).srv;
        repo.getDataById(srv, handler, placeId);

        binding.commentButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("placeId", placeId);
            NavController navController = Navigation.findNavController(requireActivity(), R.id.fragmentContainerView);
            navController.navigate(R.id.action_fragmentDetails_to_commentFragment, bundle);
        });

        return binding.getRoot();
    }
}