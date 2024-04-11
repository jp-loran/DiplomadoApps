import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.jploran.persistenciasp.R
import com.jploran.persistenciasp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding
    private lateinit var navHostFragment: Fragment

    private lateinit var encrypted_sp: SharedPreferences
    private lateinit var encrypted_spEditor: SharedPreferences.Editor

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)

        navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_main) as Fragment

        val masterKey = MasterKey.Builder(this, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM).build()

        encrypted_sp = EncryptedSharedPreferences.create(
            this,
            "encrypted_sp",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        encrypted_spEditor = encrypted_sp.edit()

        val user = encrypted_sp.getString("user_sp", null)
        user?.let {
            Toast.makeText(this, "El usuario almacenado es $user", Toast.LENGTH_SHORT).show()
        }

        val color = encrypted_sp.getInt("color", Color.WHITE)
        changeColor(color)

        val navController = findNavController(R.id.nav_host_fragment_content_main)
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        binding.fab.setOnClickListener { view ->
            // Example action
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val color = when (item.itemId) {
            R.id.action_rojo -> ContextCompat.getColor(this, R.color.mi_rojo)
            R.id.action_verde -> ContextCompat.getColor(this, R.color.mi_verde)
            R.id.action_azul -> ContextCompat.getColor(this, R.color.mi_azul)
            else -> return super.onOptionsItemSelected(item)
        }
        changeColor(color)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun changeColor(color: Int) {
        navHostFragment.view?.setBackgroundColor(color)
        saveColor(color)
    }

    private fun saveColor(color: Int) {
        encrypted_spEditor.putInt("color", color).apply()
        // You might want to save other preferences here
    }
}
