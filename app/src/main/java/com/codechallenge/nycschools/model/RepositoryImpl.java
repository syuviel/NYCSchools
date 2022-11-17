package com.codechallenge.nycschools.model;

import com.codechallenge.nycschools.model.network.Network;
import com.codechallenge.nycschools.model.pojo.NYCSATResponse;
import com.codechallenge.nycschools.model.state.SuccessSATResponse;
import com.codechallenge.nycschools.model.state.SuccessSchoolResponse;
import com.codechallenge.nycschools.model.state.UIState;

import io.reactivex.rxjava3.core.Single;

public class RepositoryImpl implements Repository {

    private Network network;

    public RepositoryImpl(Network network){
        this.network = network;
    }

    @Override
    public Single<UIState> getSchoolList() {
        return network.getSERVICE().getSchoolList()
                .map(nycSchoolResponses -> {
                    SuccessSchoolResponse result = new SuccessSchoolResponse();
                    result.setData(nycSchoolResponses);
                    return result;
                });
    }

    @Override
    public Single<UIState> getSchoolDetails(String dbn) {
        return network.getSERVICE().getSatList()
                .map(nycSchoolSats -> {
                    NYCSATResponse satSchool = new NYCSATResponse();
                    for (NYCSATResponse sat :
                            nycSchoolSats) {
                        if (sat.getDbn().equals(dbn))
                            satSchool = sat;
                    }
                    SuccessSATResponse result = new SuccessSATResponse();
                    result.setData(satSchool);
                    return result;
                });
    }
}