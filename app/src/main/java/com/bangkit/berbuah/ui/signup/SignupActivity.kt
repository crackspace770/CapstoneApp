package com.bangkit.berbuah.ui.signup

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.R

import com.bangkit.berbuah.api.ApiConfig
import com.bangkit.berbuah.databinding.ActivitySignupBinding

import com.bangkit.berbuah.ui.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity: AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivitySignupBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signupButton.setOnClickListener(this)
        binding.backToLogin.setOnClickListener(this)

        setupAction()
        playAnimation()
        setupView()
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


    private fun setupAction() {
        binding.signupButton.setOnClickListener {
            val name = binding.nameEditText.text.toString()
            val email = binding.emailEditText.text.toString()
            val password = binding.passwordEditText.text.toString()
            when {
                name.isEmpty() -> {
                    binding.nameEditText.error = "Enter your name"
                }
                email.isEmpty() -> {
                    binding.emailEditText.error = "Enter your password"
                }
                password.isEmpty() -> {
                    binding.passwordEditText.error = "Enter your password, 6 Characters in minimum"
                }
                password.length < 6 -> {
                    binding.passwordEditText.error = "Password should be 6 characters in minimum"
                }
                else -> {
                    showLoading(true)
                    ApiConfig.getApiService()
                        .signup(name, email, password)
                        .enqueue(object: Callback<SignupResponse> {
                            override fun onResponse(
                                call: Call<SignupResponse>,
                                response: Response<SignupResponse>
                            ) {
                                if(response.isSuccessful) {

                                    Toast.makeText(this@SignupActivity, "Account created, go back to login page", Toast.LENGTH_SHORT).show()
                                }
                                val mainIntent = Intent(this@SignupActivity, LoginActivity::class.java)
                                showLoading(false)
                                startActivity(mainIntent)
                                finish()
                            }

                            override fun onFailure(call: Call<SignupResponse>, t: Throwable) {
                                showLoading(false)
                                Toast.makeText(this@SignupActivity, "Failed to create account", Toast.LENGTH_SHORT).show()
                                Toast.makeText(this@SignupActivity, "Try Again", Toast.LENGTH_SHORT).show()
                            }

                        })
                }
            }
        }
    }

    override fun onClick(v: View?) {
        when(v?.id) {
            R.id.signupButton -> {
                if(validateCreateAccount()) {
                    clearEditText()
                } else {
                    clearEditText()
                }
            }
            R.id.backToLogin-> {
                val intent = Intent(this@SignupActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

    }



    private fun clearEditText() {
        binding.nameEditText.text!!.clear()
        binding.emailEditText.text!!.clear()
        binding.passwordEditText.text!!.clear()
    }

    private fun validateCreateAccount(): Boolean {
        return if(binding.emailEditText.text!!.isNotEmpty()
            && binding.passwordEditText.text!!.isNotEmpty()
            && binding.nameEditText.text!!.isNotEmpty()
            && android.util.Patterns.EMAIL_ADDRESS.matcher(binding.emailEditText.text.toString()).matches()
            && binding.passwordEditText.text.toString().length <= 5) {
            true
        } else {
            Toast.makeText(this, "Data harus diisi dengan benar", Toast.LENGTH_SHORT).show()
            false
        }

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

        val title = ObjectAnimator.ofFloat(binding.titleTextView, View.ALPHA, 1f).setDuration(500)
        val nameTextView = ObjectAnimator.ofFloat(binding.nameTextView, View.ALPHA, 1f).setDuration(500)
        val emailTextView = ObjectAnimator.ofFloat(binding.emailTextView, View.ALPHA, 1f).setDuration(500)
        val passwordTextView = ObjectAnimator.ofFloat(binding.passwordTextView, View.ALPHA, 1f).setDuration(500)
        val signup = ObjectAnimator.ofFloat(binding.signupButton, View.ALPHA, 1f).setDuration(500)

        AnimatorSet().apply {
            playSequentially(
                title,
                nameTextView,
                emailTextView,
                passwordTextView,
                signup
            )
            startDelay = 500
        }.start()
    }

}