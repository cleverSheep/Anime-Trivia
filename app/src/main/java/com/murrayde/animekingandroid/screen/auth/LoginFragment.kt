@file:Suppress("LocalVariableName", "PrivatePropertyName")

package com.murrayde.animekingandroid.screen.auth

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import com.facebook.*
import com.facebook.login.LoginManager
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.firebase.auth.FacebookAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import com.murrayde.animekingandroid.R
import com.murrayde.animekingandroid.extensions.mayNavigate
import com.murrayde.animekingandroid.network.community.api_models.AnimeData
import kotlinx.android.synthetic.main.fragment_login.*
import timber.log.Timber

class LoginFragment : Fragment() {

    private lateinit var callbackManager: CallbackManager
    private lateinit var auth: FirebaseAuth
    private lateinit var authStateListener: FirebaseAuth.AuthStateListener
    private lateinit var accessTokenTracker: AccessTokenTracker
    private lateinit var gso: GoogleSignInOptions
    private lateinit var googleSignInClient: GoogleSignInClient
    private val RC_SIGN_IN = 1
    private lateinit var loginViewModel: LoginViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        auth = FirebaseAuth.getInstance()
        FacebookSdk.sdkInitialize(activity?.applicationContext)
        callbackManager = CallbackManager.Factory.create()
        // Configure Google Sign In
        gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.web_client_id))
                .requestEmail()
                .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
        loginViewModel = ViewModelProvider(requireActivity()).get(LoginViewModel::class.java)
        return inflater.inflate(R.layout.fragment_login_carousel, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val list_anime_data = arrayListOf<AnimeData>()
        val login_rv_adapter_anime = LoginFragmentRecyclerviewAdapter(list_anime_data)

        val list_manga_data = arrayListOf<AnimeData>()
        val login_rv_adapter_manga = LoginFragmentRecyclerviewAdapter(list_manga_data)

        LoginManager.getInstance().registerCallback(callbackManager, object : FacebookCallback<LoginResult> {
            override fun onSuccess(result: LoginResult?) {
                Timber.d("facebook:onSuccess:$result")
                handleFacebookAccessToken(result!!.accessToken, view)
            }

            override fun onCancel() {
                Timber.d("facebook:onCancel")
            }

            override fun onError(error: FacebookException?) {
                Timber.d("facebook:onError")
            }

        })

        facebook_login_button.setOnClickListener {
            LoginManager.getInstance().logInWithReadPermissions(this, listOf("email", "public_profile"))
        }

        google_login_button.setOnClickListener {
            googleSignIn()
        }

        authStateListener = FirebaseAuth.AuthStateListener { firebase_auth ->
            if (firebase_auth.currentUser != null) updateUI(firebase_auth.currentUser)
            else updateUI(null)
        }

        accessTokenTracker = object : AccessTokenTracker() {
            override fun onCurrentAccessTokenChanged(oldAccessToken: AccessToken?, currentAccessToken: AccessToken?) {
                if (currentAccessToken == null) {
                    auth.signOut()
                }
            }

        }

    }

    private fun handleFacebookAccessToken(token: AccessToken, view: View) {
        Timber.d("handleFacebookAccessToken:$token")

        val credential = FacebookAuthProvider.getCredential(token.token)
        auth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity()) { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w("signInWithCredential:failure")
                        Toast.makeText(activity, "Authentication failed.",
                                Toast.LENGTH_SHORT).show()
                        updateUI(null)
                    }
                }
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user != null) {
            val directions = LoginFragmentDirections.actionLoginFragmentToHome()
            if (view != null && mayNavigate()) {
                Navigation.findNavController(requireView()).navigate(directions)
            }
        } else {
            Timber.d("Login failure!")
        }
    }

    private fun googleSignIn() {
        val signInIntent = googleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        // Pass the activity result back to the Facebook SDK
        callbackManager.onActivityResult(requestCode, resultCode, data)
        super.onActivityResult(requestCode, resultCode, data)

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                // Google Sign In was successful, authenticate with Firebase
                val account = task.getResult(ApiException::class.java)
                firebaseAuthWithGoogle(account!!)
            } catch (e: ApiException) {
                // Google Sign In failed, update UI appropriately
                Timber.w("Google sign in failed: ${e.printStackTrace()}")
            }
        }

    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        Timber.d("firebaseAuthWithGoogle:")

        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        auth.signInWithCredential(credential)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // Sign in success, update UI with the signed-in user's information
                        Timber.d("signInWithCredential:success")
                        val user = auth.currentUser
                        updateUI(user)
                    } else {
                        // If sign in fails, display a message to the user.
                        Timber.w("signInWithCredential:failure")
                        updateUI(null)
                    }
                }
    }

    override fun onStart() {
        super.onStart()
        auth.addAuthStateListener(authStateListener)
        // Check if user is signed in (non-null) and update UI accordingly.
        val currentUser = auth.currentUser
        updateUI(currentUser)
    }

}
