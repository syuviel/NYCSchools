package com.codechallenge.nycschools.view;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.codechallenge.nycschools.databinding.SchoolDetailsFragmentLayoutBinding;
import com.codechallenge.nycschools.model.Repository;
import com.codechallenge.nycschools.model.pojo.NYCSATResponse;
import com.codechallenge.nycschools.model.state.Error;
import com.codechallenge.nycschools.model.state.SuccessSATResponse;
import com.codechallenge.nycschools.model.state.UIState;
import com.codechallenge.nycschools.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SchoolDetails extends Fragment {

    @Inject
    Repository repository;

    @Inject
    SchoolViewModelProvider schoolViewModelProvider;

    private static final String KEY_DBN = "KEY_DBN_SCHOOL_DETAILS";
    private static final String KEY_NAME = "KEY_NAME_SCHOOL_DETAILS";
    private static final String KEY_LOC = "KEY_LOC_SCHOOL_DETAILS";
    private static final String KEY_PHONE = "KEY_PHONE_SCHOOL_DETAILS";
    private static final String KEY_EMAIL = "KEY_EMAIL_SCHOOL_DETAILS";


    private SchoolDetailsFragmentLayoutBinding binding;
    private SchoolViewModel viewModel;
    private String schoolNameStr;
    private String schoolLocStr;
    private String schoolEmailStr;
    private String schoolPhoneStr;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = SchoolDetailsFragmentLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        initObservables();

        if (getArguments().getString(KEY_DBN) != null)
            viewModel.getSatDetails(getArguments().getString(KEY_DBN));

        if (getArguments().getString(KEY_NAME) != null)
            schoolNameStr = getArguments().getString(KEY_NAME);

        if (getArguments().getString(KEY_LOC) != null)
            schoolLocStr = getArguments().getString(KEY_LOC).substring(0, (getArguments().getString(KEY_LOC).indexOf('(')));

        if (getArguments().getString(KEY_PHONE) != null)
            schoolPhoneStr = getArguments().getString(KEY_PHONE);

        if (getArguments().getString(KEY_EMAIL) != null)
            schoolEmailStr = getArguments().getString(KEY_EMAIL);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private void initObservables() {
        viewModel = new ViewModelProvider(this, schoolViewModelProvider).get(SchoolViewModel.class);
        viewModel.schoolState().observe(getViewLifecycleOwner(), this::updateUI);
    }

    private void updateUI(UIState uiState) {
        if (uiState instanceof SuccessSATResponse)
            updateView(((SuccessSATResponse) uiState).getData());
        else if (uiState instanceof Error)
            showError(((Error) uiState).getErrorMessage());
    }

    private void showError(String errorMessage) {
    }

    private void updateView(NYCSATResponse data) {
        String takers = getString(R.string.sat_takers) + data.getNum_of_sat_test_takers();
        String math = getString(R.string.sat_math) + data.getSat_math_avg_score();
        String read = getString(R.string.sat_read) + data.getSat_critical_reading_avg_score();
        String write = getString(R.string.sat_write) + data.getSat_writing_avg_score();

        binding.schoolName.setText(schoolNameStr);
        binding.schoolLocation.setText(schoolLocStr);
        binding.schoolEmail.setText(schoolEmailStr);
        binding.schoolPhone.setText(schoolPhoneStr);


        if(data.getDbn() == null || data.getSchool_name() == null || data.getNum_of_sat_test_takers() == null
                || data.getSat_math_avg_score() == null || data.getSat_writing_avg_score() == null || data.getSat_critical_reading_avg_score() == null){
            binding.satDetails.setText(R.string.sat_na);
        }
        else {
            binding.satDetails.setText(R.string.sat_details);
            binding.schoolDetailsSatTakers.setText(takers);
            binding.schoolDetailsSatReading.setText(read);
            binding.schoolDetailsSatWriting.setText(write);
            binding.schoolDetailsSatMath.setText(math);
        }
    }

    public static Fragment getInstance(String dbn, String name, String loc, String email, String phone) {
        SchoolDetails fragment = new SchoolDetails();
        Bundle bundle = new Bundle();
        bundle.putString(KEY_DBN, dbn);
        bundle.putString(KEY_NAME, name);
        bundle.putString(KEY_LOC, loc);
        bundle.putString(KEY_EMAIL, email);
        bundle.putString(KEY_PHONE, phone);
        fragment.setArguments(bundle);
        return fragment;
    }
}