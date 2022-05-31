package com.bangkit.berbuah.ui.login

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.bangkit.berbuah.R

import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.databinding.ActivityLoginBinding
import com.bangkit.berbuah.model.UserModel
import com.bangkit.berbuah.ui.home.HomeActivity
import com.bangkit.berbuah.ui.signup.SignupActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")
class LoginActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var loginViewModel: LoginViewModel
    private lateinit var  binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences
    private var isRemembered = false
    private lateinit var user: UserModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        sharedPreferences = getSharedPreferences(SHARED_PREFERENCES, Context.MODE_PRIVATE)
        isRemembered = sharedPreferences.getBoolean(IS_LOGIN, false)
        hasLogin(isRemembered)

        binding.loginButton.setOnClickListener(this)
        binding.signupButton.setOnClickListener(this)

        //setupViewModel()
        playAnimation()
        setupView()
        setupAction()
    }

 //   private fun setupViewModel() {
   //     loginViewModel = ViewModelProvider(
     //       this,
       //     ViewModelFactory(UserPreference.getInstance(dataStore))
      //  )[LoginViewModel::class.java]

     //   loginViewModel.getUser().observe(this, { user ->
       //     this.user = user
      //  })
    //}

    private fun setupAction() {
        binding.loginButton.setOnClickListener {
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                email.isEmpty() -> {
                    binding.emailEditText.error = "Enter Your Email"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Enter Your Password"
                }
                else -> {
                    loginViewModel.login()
                    showLoading(true)
                    val email = binding.emailEditText.text.toString().trim()
                    val password = binding.passwordEditText.text.toString().trim()
                    ApiConfig.getApiService()
                        .login(email, password)
                        .enqueue(object: Callback<LoginResponse> {
                            override fun onResponse(
                                call: Call<LoginResponse>,
                                response: Response<LoginResponse>
                            ) {
                                if(response.isSuccessful) {

                                    response.body()?.loginResult?.apply {
                                        validateLogin(userId, name, token, isLogin = true)
                                    }
                                    val mainIntent = Intent(this@LoginActivity, HomeActivity::class.java)
                                    showLoading(false)
                                    startActivity(mainIntent)
                                    finish()
                                }
                            }

                            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                                showLoading(false)
                                Toast.makeText(this@LoginActivity, "Data yang dimasukan tidak valid", Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }
        }
    }


    private fun setupView() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun hasLogin(boolean: Boolean) {
        if(boolean) {
            val intent = Intent(this, HomeActivity::class.java)
            startActivity(intent)
        }
    }


    override fun onClick(view: View) {
        when(view.id) {
            R.id.signupButton -> {
                val intent = Intent(this@LoginActivity, SignupActivity::class.java)
                startActivity(intent)
                finish()
            }
        }
    }

    private fun validateLogin(userId: String, name: String, token: String, isLogin: Boolean){
        val editor: SharedPreferences.Editor = sharedPreferences.edit()
        editor.putString(NAME, name)
        editor.putString(USER_ID, userId)
        editor.putString(TOKEN, token)
        editor.putBoolean(IS_LOGIN, isLogin)
        editor.apply()
    }

    private fun saveToken(token: String, name: String, email: String){
        val preferences = getSharedPreferences("myPrefs", MODE_PRIVATE)
        preferences.edit().putString("token", token).apply()
        preferences.edit().putString("name", name).apply()
        preferences.edit().putString("email", email).apply()
    }

    private fun showLoading(isLoading: Boolean) {
        if(isLoading) binding.progressBar.visibility = View.VISIBLE
        else binding.progressBar.visibility = View.GONE
    }

    private fun playAnimation() {
        ObjectAnimator.ofFloat(binding.imageView, View.TRANSLATION_X, -30f, 30f).apply {
            duration = 6000
            repeatCount = ObjectAnimator.INFINITE
            repeatMode = ObjectAnimator.REVERSE
        }.start()

        val title = ObjectAnimator.ofFloat(binding.tvLogin, View.ALPHA, 1f).setDuration(500)
        val message = ObjectAnimator.ofFloat(binding.tvTitle, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val login = ObjectAnimator.ofFloat(binding.loginButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                message,
                emailTextView,
                passwordTextView,
                login)
            startDelay = 500
        }.start()
    }

    companion object {
        val SHARED_PREFERENCES = "shared_preferences"
        val NAME = "name"
        val USER_ID = "user_id"
        val TOKEN = "token"
        val IS_LOGIN = "is_login"
    }

}