package com.dev.fi.movieticket

import android.content.Intent
import android.os.Bundle
import android.util.SparseArray
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.vision.barcode.Barcode
import info.androidhive.barcode.BarcodeReader


/**
 ****************************************
created by -manca-
.::manca.fi@gmail.com ::.
 ****************************************
 */

class ScanActivity : AppCompatActivity(), BarcodeReader.BarcodeReaderListener {

    private lateinit var barcodeReader: BarcodeReader

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan)

        // get the barcode reader instance
        barcodeReader = (supportFragmentManager.findFragmentById(R.id.barcode_scanner) as BarcodeReader?)!!
    }

    override fun onScanned(barcode: Barcode) {

        // playing barcode reader beep sound
        barcodeReader.playBeep()

        // ticket details activity by passing barcode
        val intent = Intent(this@ScanActivity, TicketResultActivity::class.java)
        intent.putExtra("code", barcode.displayValue)
        startActivity(intent)
    }

    override fun onScannedMultiple(list: List<Barcode>) {

    }

    override fun onBitmapScanned(sparseArray: SparseArray<Barcode>) {

    }

    fun onCameraPermissionDenied() {
        finish()
    }

    override fun onScanError(s: String) {
        Toast.makeText(applicationContext, "Error occurred while scanning $s", Toast.LENGTH_SHORT).show()
    }
}