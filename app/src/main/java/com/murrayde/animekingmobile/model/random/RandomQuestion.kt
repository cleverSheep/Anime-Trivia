package com.murrayde.animekingmobile.model.random

import androidx.annotation.Keep

@Keep
data class RandomQuestion(
        val response_code: Int,
        val results: List<Result>
)