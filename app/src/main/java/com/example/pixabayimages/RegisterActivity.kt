package com.example.pixabayimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.pixabayimages.model.UserData
import com.example.pixabayimages.persistence.Preferences

import kotlinx.android.synthetic.main.activity_register.*
import org.koin.android.ext.android.inject

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
        val userList = memoryDb.userList.value

        val typedUsername = username_register.text.toString().toLowerCase()
        val typedPassword = password_register.text.toString()
        val typedConfirmationPassword = confirmation_register.text.toString()

        if(typedPassword != typedConfirmationPassword){
            Toast.makeText(BaseApplication.context, "Password not same!!", Toast.LENGTH_SHORT).show()
            return
        }
        val newUser = UserData()
        newUser.username = typedUsername
        newUser.password = typedPassword

        userList!!.add(newUser)
        memoryDb.userList.value = userList
        Toast.makeText(BaseApplication.context, "Registered!", Toast.LENGTH_SHORT).show()
        userLogin()


    }

    private fun userLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        return startActivity(intent)
    }
}