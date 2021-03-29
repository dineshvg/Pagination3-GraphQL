package de.mobile.dinesh.gorillas

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.findNavController
import de.mobile.dinesh.gorillas.databinding.MainActivityBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: MainActivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = MainActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        delegate.localNightMode = AppCompatDelegate.MODE_NIGHT_NO
        supportActionBar?.hide()

        window.navigationBarColor = ResourcesCompat.getColor(
            resources, R.color.purple_500,
            applicationContext.theme
        )

        window.statusBarColor = ResourcesCompat.getColor(
            resources, R.color.purple_500,
            applicationContext.theme
        )

        findNavController(R.id.navHostFragment).apply {
            graph = navInflater.inflate(R.navigation.nav_graph)
        }
    }

    override fun onSupportNavigateUp() = findNavController(R.id.navHostFragment).navigateUp()
}