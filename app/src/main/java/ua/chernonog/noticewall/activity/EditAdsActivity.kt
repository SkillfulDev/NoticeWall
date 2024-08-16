package ua.chernonog.noticewall.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ua.chernonog.noticewall.databinding.ActivityEditAdsBinding

class EditAdsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityEditAdsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditAdsBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}