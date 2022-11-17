package com.codechallenge.nycschools.view;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.codechallenge.nycschools.model.Repository;

import javax.inject.Inject;

public class SchoolViewModelProvider implements ViewModelProvider.Factory {

    private Repository repository;

    @Inject
    public SchoolViewModelProvider(Repository repository){
        this.repository = repository;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SchoolViewModel(repository);
    }
}
