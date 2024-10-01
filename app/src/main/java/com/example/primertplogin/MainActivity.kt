package com.example.primertplogin

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.primertplogin.RegisterActivity.Companion.CREDENTIALS
import com.example.primertplogin.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class MainActivity : AppCompatActivity() {

  private lateinit var binding: ActivityMainBinding
  private lateinit var preferences: SharedPreferences

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)
    preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
    val edit = preferences.edit()


    val autoLogin = preferences.getBoolean("autoLogin", false)
    if (autoLogin) {
      goToHomeActivity()
    }

    binding.buttonRegister.setOnClickListener {
      val intent = Intent(this, RegisterActivity::class.java)
      startActivity(intent)
    }

    binding.buttonSigIn.setOnClickListener {
      val checkBox = binding.checkbox.isChecked
      val user = binding.editUser.text.toString()
      val password = binding.editPassword.text.toString()

      edit.putBoolean("autoLogin", checkBox)
      edit.apply()

      if (validateAutoLogin()) {
        goToHomeActivity()
      } else {
        if (validateData(user, password)) {
          edit.putBoolean("autoLogin", checkBox)
          edit.apply()
          goToHomeActivity()
        } else {
          Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
          edit.putBoolean("autoLogin", false)
          edit.apply()
        }
      }
    }
  }

  override fun onResume() {
    super.onResume()
    val autologin = preferences.getBoolean("autoLogin", false)

    if (autologin) {
      goToHomeActivity()
    }
  }

  private fun goToHomeActivity() {
    val intent = Intent(this, UserActivity::class.java)
    startActivity(intent)
  }

  private fun validateData(user: String, password: String): Boolean {
    try {
      val personJson = preferences.getString("person_list", "[]")
      val gson = Gson()
      val personListType = object : TypeToken<List<Persona>>() {}.type
      val personList : List<Persona> = gson.fromJson(personJson, personListType)

      for (person in personList) {
        if (person.userName == user && person.password == password) {
          return true
        }
      }
    } catch (e: Exception) {
      e.printStackTrace()
    }
    return false
  }

  private fun validateAutoLogin(): Boolean {
    val autologin = preferences.getBoolean("autoLogin", false)
    return autologin
  }
}