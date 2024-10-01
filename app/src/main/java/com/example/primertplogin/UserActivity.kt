package com.example.primertplogin

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.primertplogin.RegisterActivity.Companion.CREDENTIALS
import com.example.primertplogin.databinding.ActivityUserBinding

class UserActivity : AppCompatActivity() {

  private lateinit var binding: ActivityUserBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityUserBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)

    binding.button.setOnClickListener {
      preferences.edit().remove("autoLogin").apply()
      goToLogin()
      }
    }

  private fun goToLogin() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    }
  }