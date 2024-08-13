package ua.chernonog.noticewall

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import ua.chernonog.noticewall.databinding.ActivityMainBinding
import ua.chernonog.noticewall.dialoghelper.DialogConst
import ua.chernonog.noticewall.dialoghelper.DialogHelper
import ua.chernonog.noticewall.dialoghelper.GoogleConst.GOOGLE_SIGN_IN_REQUEST_CODE

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val mAuth = FirebaseAuth.getInstance()
    private val dialogHelper = DialogHelper(this)
    private lateinit var rootElement: ActivityMainBinding
    private lateinit var tvAccount: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
    }

    override fun onStart() {
        super.onStart()
        uiUpdate(mAuth.currentUser)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == GOOGLE_SIGN_IN_REQUEST_CODE) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    dialogHelper.accHelper.signInFirebaseWithGoogle(account.idToken!!)
                }
            } catch (e: ApiException) {
                Log.d("Error", "Api error ${e.message}")
            }
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.id_my_ads -> {
                Toast.makeText(this, "Pressed my_ads", Toast.LENGTH_SHORT).show()
            }

            R.id.id_car -> {
                Toast.makeText(this, "Pressed car", Toast.LENGTH_SHORT).show()
            }

            R.id.id_pc -> {
                Toast.makeText(this, "Pressed pc", Toast.LENGTH_SHORT).show()
            }

            R.id.id_smartphone -> {
                Toast.makeText(this, "Pressed smartphone", Toast.LENGTH_SHORT).show()
            }

            R.id.id_dm -> {
                Toast.makeText(this, "Pressed dm", Toast.LENGTH_SHORT).show()
            }

            R.id.id_sign_up -> {
                dialogHelper.createSignDialog(DialogConst.SING_UP_STATE)
            }

            R.id.id_sign_in -> {
                dialogHelper.createSignDialog(DialogConst.SING_IN_STATE)
            }

            R.id.id_sign_out -> {
                uiUpdate(null)
                mAuth.signOut()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    fun uiUpdate(user: FirebaseUser?) {
        tvAccount.text = if (user == null) {
            resources.getString(R.string.not_reg)
        } else {
            user.email
        }
    }

    private fun init() {
        val toggle = ActionBarDrawerToggle(
            this,
            rootElement.drawerLayout,
            rootElement.mainContent.toolbar,
            R.string.open,
            R.string.close
        )
        rootElement.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        rootElement.navView.setNavigationItemSelectedListener(this)
        tvAccount = rootElement.navView.getHeaderView(0).findViewById(R.id.tvAccountEmail)
    }
}
