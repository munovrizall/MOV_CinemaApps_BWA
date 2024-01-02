package com.artonov.mov.activity_home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.fragment.app.Fragment
import com.artonov.mov.R
import com.artonov.mov.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragmentHome = DashboardFragment()
        val fragmentTicket = TicketFragment()
        val fragmentProfile = ProfileFragment()
        setFragment(fragmentHome)

        binding.ivHome.setOnClickListener() {
            setFragment(fragmentHome)

            changeNavbar(binding.ivHome, R.drawable.ic_home_active)
            changeNavbar(binding.ivTicket, R.drawable.ic_tiket)
            changeNavbar(binding.ivProfile, R.drawable.ic_profile)
        }

        binding.ivTicket.setOnClickListener() {
            setFragment(fragmentTicket)

            changeNavbar(binding.ivHome, R.drawable.ic_home)
            changeNavbar(binding.ivTicket, R.drawable.ic_tiket_active)
            changeNavbar(binding.ivProfile, R.drawable.ic_profile)
        }

        binding.ivProfile.setOnClickListener() {
            setFragment(fragmentProfile)

            changeNavbar(binding.ivHome, R.drawable.ic_home)
            changeNavbar(binding.ivTicket, R.drawable.ic_tiket)
            changeNavbar(binding.ivProfile, R.drawable.ic_profile_active)
        }

    }

    private fun setFragment(fragment: Fragment) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.layoutFragment, fragment)
        fragmentTransaction.commit()
    }

    private fun changeNavbar(imageView: ImageView, int: Int) {
        imageView.setImageResource(int)
    }
}