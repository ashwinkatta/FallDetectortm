package com.example.falldetectortm
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.telephony.SmsManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PERMISSION_REQUEST_CODE = 1
        private const val PHONE_NUMBER = "13612195148"  // Number to call (Aidan's)
        private const val TARGET_PHONE_NUMBER = "+13612195148"  // Number to send the SMS to
        private const val MESSAGE = "Test. Ashwin Katta has fallen at location 30.28478293776824, -97.74514412446352!"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CALL_PHONE, Manifest.permission.SEND_SMS), PERMISSION_REQUEST_CODE)
        } else {
            makePhoneCall()
            sendTextMessage()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == PERMISSION_REQUEST_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            makePhoneCall()
            sendTextMessage()
        } else {
            Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show()
        }
    }

    private fun makePhoneCall() {
        val intent = Intent(Intent.ACTION_CALL)
        intent.data = Uri.parse("tel:$PHONE_NUMBER")
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
            startActivity(intent)
        }
    }

    private fun sendTextMessage() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.SEND_SMS) == PackageManager.PERMISSION_GRANTED) {
            val smsManager = SmsManager.getDefault()
            smsManager.sendTextMessage(TARGET_PHONE_NUMBER, null, MESSAGE, null, null)
            Toast.makeText(this, "Message sent", Toast.LENGTH_LONG).show()
        } else {
            Toast.makeText(this, "SMS permission not granted", Toast.LENGTH_LONG).show()
        }
    }
}