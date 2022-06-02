package com.bangkit.berbuah.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.bangkit.berbuah.R
import com.bangkit.berbuah.ui.login.LoginActivity

class SplashActivity: AppCompatActivity() {
    private var mDelayHandler: Handler? = null
    private var durasi: Long=2500 //2.5 detik

    internal val mRunnable: Runnable = Runnable {
        val intent = Intent(applicationContext, LoginActivity::class.java)
        startActivity(intent)


        finish()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash)

        mDelayHandler = Handler()

        mDelayHandler!!.postDelayed(mRunnable,durasi)
    }
}