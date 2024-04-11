package com.jploran.videogamesdb.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jploran.videogamesdb.application.VideoGamesDBApp
import com.jploran.videogamesdb.data.GameRepository
import com.jploran.videogamesdb.data.db.model.GameEntity
import com.jploran.videogamesdb.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var games: List<GameEntity> = emptyList()
    private lateinit var repository: GameRepository
    private lateinit var gameAdapter: GameAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = (application as VideoGamesDBApp).repository
        gameAdapter = GameAdapter(){selectedGame ->
            val dialog = GameDialog(newGame=false, game=selectedGame, { updateUI() }, {action-> message(action)})
            dialog.show(supportFragmentManager, "dialogo2")
        }

        binding.rvGames.apply {
            layoutManager = LinearLayoutManager(this@MainActivity)
            adapter = gameAdapter
        }
        updateUI()
    }

    private fun updateUI(){
        lifecycleScope.launch(){
            games = repository.getAllGames()

            binding.tvSinRegistros.visibility =
                if(games.isEmpty()) View.VISIBLE else View.INVISIBLE

            gameAdapter.updateList(games)
        }
    }

    fun click(view: View) {
        val dialog = GameDialog(updateUI = { updateUI() }, message = {action -> message(action)})
        dialog.show(supportFragmentManager, "dialogo")
    }

    private fun message(text:String){
        Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    }
}
