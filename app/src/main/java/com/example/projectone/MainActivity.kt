package com.example.projectone

import android.Manifest
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var bottomNavigationView: BottomNavigationView
    lateinit var drawerLayout: DrawerLayout
    lateinit var location : TextView

    var locationManager: LocationManager? = null
    var latitude: String? = null
    var longitude: String? = null
    private lateinit var profileName : TextView

    private lateinit var auth: FirebaseAuth

    private lateinit var logoutBtn: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        auth = FirebaseAuth.getInstance()

        if(auth.currentUser == null){
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        setContentView(R.layout.activity_main)

        val navigationView : NavigationView = findViewById(R.id.nav_view)

        val headerLayout: View = navigationView.inflateHeaderView(R.layout.header_layout)
        profileName = headerLayout.findViewById(R.id.profileName)

        if(auth.currentUser != null){
            profileName.setText(auth.currentUser.email)
        }else{
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }

        logoutBtn = findViewById(R.id.logout)

        logoutBtn.setOnClickListener{
            FirebaseAuth.getInstance().signOut()
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        }


        supportFragmentManager.beginTransaction().apply {
            add(R.id.frameLayout, FeedFragment())
            addToBackStack("feed")
            commit()
        }

        bottomNavigationView = findViewById(R.id.bottonNavigation)
        drawerLayout = findViewById(R.id.openDrawer)
        val toolbar : androidx.appcompat.widget.Toolbar = findViewById(R.id.toolbar)

        bottomNavigationView.setOnNavigationItemSelectedListener {
            when(it.itemId){
                R.id.feed -> changeFragment(FeedFragment(), "feed")
                R.id.adoption -> changeFragment(AdoptionFragment(), "adoption")
            }
            true
        }

        setSupportActionBar(toolbar)

        val toggle = ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer)

        toggle.drawerArrowDrawable.color = resources.getColor(R.color.black);
        toggle.drawerArrowDrawable.barThickness = 8F
        toggle.drawerArrowDrawable.barLength = 60F

        drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        navigationView.setNavigationItemSelectedListener(this)

    location = findViewById(R.id.location)

    location.setOnClickListener(){
        locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager!!.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            OnGPS()
        } else {
            getYourLocation()
        }
    }
}

    private fun getYourLocation() {
        if (ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
        }
        else {
            val locationGPS : Location? = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)

                val lat: Double? = locationGPS?.latitude
                val longi: Double? = locationGPS?.longitude
                latitude = lat.toString()
                longitude = longi.toString()

                val uriBuilder = Uri.Builder()
                    .scheme("geo")
                    .path("$lat,$longi")
                    .appendQueryParameter("q", "veterinarian near me")

                val intent = Intent(Intent.ACTION_VIEW,uriBuilder.build())
                startActivity(intent)

        }
    }

    private fun OnGPS() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.setMessage("Enable GPS").setCancelable(false)
            .setPositiveButton("Yes",
                DialogInterface.OnClickListener { dialog, which -> startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)) })
            .setNegativeButton("No",
                DialogInterface.OnClickListener { dialog, which -> dialog.cancel() })
        val alertDialog: AlertDialog = builder.create()
        alertDialog.show()
    }


    private fun changeFragment(fragment: Fragment, s : String){
        supportFragmentManager.beginTransaction().apply {
            replace(R.id.frameLayout, fragment)
            addToBackStack(s)
            commit()
        }
    }

    override fun onBackPressed() {
        val drawerLayout: DrawerLayout = findViewById(R.id.openDrawer)

        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }
        else{
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        val drawerLayout: DrawerLayout = findViewById(R.id.openDrawer)

        return when(item.itemId){
            R.id.yourPet -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, YourPetActivity::class.java)
                startActivity(intent)
                true
            }
            R.id.likedFeed -> {
                drawerLayout.closeDrawer(GravityCompat.START)
                val intent = Intent(this, likedActivity::class.java)
                startActivity(intent)
                true
            }
            else -> false
        }
    }
}