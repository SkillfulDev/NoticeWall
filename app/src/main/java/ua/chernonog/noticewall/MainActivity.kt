package ua.chernonog.noticewall

import android.os.Bundle
import android.view.Gravity
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.GravityCompat
import androidx.core.view.accessibility.AccessibilityManagerCompat.TouchExplorationStateChangeListener
import androidx.drawerlayout.widget.DrawerLayout
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        init()


    }

    private fun init() {
        val navView = findViewById<NavigationView>(R.id.navView)
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        val toggle = ActionBarDrawerToggle(
            this,
            drawerLayout,
            toolbar,
            R.string.open,
            R.string.close
        )
        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()
        navView.setNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        val drawerLayout = findViewById<DrawerLayout>(R.id.drawerLayout)
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
                Toast.makeText(this, "Pressed sign up", Toast.LENGTH_SHORT).show()
            }

            R.id.id_sign_in -> {
                Toast.makeText(this, "Pressed sing in", Toast.LENGTH_SHORT).show()
            }

            R.id.id_sign_out -> {
                Toast.makeText(this, "Pressed sign out", Toast.LENGTH_SHORT).show()
            }
        }
        drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

}