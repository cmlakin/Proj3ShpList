package edu.umsl.corrina_lakin.proj3shplist

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import edu.umsl.corrina_lakin.proj3shplist.modules.shpList.ShpListActivity
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository
import java.util.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // initialize database
        DataRepository.createDatabse(this)
        val intent = Intent(this, ShpListActivity::class.java)
        val timer = Timer()
        val task = object : TimerTask(){
            override fun run() {
                startActivity(intent)
                finish()
            }
        }
        timer.schedule(task, 2000)

    }

}


