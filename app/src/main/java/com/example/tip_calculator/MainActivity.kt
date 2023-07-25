package com.example.tip_calculator

import android.animation.ArgbEvaluator
import android.nfc.Tag
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat
import org.w3c.dom.Text

private const val TAG = "MainActivity"
private const val InitialPercentage = 40

class MainActivity : AppCompatActivity() {

    private lateinit var etPriceAmount : EditText
    private lateinit var tvTipPercentage : TextView
    private lateinit var seekBarTip : SeekBar
    private lateinit var tvTipAmount : TextView
    private lateinit var etNumberOfPeople : EditText
    private lateinit var tvTotal : TextView
    private lateinit var tvTotalPerPerson : TextView
    private lateinit var tvTipDiscription : TextView

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
        tvTipDiscription = findViewById(R.id.tvTipDiscription)

        seekBarTip.progress= InitialPercentage
        tvTipPercentage.text= (InitialPercentage / 3.3).toInt().toString() + "%"
        updateTipDiscription((InitialPercentage / 3.3).toInt())
        seekBarTip.setOnSeekBarChangeListener(object: SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                val p = (p1/3.3).toInt()
                Log.i(TAG, "OnProgressChange : $p")
                tvTipPercentage.text = "$p%"

                updateTipDiscription(p)
                computeTipandTotal()
                computeTotalPerPerson()
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
        etNumberOfPeople.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(p0: Editable?) {
                Log.i(TAG, "afterTextChanged : $p0")
                computeTotalPerPerson()
            }

        })
    }

    private fun updateTipDiscription(p1: Int) {
        val tipDescription = when(p1) {
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..19 -> "Good"
            in 20..25 -> "Great"
            else -> "Amazing"
        }
        tvTipDiscription.text = tipDescription

        // Updating the color based on tipPercent
        val color = ArgbEvaluator().evaluate(
            (p1*3.3).toFloat()/seekBarTip.max,
            ContextCompat.getColor(this, R.color.color_worst_tip),
            ContextCompat.getColor(this, R.color.color_best_tip)
        ) as Int
        tvTipDiscription.setTextColor(color)
    }

    private fun computeTipandTotal() {
        if(etPriceAmount.text.isEmpty() or etNumberOfPeople.text.isEmpty()){
            tvTipAmount.text = "0"
            tvTotal.text = "0"
            tvTotalPerPerson.text = "0"
            return
        }
        val baseAmount = etPriceAmount.text.toString().toDouble()
        val tipPercentage = (seekBarTip.progress / 3.3).toInt()
        val tipAmount = baseAmount * tipPercentage / 100
        tvTipAmount.text = "%.2f".format(tipAmount)
        tvTotal.text = "%.2f".format(baseAmount + tipAmount)
        computeTotalPerPerson()
    }
    private fun computeTotalPerPerson() {
        if(etNumberOfPeople.text.isEmpty() or etPriceAmount.text.isEmpty()){
            tvTotalPerPerson.text = "0"
            return
        }
        val totalAmount = tvTotal.text.toString().toDouble()
        val members = etNumberOfPeople.text.toString().toInt()
        tvTotalPerPerson.text = "%.2f".format(totalAmount / members)
    }

}