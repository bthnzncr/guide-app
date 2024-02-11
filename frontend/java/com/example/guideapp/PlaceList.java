package com.example.guideapp;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guideapp.databinding.FragmentListOpSysBinding;

import java.util.List;
import java.util.concurrent.ExecutorService;

public class PlaceList extends Fragment {

    FragmentListOpSysBinding binding;

    Handler dataHandler = new Handler(new Handler.Callback() {
        @Override
        public boolean handleMessage(@NonNull Message msg) {
            List<Place> data = (List<Place>) msg.obj;
            PlaceAdapter adp = new PlaceAdapter(getActivity(), data);
            binding.recView.setAdapter(adp);
            return true;
        }
    });

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentListOpSysBinding.inflate(inflater, container, false);

        Bundle arguments = getArguments();
        String selectedDistrict = null;
        if (arguments != null && arguments.containsKey("selectedDistrict")) {
            selectedDistrict = arguments.getString("selectedDistrict");
            ((MainActivity) requireActivity()).getToolBar().setTitle(arguments.getString("selectedDistrict"));
        }


        binding.recView.setLayoutManager(new LinearLayoutManager(getActivity()));

        AppGuideRepo repo = new AppGuideRepo();
        ExecutorService srv = ((GuideApp)getActivity().getApplication()).srv;
        repo.getFilteredPlaces(srv, dataHandler, selectedDistrict, null, null);

                return binding.getRoot();
    }
}
