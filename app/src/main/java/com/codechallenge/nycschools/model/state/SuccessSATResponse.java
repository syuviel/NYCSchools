package com.codechallenge.nycschools.model.state;

import com.codechallenge.nycschools.model.pojo.NYCSATResponse;

public class SuccessSATResponse extends UIState{
    private NYCSATResponse data;

    public NYCSATResponse getData() {
        return data;
    }

    public void setData(NYCSATResponse data) {
        this.data = data;
    }
}
