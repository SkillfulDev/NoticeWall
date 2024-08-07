package ua.chernonog.noticewall.dialoghelper

import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import ua.chernonog.noticewall.MainActivity
import ua.chernonog.noticewall.R
import ua.chernonog.noticewall.accounthelper.AccountHelper
import ua.chernonog.noticewall.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val activity = act
    private val accHelper = AccountHelper(act)

    fun createSignDialog(state: Int) {
        val builder = AlertDialog.Builder(activity)
        val rootDialogElement = SignDialogBinding.inflate(activity.layoutInflater)
        val view = rootDialogElement.root
        builder.setView(view)
        setDialogState(state, rootDialogElement)
        val dialog = builder.create()
        rootDialogElement.btSignUpIn.setOnClickListener {
            setOnClickSignUpIn(state, dialog, rootDialogElement)
        }
        rootDialogElement.btForgetP.setOnClickListener{
            seOnClickResetPassword(rootDialogElement, dialog)
        }

        dialog.show()
    }

    private fun seOnClickResetPassword(
        rootDialogElement: SignDialogBinding,
        dialog: AlertDialog
    ) {
        val email = rootDialogElement.edSignEmail.text.toString()
        if (rootDialogElement.edSignEmail.text.isNotEmpty()) {
            activity.mAuth.sendPasswordResetEmail(email).addOnCompleteListener {
                if (it.isSuccessful) {
                    dialog.dismiss()
                    Toast.makeText(activity, R.string.email_password_sent, Toast.LENGTH_LONG).show()
                }
            }
        } else {
            rootDialogElement.tvDialogMessage.visibility = View.VISIBLE
        }
    }

    private fun setOnClickSignUpIn(
        state: Int,
        dialog: AlertDialog,
        rootDialogElement: SignDialogBinding
    ) {
        dialog.dismiss()
        val email = rootDialogElement.edSignEmail.text.toString()
        val password = rootDialogElement.edSignPassword.text.toString()
        if (state == DialogConst.SING_UP_STATE) {
            accHelper.signUpWithEmail(email, password)
        } else {
            accHelper.signInWithEmail(email, password)
            Toast.makeText(
                activity,
                activity.resources.getString(R.string.sing_in_success),
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun setDialogState(state: Int, rootDialogElement: SignDialogBinding) {
        if (state == DialogConst.SING_UP_STATE) {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_up)
            rootDialogElement.btSignUpIn.text = activity.resources.getString(R.string.sign_up_action)
        } else {
            rootDialogElement.tvSignTitle.text = activity.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = activity.resources.getString(R.string.sign_in_action)
            rootDialogElement.btForgetP.visibility = View.VISIBLE
        }
    }
}
