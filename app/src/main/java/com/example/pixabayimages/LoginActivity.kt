package com.example.pixabayimages

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.pixabayimages.BaseApplication.Companion.context
import com.example.pixabayimages.fragment.FragmentAccount
import com.example.pixabayimages.model.UserData
import com.example.pixabayimages.persistence.Preferences
import kotlinx.android.synthetic.main.activity_login.*
import org.koin.android.ext.android.inject
import java.util.*
import kotlin.math.log

class LoginActivity : AppCompatActivity() {
    private val memoryDb: MemoryDb by inject()
    private val preferences: Preferences by inject()
    private val fragmentAccount: FragmentAccount by inject()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            userLogin()
        }

        btnRegisterInstead.setOnClickListener{
            userRegister()
        }
    }

    private fun userRegister() {
        val intent = Intent(this, RegisterActivity::class.java)
        return startActivity(intent)
    }

    private fun userLogin(): Boolean {
        val typedUsername = username_login.text.toString().toLowerCase(Locale.ROOT)
        val typedPassword = password_login.text.toString()

        val userList = preferences.getUserList()
        var userData : UserData? = null
        for (item in userList){
            if (item?.username.equals(typedUsername)){
                userData = item

            }
        }
        val userPassword = userData?.password.toString()
        Log.d(TAG, "userLogin: yes $userPassword")
        if (typedPassword == userPassword){
            if (userData != null) {
                preferences.setCurrentUser(userData)
            }
            Log.d(TAG, "userLogin: password is same")
            val intent = Intent(this, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_REORDER_TO_FRONT;
            fragmentAccount.setUsername(userData!!.username)
            return startActivityIfNeeded(intent, 0);
        }

        Toast.makeText(context, "Login Failed!", Toast.LENGTH_SHORT).show()

        return true
    }

}