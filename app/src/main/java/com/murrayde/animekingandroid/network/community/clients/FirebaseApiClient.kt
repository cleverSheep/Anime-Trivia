package com.murrayde.animekingandroid.network.community.clients

import androidx.core.os.LocaleListCompat
import com.google.firebase.firestore.FieldPath
import com.google.firebase.firestore.FirebaseFirestore
import com.murrayde.animekingandroid.model.community.CommunityQuestion
import com.murrayde.animekingandroid.model.community.PlayHistory
import com.murrayde.animekingandroid.util.removeForwardSlashes
import de.aaronoe.rxfirestore.getSingle
import io.reactivex.Single
import javax.inject.Inject

class FirebaseApiClient @Inject constructor() {
    private val firebaseDB = FirebaseFirestore.getInstance()

    /** Questions for quiz session*/
    fun getQuestions(anime_title: String): Single<List<CommunityQuestion>> {
        val questionsRef = firebaseDB.collection("anime").document(LocaleListCompat.getDefault()[0].language).collection("titles")
                .document(removeForwardSlashes(anime_title)).collection("questions")

        val key = questionsRef.document().id
        return questionsRef.whereGreaterThanOrEqualTo(FieldPath.documentId(), key).getSingle()
    }

    /** High score for an anime title*/
    fun getHighScore(anime_title: String, user_id: String): Single<PlayHistory> {
        val highScoreRef = firebaseDB.collection("users").document(user_id).collection("play_history").document(removeForwardSlashes(anime_title))
        return highScoreRef.getSingle()
    }
}
