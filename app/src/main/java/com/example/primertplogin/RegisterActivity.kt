package com.example.primertplogin

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.primertplogin.databinding.ActivityRegisterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class RegisterActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRegisterBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.buttonRegister.setOnClickListener {
      val user = binding.editUser.text.toString()
      val password = binding.editPassword.text.toString()

      if (user.isNotEmpty() && password.isNotEmpty()) {
        val preferences = getSharedPreferences(CREDENTIALS, MODE_PRIVATE)
        val edit = preferences.edit()
        val gson = Gson()

        val existingPersonsJson = preferences.getString("person_list", "[]")
        val personListType = object : TypeToken<MutableList<Persona>>() {}.type
        val personList: MutableList<Persona> = gson.fromJson(existingPersonsJson, personListType)

        var userExist = false
        for (person in personList) {
          if (person.userName == user) {
            userExist = true
            break
          }
        }

        if (userExist) {
          Toast.makeText(this, "El usuario ya existe", Toast.LENGTH_SHORT).show()
        } else {
          val persona = Persona(userName = user, password = password)
          personList.add(persona)

          val personListInJsonFormat = gson.toJson(personList)
          edit.putString("person_list", personListInJsonFormat)
          edit.apply()

          Toast.makeText(this, "Usuario registrado con éxito", Toast.LENGTH_SHORT).show()
          goToMainActivity()
        }
      } else {
        Toast.makeText(this, "Campos incompletos", Toast.LENGTH_SHORT).show()
      }
    }
  }

  private fun goToMainActivity() {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
  }

  //Acá definimos las variables que queremos que tengan alcance global
  companion object {
    const val CREDENTIALS = "Credenciales"
  }
}