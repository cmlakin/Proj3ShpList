package edu.umsl.corrina_lakin.proj3shplist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.umsl.corrina_lakin.proj3shplist.modules.dashboard.DashboardActivity
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize database
        DataRepository.createDatabse(this)
        startActivity(Intent(this, DashboardActivity::class.java))
    }
}
