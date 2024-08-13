package ua.chernonog.noticewall.accounthelper

import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ua.chernonog.noticewall.MainActivity
import ua.chernonog.noticewall.R
import ua.chernonog.noticewall.dialoghelper.GoogleConst.GOOGLE_SIGN_IN_REQUEST_CODE

class AccountHelper(act: MainActivity) {
    private val activity = act
    private lateinit var signInClient: GoogleSignInClient
    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    sentEmailVerification(it.result.user!!)
                    activity.uiUpdate(it.result.user)

                } else {
                    Toast.makeText(activity, R.string.sing_up_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    activity.uiUpdate(it.result.user)

                } else {
                    Toast.makeText(
                        activity,
                        activity.resources.getString(R.string.sing_in_error),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.client_id)).build()
        return GoogleSignIn.getClient(activity, gso)
    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intend = signInClient.signInIntent
        activity.startActivityForResult(intend, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.mAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(activity, "Ok", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sentEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.sent_verification_done),
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                Toast.makeText(
                    activity,
                    activity.resources.getString(R.string.sent_verification_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}
