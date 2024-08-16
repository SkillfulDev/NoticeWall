package ua.chernonog.noticewall.dialoghelper

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ua.chernonog.noticewall.R
import ua.chernonog.noticewall.accounthelper.AccountHelper
import ua.chernonog.noticewall.activity.MainActivity
import ua.chernonog.noticewall.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val activity = act
    val accHelper = AccountHelper(act)

    fun createSignDialog(state: Int) {
        val builder = AlertDialog.Builder(activity)
        val binding = SignDialogBinding.inflate(activity.layoutInflater)
        val view = binding.root
        builder.setView(view)
        setDialogState(state, binding)
        val dialog = builder.create()
        binding.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(state, dialog, binding)
        }
        binding.btForgetP.setOnClickListener {
            seOnClickResetPassword(binding, dialog)
        }
        binding.btGoogleSignIn.setOnClickListener {
            accHelper.signInWithGoogle()
            dialog.dismiss()
        }
        dialog.show()
    }

    private fun seOnClickResetPassword(
        binding: SignDialogBinding,
        dialog: AlertDialog
    ) {
        val email = binding.edSignEmail.text.toString()
        if (binding.edSignEmail.text.isNotEmpty()) {
            activity.firebaseAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.dismiss()
                    Toast.makeText(activity, R.string.email_password_sent, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            binding.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(
        state: Int,
        dialog: AlertDialog,
        binding: SignDialogBinding
    ) {
        dialog.dismiss()
        val email = binding.edSignEmail.text.toString()
        val password = binding.edSignPassword.text.toString()
        if (state == DialogConst.SING_UP_STATE) {
            accHelper.signUpWithEmail(email, password)
        } else {
            accHelper.signInWithEmail(email, password)
        }
    }

    private fun setDialogState(state: Int, binding: SignDialogBinding) {
        if (state == DialogConst.SING_UP_STATE) {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_up)
            binding.btSignUpIn.text =
                activity.resources.getString(R.string.sign_up_action)
        } else {
            binding.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_in)
            binding.btSignUpIn.text =
                activity.resources.getString(R.string.sign_in_action)
            binding.btForgetP.visibility = View.VISIBLE
        }
    }
}
