package br.com.luan.myskeletonv2.view.ui

import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.view.View
import android.widget.Toast
import br.com.luan.myskeletonv2.Manifest

import br.com.luan.myskeletonv2.R

class SplashActivity : BaseActivity() {
    private val REQUEST_PERMISSIONS_CODE = 128

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)


        if (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this,
                        android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, android.Manifest.permission.READ_EXTERNAL_STORAGE,android.Manifest.permission.ACCESS_FINE_LOCATION),
                    REQUEST_PERMISSIONS_CODE)
        } else {
            open()
        }



    }


    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            REQUEST_PERMISSIONS_CODE -> for (i in permissions.indices) {

                if (permissions[i].equals(android.Manifest.permission.WRITE_EXTERNAL_STORAGE, ignoreCase = true) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show()
                    finishAffinity()
                    return
                } else if (permissions[i].equals(android.Manifest.permission.READ_EXTERNAL_STORAGE, ignoreCase = true) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show()
                    finishAffinity()
                    return
                }
                else if (permissions[i].equals(android.Manifest.permission.ACCESS_FINE_LOCATION, ignoreCase = true) && grantResults[i] == PackageManager.PERMISSION_DENIED) {
                    Toast.makeText(this, "Favor habilitar a permissão para usar o aplicativo!", Toast.LENGTH_LONG).show()
                    finishAffinity()
                    return
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        open()
    }

    fun open(){
        Handler().postDelayed({
            val intent = Intent(baseContext, MainActivity::class.java)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
            startActivity(intent)

//            val user = Utils().getSharedAuth(this)
//            user?.let{
//                startActivityClearTask(MainActivity())
//            } ?: run {
//                startActivityClearTask(LoginActivity())
//            }

        }, 3000)
    }




}
