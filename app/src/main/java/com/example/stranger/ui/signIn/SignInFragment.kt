package com.example.stranger.ui.signIn

import android.content.ContentValues
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.viewModels
import com.example.stranger.R
import com.example.stranger.base.BaseFragmentWithBinding
import com.example.stranger.common.State
import com.example.stranger.databinding.FragmentSignInBinding
import com.example.stranger.ui.main.MainFragment
import com.example.stranger.ui.newProFile.NewProFileFragment
import com.example.stranger.ui.signUp.SignUpFragment
import com.google.android.gms.auth.api.identity.BeginSignInRequest
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SignInFragment : BaseFragmentWithBinding<FragmentSignInBinding>() {

    private var auth: FirebaseAuth = FirebaseAuth.getInstance()
    private val RC_SIGN_IN = 400
    private var mGoogleSignInClient: GoogleSignInClient? = null
    private var idToken: String? = null

    companion object {
        fun newInstance() = SignInFragment()
    }

    private val viewModel: SignInViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val signInRequest = BeginSignInRequest.builder()
            .setGoogleIdTokenRequestOptions(
                BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                    .setSupported(true)
                    // Your server's client ID, not your Android client ID.
                    .setServerClientId(getString(R.string.default_web_client_id))
                    // Only show accounts previously used to sign in.
                    .setFilterByAuthorizedAccounts(true)

                    .build()
            )
            .build()
        val gso = signInRequest.googleIdTokenRequestOptions.serverClientId?.let {
            GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(it)
                .requestEmail()
                .build()
        }
        mGoogleSignInClient = gso?.let { GoogleSignIn.getClient(requireActivity(), it) }
    }

    override fun getViewBinding(inflater: LayoutInflater): FragmentSignInBinding =
        FragmentSignInBinding.inflate(inflater).apply {
            this.lifecycleOwner = viewLifecycleOwner
            this.viewmodel = viewModel
        }

    override fun initAction() {
        binding.lognIn.setOnClickListener {  viewModel.login() }
        binding.signUp.setOnClickListener {
            splashActivity.addFragment(
                SignUpFragment.newInstance(),
                SignUpFragment::class.java.name
            )
        }
        binding.sigInGoogle.setOnClickListener { view ->
            val signInIntent = mGoogleSignInClient?.signInIntent
            startActivityForResult(signInIntent, RC_SIGN_IN)
        }
    }

    override fun init() {
        getProfile()
        firebaseUser()
    }

    override fun initData() {

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            handleSignInResult(task)
        }
    }

    private fun handleSignInResult(completedTask: Task<GoogleSignInAccount>) {
        try {
            val account = completedTask.getResult(ApiException::class.java)
            firebaseAuthWithGoogle(account?.idToken.toString())

        } catch (e: ApiException) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information
            Log.w(ContentValues.TAG, "signInResult:failed code=" + e.statusCode)
        }
    }

    private fun firebaseAuthWithGoogle(idToken: String) {
        val credential = GoogleAuthProvider.getCredential(idToken, null)
        auth.signInWithCredential(credential)
            .addOnCompleteListener(requireActivity()) { task ->
                if (task.isSuccessful) {
                    Log.d(ContentValues.TAG, "signInWithCredential:success")
                    Toast.makeText(requireContext(), "Login ${auth.uid}", Toast.LENGTH_LONG).show()
                    auth.uid?.let { viewModel.getFroFile(it) }
                } else {
                    Log.w(ContentValues.TAG, "signInWithCredential:failure", task.exception)
                }
            }
    }

    private fun firebaseUser() {
        viewModel.firebaseUser.observe(viewLifecycleOwner) {
            when (it) {
                is State.Loading -> {
                    binding.lognIn.visibility = View.GONE
                    binding.email.isEnabled = false
                    binding.pass.isEnabled = false
                }
                is State.Success -> it.data.uid.let { user ->
                    viewModel.getFroFile(user)
                }
                is State.Error -> {
                    binding.lognIn.visibility = View.VISIBLE
                    binding.textInputLayout.helperText = "Email không đúng"
                    binding.textInputLayout2.helperText = "Sai mật khẩu"
                    viewModel.hintEmailTextColor.value = true
                    viewModel.hintPassTextColor.value = true
                    binding.email.isEnabled = true
                    binding.pass.isEnabled = true
                    Toast.makeText(context, it.exception, Toast.LENGTH_SHORT).show()
                    binding.lognIn.isEnabled = true
                }
                else -> {
                }
            }
        }
    }

    private fun getProfile() {
        viewModel.proFile.observe(viewLifecycleOwner) { profile ->
            when (profile) {
                is State.Loading -> binding.lognIn.visibility = View.GONE
                is State.Success -> {
                    if (profile.data != null) {
                        auth.currentUser?.getIdToken(true)?.addOnCompleteListener {
                            if (it.isSuccessful) {
                                idToken = it.result.token
                                Log.d("alo", it.result.token.toString())
                                if (profile.data.token == it.result.token.toString() && !profile.data.token.isNullOrEmpty() && !it.result.token.isNullOrEmpty()) {
                                    if (profile.data.name.isNullOrEmpty()) {
                                        openHome()
                                    } else {
                                        openHome(true)
                                    }
                                } else {
                                    auth.currentUser?.getIdToken(true)?.addOnCompleteListener {
                                        if (it.isSuccessful) {
                                            idToken = it.result.token
                                            var proFile = profile.data
                                            proFile.token = it.result.token.toString()
                                            viewModel.updateToken(proFile)
                                        }
                                    }
                                }
                            }
                        }

                    } else openHome()
                }
                is State.Error -> {
                    binding.lognIn.visibility = View.VISIBLE
                    Toast.makeText(context, profile.exception, Toast.LENGTH_SHORT).show()
                    binding.lognIn.isEnabled = true
                }
                else -> {

                }
            }

        }
    }

    private fun openHome(isOpenHome: Boolean = false) {
        if (isOpenHome) {
            replaceFragment(MainFragment.newInstance(), MainFragment::class.java.name)
        } else {
            replaceFragment(NewProFileFragment.newInstance(), NewProFileFragment::class.java.name)
        }
    }
}