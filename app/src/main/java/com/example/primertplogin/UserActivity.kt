package com.example.primertplogin

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import com.example.primertplogin.RegisterActivity.Companion.CREDENTIALS
import com.example.primertplogin.databinding.ActivityUserBinding
import com.google.android.material.navigation.NavigationView

class UserActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

  private lateinit var binding: ActivityUserBinding

  private lateinit var drawerLayout : DrawerLayout

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val toolbar = binding.toolbar
    setSupportActionBar(toolbar)

    drawerLayout = binding.main

    val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_drawer_home_open, R.string.nav_drawer_home_close)
    drawerLayout.addDrawerListener(toggle)

    val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)


    supportActionBar?.setDisplayHomeAsUpEnabled(true)
    supportActionBar?.setHomeButtonEnabled(true)
    supportActionBar?.setHomeAsUpIndicator(R.drawable.ic_menu)
    supportActionBar?.title = "Bienvenido a CineLoop"

    binding.navigationView.setNavigationItemSelectedListener(this, )
    }

  private fun goToLogin() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    }

  override fun onNavigationItemSelected(item: MenuItem): Boolean {
    when(item.itemId) {
      R.id.nav_item_drama -> {
        Toast.makeText(this, "Drama", Toast.LENGTH_SHORT).show()
      }
      R.id.nav_item_accion -> {
        Toast.makeText(this, "Acción", Toast.LENGTH_SHORT).show()
      }
      R.id.nav_item_susp -> {
        Toast.makeText(this, "Suspenso", Toast.LENGTH_SHORT).show()
      }
      R.id.nav_item_fant -> {
        Toast.makeText(this, "Fantasia", Toast.LENGTH_SHORT).show()
      }
      R.id.nav_item_comedia -> {
        Toast.makeText(this, "Comedia", Toast.LENGTH_SHORT).show()
      }
      R.id.nav_item_terr -> {
        Toast.makeText(this, "Terror", Toast.LENGTH_SHORT).show()
      }
        R.id.nav_item_cerrar -> {
          val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
          preferences.edit().remove("autoLogin").apply()
          goToLogin()
          Toast.makeText(this, "Cerrar", Toast.LENGTH_SHORT).show()
        }
    }

    drawerLayout.closeDrawer(GravityCompat.START)
    return true
  }
}