package ua.chernonog.noticewall.accounthelper

import android.util.Log
import android.widget.Toast
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.GoogleAuthProvider
import ua.chernonog.noticewall.R
import ua.chernonog.noticewall.activity.MainActivity
import ua.chernonog.noticewall.dialoghelper.FirebaseErrorConst
import ua.chernonog.noticewall.dialoghelper.GoogleConst.GOOGLE_SIGN_IN_REQUEST_CODE

class AccountHelper(act: MainActivity) {
    private val activity = act
    private lateinit var signInClient: GoogleSignInClient

    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        sentEmailVerification(it.result.user!!)
                        activity.uiUpdate(it.result.user)
                    } else {
                        Log.d("Exception", "${it.exception}")
                        if (it.exception is FirebaseAuthUserCollisionException) {
                            val exception = it.exception as
                                    FirebaseAuthUserCollisionException
                            Toast.makeText(
                                activity,
                                exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                            linkEmailToGoogle(email, password)
                        } else if (it.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception = it.exception as
                                    FirebaseAuthInvalidCredentialsException
                            Toast.makeText(
                                activity,
                                exception.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
                }
        }
    }

    fun signInWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            activity.firebaseAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener {
                    if (it.isSuccessful) {
                        activity.uiUpdate(it.result.user)
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.sing_in_success),
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        if (it.exception is FirebaseAuthInvalidCredentialsException) {
                            val exception = it.exception as
                                    FirebaseAuthInvalidCredentialsException
                            Log.d("ExceptionMy", exception.errorCode)
                            if (exception.errorCode == FirebaseErrorConst.ERROR_INVALID_CREDENTIAL) {
                                Toast.makeText(
                                    activity,
                                    FirebaseErrorConst.ERROR_INVALID_CREDENTIAL,
                                    Toast.LENGTH_SHORT
                                ).show()
                            } else {
                                Toast.makeText(
                                    activity,
                                    exception.message,
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }
                }
        }
    }

    fun signInWithGoogle() {
        signInClient = getSignInClient()
        val intend = signInClient.signInIntent
        activity.startActivityForResult(intend, GOOGLE_SIGN_IN_REQUEST_CODE)
    }

    fun signInFirebaseWithGoogle(token: String) {
        val credential = GoogleAuthProvider.getCredential(token, null)
        activity.firebaseAuth.signInWithCredential(credential).addOnCompleteListener {
            if (it.isSuccessful) {
                activity.uiUpdate(it.result.user)
            } else {
                Log.d("GoogleSignIn", "${it.exception?.message}")
            }
        }
    }

    fun signOutFromGoogle() {
        getSignInClient().signOut()
    }

    private fun getSignInClient(): GoogleSignInClient {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(activity.getString(R.string.client_id)).requestEmail().build()
        return GoogleSignIn.getClient(activity, gso)
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

    private fun linkEmailToGoogle(email: String, password: String) {
        val credential = EmailAuthProvider.getCredential(email, password)
        if (activity.firebaseAuth.currentUser == null) {
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.link_error),
                Toast.LENGTH_SHORT
            ).show()
        } else {
            activity.firebaseAuth.currentUser?.linkWithCredential(credential)
                ?.addOnCompleteListener {
                    if (it.isSuccessful) {
                        Toast.makeText(
                            activity,
                            activity.resources.getString(R.string.link_done),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
        }
    }
}
