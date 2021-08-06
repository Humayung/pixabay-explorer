package com.example.pixabayimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pixabayimages.model.UserData
import com.example.pixabayimages.persistence.Preferences

import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject
import java.util.*

class RegisterActivity : AppCompatActivity() {
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)


        btnRegister.setOnClickListener{
            userRegister()
        }
        btnLoginInstead.setOnClickListener{
            userLogin()
        }

    }

    private fun userRegister(){
        val userList = preferences.getUserList()

        val typedUsername = username_register.text.toString().toLowerCase()
        val typedPassword = password_register.text.toString()

        val newUser = UserData()
        newUser.username = typedUsername
        newUser.password = typedPassword

        userList.add(newUser)
        preferences.setUserList(userList)
        Toast.makeText(BaseApplication.context, "Registered!", Toast.LENGTH_SHORT).show()
        userLogin()


    }

    private fun userLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        return startActivity(intent)
    }
}