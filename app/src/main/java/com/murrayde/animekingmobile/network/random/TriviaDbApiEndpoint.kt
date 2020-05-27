package com.murrayde.animekingmobile.network.random

import com.murrayde.animekingmobile.model.random.RandomQuestion
import io.reactivex.Single
import retrofit2.http.GET

interface TriviaDbApiEndpoint {
    @GET("api.php?amount=10&category=31&type=multiple&command=request")
    fun getTriviaApiResult(): Single<RandomQuestion>
}