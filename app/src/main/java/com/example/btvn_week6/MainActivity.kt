package com.example.btvn_week6

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var sourceAmount: EditText
    private lateinit var targetAmount: EditText
    private lateinit var sourceCurrency: Spinner
    private lateinit var targetCurrency: Spinner

    private val currencyRates = mapOf(
        "USD" to 1.0,
        "EUR" to 0.85,
        "VND" to 23000.0
        // Thêm tỷ giá khác nếu cần
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sourceAmount = findViewById(R.id.source_amount)
        targetAmount = findViewById(R.id.target_amount)
        sourceCurrency = findViewById(R.id.source_currency)
        targetCurrency = findViewById(R.id.target_currency)

        setupSpinners()
        setupListeners()
    }

    private fun setupSpinners() {
        val currencies = currencyRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        sourceCurrency.adapter = adapter
        targetCurrency.adapter = adapter
    }

    private fun setupListeners() {
        sourceAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        sourceCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        targetCurrency.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }
    }

    private fun convertCurrency() {
        val sourceValue = sourceAmount.text.toString().toDoubleOrNull() ?: return
        val sourceCurrencyValue = sourceCurrency.selectedItem.toString()
        val targetCurrencyValue = targetCurrency.selectedItem.toString()

        val sourceRate = currencyRates[sourceCurrencyValue] ?: return
        val targetRate = currencyRates[targetCurrencyValue] ?: return

        val targetValue = (sourceValue * (targetRate / sourceRate))
        targetAmount.setText(String.format("%.2f", targetValue))
    }
}