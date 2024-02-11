package com.example.guideapp;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.guideapp.databinding.FirstFragmentBinding;

public class FirstFragment extends Fragment {

    private FirstFragmentBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        binding = FirstFragmentBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.kadikoyButton.setOnClickListener(v -> navigateToFragmentListOpSys("Kadıköy"));
        binding.uskudarButton.setOnClickListener(v -> navigateToFragmentListOpSys("Üsküdar"));

        return view;
    }

    private void navigateToFragmentListOpSys(String district) {
        Bundle bundle = new Bundle();
        bundle.putString("selectedDistrict", district);
        Navigation.findNavController(binding.getRoot()).navigate(R.id.fragmentListOpSys, bundle);
    }
}
