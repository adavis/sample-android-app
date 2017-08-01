package info.adavis.sampleapp

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.ActivityCompat.requestPermissions
import android.support.v4.app.ActivityCompat.shouldShowRequestPermissionRationale
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import timber.log.Timber

class GrantPermissionsActivity : AppCompatActivity() {

    val myButton: Button by lazy { findViewById<Button>(R.id.button) }
    val rootView: View by lazy { findViewById<View>(android.R.id.content) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_grant_permissions)

        myButton.setOnClickListener { attemptToMakeCall() }
    }

    fun attemptToMakeCall(): Unit {
        if (hasCallPhonePermission()) {
            makeCall()
        } else {
            if (shouldShowRequestPermissionRationale(this, PHONE_PERMISSION)) {

                Snackbar.make(rootView, R.string.permissions_rationale, Snackbar.LENGTH_INDEFINITE)
                        .setAction(R.string.ok) {
                            requestPermissions(this,
                                               arrayOf(PHONE_PERMISSION),
                                               PERMISSIONS_REQ_CODE)
                        }
                        .show()

            } else {
                requestPermissions(this, arrayOf(PHONE_PERMISSION), PERMISSIONS_REQ_CODE)
            }
        }
    }

    private fun hasCallPhonePermission(): Boolean {
        return (ContextCompat.checkSelfPermission(this, PHONE_PERMISSION)
                    == PackageManager.PERMISSION_GRANTED)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>,
                                            grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSIONS_REQ_CODE
                && grantResults.isNotEmpty()
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makeCall()
        }
    }

    @SuppressLint("MissingPermission")
    private fun makeCall() {
        try {
            val intent = Intent(Intent.ACTION_CALL, PHONE_NUMBER)
            startActivity(intent)
        } catch (e: Exception) {
            Timber.e(e, "unable to make the call")
        }
    }

    companion object {
        private val PERMISSIONS_REQ_CODE = 1
        private val PHONE_PERMISSION = Manifest.permission.CALL_PHONE

        val PHONE_NUMBER: Uri = Uri.parse("tel:5551231234")
    }
}
