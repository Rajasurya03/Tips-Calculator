package com.example.tip_calculator

import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView

private const val TAG = "MainActivity"
private const val InitialPercentage = 14

class MainActivity : AppCompatActivity() {

    private lateinit var etPriceAmount : EditText
    private lateinit var tvTipPercentage : TextView
    private lateinit var seekBarTip : SeekBar
    private lateinit var tvTipAmount : TextView
    private lateinit var etNumberOfPeople : EditText
    private lateinit var tvTotal : TextView
    private lateinit var tvTotalPerPerson : TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        etPriceAmount = findViewById(R.id.etPriceAmount)
        tvTipPercentage = findViewById(R.id.tvTipPercentage)
        seekBarTip = findViewById(R.id.seekBarTip)
        tvTipAmount = findViewById(R.id.tvTipAmount)
        etNumberOfPeople = findViewById(R.id.etNumberOfPeople)
        tvTotal = findViewById(R.id.tvTotal)
        tvTotalPerPerson = findViewById(R.id.tvTotalPerPerson)

        seekBarTip.progress= InitialPercentage
        tvTipPercentage.text= "$InitialPercentage%"

        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                Log.i(TAG, "OnProgressChange : $p1")
                tvTipPercentage.text = "$p1%"
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {}

            override fun onStopTrackingTouch(p0: SeekBar?) {}

        })
        etPriceAmount.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged : $p0")
                computeTipandTotal()
            }

        })
    }

    private fun computeTipandTotal() {
        TODO("Not yet implemented")
    }
}