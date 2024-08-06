package ua.chernonog.noticewall

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import ua.chernonog.noticewall.databinding.ActivityMainBinding
import ua.chernonog.noticewall.dialoghelper.DialogConst
import ua.chernonog.noticewall.dialoghelper.DialogHelper

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    val mAuth = FirebaseAuth.getInstance()
    private val dialogHelper = DialogHelper(this)
    private lateinit var rootElement: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        rootElement = ActivityMainBinding.inflate(layoutInflater)
        val view = rootElement.root
        setContentView(view)
        init()
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
                Toast.makeText(this, "Pressed sign out", Toast.LENGTH_SHORT).show()
            }
        }
        rootElement.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }
}
