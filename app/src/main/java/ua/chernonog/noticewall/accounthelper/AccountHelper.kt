package ua.chernonog.noticewall.accounthelper

import android.widget.Toast
import com.google.firebase.auth.FirebaseUser
import ua.chernonog.noticewall.MainActivity
import ua.chernonog.noticewall.R

class AccountHelper(act: MainActivity) {
    private val act = act
    fun signUpWithEmail(email: String, password: String) {
        if (email.isNotEmpty() && password.isNotEmpty()) {
            act.mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
                if (it.isSuccessful) {
                    sentEmailVerification(it.result.user!!)
                } else {
                    Toast.makeText(act, R.string.sing_up_error, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    private fun sentEmailVerification(user: FirebaseUser) {
        user.sendEmailVerification().addOnCompleteListener {
            if (it.isSuccessful) {
                Toast.makeText(act, R.string.sent_verification_done, Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(act, R.string.sent_verification_error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}
