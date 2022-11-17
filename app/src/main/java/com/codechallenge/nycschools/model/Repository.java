package com.codechallenge.nycschools.model;


import com.codechallenge.nycschools.model.state.UIState;

import io.reactivex.rxjava3.core.Single;

public interface Repository {
    Single<UIState> getSchoolList();
    Single<UIState> getSchoolDetails(String dbn);
}
