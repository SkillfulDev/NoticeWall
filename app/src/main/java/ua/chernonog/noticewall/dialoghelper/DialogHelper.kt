package ua.chernonog.noticewall.dialoghelper

import androidx.appcompat.app.AlertDialog
import ua.chernonog.noticewall.MainActivity
import ua.chernonog.noticewall.R
import ua.chernonog.noticewall.accounthelper.AccountHelper
import ua.chernonog.noticewall.databinding.SignDialogBinding

class DialogHelper(act: MainActivity) {
    private val act = act
    private val accHelper = AccountHelper(act)

    fun createSignDialog(state: Int) {
        val builder = AlertDialog.Builder(act)
        val rootDialogElement = SignDialogBinding.inflate(act.layoutInflater)
        val view = rootDialogElement.root
        if (state == DialogConst.SING_UP_STATE) {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_up)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_up_action)
        } else {
            rootDialogElement.tvSignTitle.text = act.resources.getString(R.string.ac_sign_in)
            rootDialogElement.btSignUpIn.text = act.resources.getString(R.string.sign_in_action)
        }
        rootDialogElement.btSignUpIn.setOnClickListener {
            val email = rootDialogElement.edSignEmail.text.toString()
            val password = rootDialogElement.edSignPassword.text.toString()
            if (state == DialogConst.SING_UP_STATE) {
                accHelper.signUpWithEmail(email, password)
            } else {

            }
        }
        builder.setView(view)
        builder.show()
    }
}
