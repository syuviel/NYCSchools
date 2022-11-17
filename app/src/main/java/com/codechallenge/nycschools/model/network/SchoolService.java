package com.codechallenge.nycschools.model.network;

import com.codechallenge.nycschools.model.pojo.NYCSATResponse;
import com.codechallenge.nycschools.model.pojo.NYCSchoolResponse;

import java.util.List;

import io.reactivex.rxjava3.core.Single;
import retrofit2.http.GET;

public interface SchoolService {
    @GET(ENDPOINTS.ENDPOINT_SCHOOL)
    Single<List<NYCSchoolResponse>> getSchoolList();

    @GET(ENDPOINTS.ENDPOINT_SAT)
    Single<List<NYCSATResponse>> getSatList();
}
