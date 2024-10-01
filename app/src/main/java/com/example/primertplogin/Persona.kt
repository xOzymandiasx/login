package com.example.primertplogin;

import java.io.Serializable

data class Persona (
     val userName: String,
     val password: String
): Serializable //Se utiliza para que pueda ser decodeada a base64
