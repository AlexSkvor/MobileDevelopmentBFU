package ru.alexskvortsov.lab1

import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.core.app.ActivityCompat
import timber.log.Timber

class MainActivity : LoggingLifecycleActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    companion object {
        private const val MY_PERMISSIONS_REQUEST_CODE = 1
    }

    private var listOfNonGrantedPermissions: MutableList<String> = mutableListOf()

    override fun onStart() {
        super.onStart()
        val info = packageManager.getPackageInfo(this.packageName, PackageManager.GET_PERMISSIONS)
        Timber.d("SDK ${Build.VERSION.SDK_INT}  App Permissions:")
        info.requestedPermissions?.forEach {
            if (checkPermission(it, android.os.Process.myPid(), android.os.Process.myUid()) == PackageManager.PERMISSION_GRANTED) {
                Timber.d("$it PERMISSION_GRANTED")
            } else {
                Timber.d("$it PERMISSION_DENIED")
                listOfNonGrantedPermissions.add(it)
            }
        }
        makeRequestForNotGrantedPermissions()
    }

    private fun makeRequestForNotGrantedPermissions() {
        if (listOfNonGrantedPermissions.isNotEmpty()) {
            val nextPermission = arrayOf(listOfNonGrantedPermissions.first())
            listOfNonGrantedPermissions.removeAt(0)
            ActivityCompat.requestPermissions(
                this,
                nextPermission,
                MY_PERMISSIONS_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when (requestCode) {
            MY_PERMISSIONS_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Timber.d("Granted")
                } else {
                    Timber.d("NOT Granted")
                }
                return makeRequestForNotGrantedPermissions()
            }
        }
    }
}
