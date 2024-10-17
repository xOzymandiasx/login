package com.example.primertplogin

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.primertplogin.databinding.ActivityRegisterBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import android.R
import android.view.View
import android.widget.AdapterView

class RegisterActivity : AppCompatActivity() {
  private lateinit var binding: ActivityRegisterBinding
  val arrayGenres: Array<Genres> = Genres.values()
  var genreSelected: Genres? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityRegisterBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val adapter = ArrayAdapter(this, R.layout.simple_spinner_item, arrayGenres)

    binding.genreSelection.adapter = adapter

    binding.genreSelection.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
      override fun onItemSelected(p0: AdapterView<*>?, p1: View?, position: Int, p3: Long) {
        genreSelected = arrayGenres.get(position)
      }

      override fun onNothingSelected(p0: AdapterView<*>?) {
        genreSelected = null
      }
    }

    binding.buttonRegister.setOnClickListener {
      val user = binding.editUser.text.toString()
      val password = binding.editPassword.text.toString()

      if (user.isNotEmpty() && password.isNotEmpty() && genreSelected != null) {
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
          val persona = Persona(userName = user, password = password, genre = genreSelected!! )
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