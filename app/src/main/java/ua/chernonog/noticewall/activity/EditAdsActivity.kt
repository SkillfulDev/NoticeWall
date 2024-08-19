package ua.chernonog.noticewall.activity

import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.noticewall.databinding.ActivityEditAdsBinding
import ua.chernonog.noticewall.utils.JsonFileParser

class EditAdsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            JsonFileParser.getAllCountries(this)
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spCountries.adapter = adapter
    }
}
