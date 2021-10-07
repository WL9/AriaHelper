package com.k.ariahelper

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.k.ariahelper.controllers.cards.CardsFragment
import com.k.ariahelper.controllers.character.CharacterFragment
import com.k.ariahelper.controllers.dices.DicesFragment
import com.k.ariahelper.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var activityMain: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMain = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMain.root)

        loadFragment(DicesFragment())
        setupBottomNavigationBar()
    }

    private fun setupBottomNavigationBar() {
        activityMain.bottomNavigation.setOnItemSelectedListener { item ->
            when(item.itemId) {
                R.id.dices -> {
                    loadFragment(DicesFragment())
                    true
                }
                R.id.cards -> {
                    loadFragment(CardsFragment())
                    true
                }
                R.id.character -> {
                    loadFragment(CharacterFragment())
                    true
                }
                else -> false
            }
        }
    }

    private fun loadFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.frame, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
}