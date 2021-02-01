@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingmobile.screen.game_over


import android.content.SharedPreferences
import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.google.firebase.auth.FirebaseAuth
import com.murrayde.animekingmobile.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.fragment_view_results.*


@AndroidEntryPoint
class GameOverResultsList : Fragment() {

    private val resultsArgs: GameOverResultsListArgs by navArgs()
    private lateinit var gameOverViewModel: GameOverViewModel
    private lateinit var media: MediaPlayer
    private var media_is_playing = true
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var animeTitle: String
    private lateinit var auth: FirebaseAuth

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_view_results, container, false)
        gameOverViewModel = ViewModelProvider(requireActivity()).get(GameOverViewModel::class.java)
        animeTitle = if (resultsArgs.animeAttributes.titles.en != null) resultsArgs.animeAttributes.titles.en else resultsArgs.animeAttributes.canonicalTitle
        auth = FirebaseAuth.getInstance()
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(activity)
        media = MediaPlayer.create(activity, R.raw.button_click_sound_effect)
        media_is_playing = sharedPreferences.getBoolean("sound_effects", true)
        progressBarSimpleCustom.enableAnimation()
        increase_progress.setOnClickListener {
            progressBarSimpleCustom.progress = progressBarSimpleCustom.progress + 40
            updateCustomSecondaryProgress()
        }
        decrease_progress.setOnClickListener {
            progressBarSimpleCustom.progress = progressBarSimpleCustom.progress - 30
            updateCustomSecondaryProgress()
        }
        if(gameOverViewModel.getTotalCorrect() > gameOverViewModel.getHighScore()) {
            gameOverViewModel.updateBackendHighScore(auth.uid!!, animeTitle, gameOverViewModel.getTotalCorrect())
        }
    }

    private fun updateCustomSecondaryProgress() {
        progressBarSimpleCustom.secondaryProgress = progressBarSimpleCustom.progress + 10
    }
}
