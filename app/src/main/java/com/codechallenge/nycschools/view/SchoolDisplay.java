package com.codechallenge.nycschools.view;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.codechallenge.nycschools.databinding.SchoolDisplayFragmentLayoutBinding;
import com.codechallenge.nycschools.model.Repository;
import com.codechallenge.nycschools.model.pojo.NYCSchoolResponse;
import com.codechallenge.nycschools.model.state.Error;
import com.codechallenge.nycschools.model.state.SuccessSchoolResponse;
import com.codechallenge.nycschools.model.state.UIState;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SchoolDisplay extends Fragment implements Listener.ListClickEvent{

    @Inject
    Repository repository;
    @Inject
    SchoolViewModelProvider schoolViewModelProvider;

    private SchoolDisplayFragmentLayoutBinding binding;
    private SchoolViewModel viewModel;
    private SchoolAdapter adapter;
    private Listener listener;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof Listener)
            listener = (Listener) context;
        else
            throw new ExceptionInInitializerError("Incorrect Host Activity");
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = SchoolDisplayFragmentLayoutBinding.inflate(inflater, container, false);
        View view = binding.getRoot();

        binding.schoolList.setLayoutManager(new LinearLayoutManager(getContext()));

        initObservables();
        return view;
    }

    private void initObservables() {
        viewModel = new ViewModelProvider(this, schoolViewModelProvider).get(SchoolViewModel.class);
        viewModel.schoolState().observe(getViewLifecycleOwner(), this::processUIState);
    }

    private void processUIState(UIState uiState) {
        if (uiState instanceof SuccessSchoolResponse)
            updateAdapter(((SuccessSchoolResponse) uiState).getData());
        else if (uiState instanceof Error)
            showError(((Error) uiState).getErrorMessage());
    }

    private void showError(String errorMessage) {

    }

    private void updateAdapter(List<NYCSchoolResponse> data) {
        adapter = new SchoolAdapter(data, this);
        binding.schoolList.setAdapter(adapter);
    }

    @Override
    public void clickDetails(String dbn, String name, String loc, String email, String phone) {
        listener.openDetails(dbn, name, loc, email, phone);
    }
}
