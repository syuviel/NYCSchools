package com.codechallenge.nycschools.model.state;

import com.codechallenge.nycschools.model.pojo.NYCSchoolResponse;

import java.util.List;

public class SuccessSchoolResponse extends UIState{
    private List<NYCSchoolResponse> data;

    public List<NYCSchoolResponse> getData() {
        return data;
    }

    public void setData(List<NYCSchoolResponse> data) {
        this.data = data;
    }
}