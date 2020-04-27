package edu.umsl.corrina_lakin.proj3shplist.modules.shpList

import android.content.DialogInterface
import android.os.Bundle
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
//import edu.umsl.corrina_lakin.ShpList.R
//import edu.umsl.corrina_lakin.ShpList.data.models.ShpList
//import edu.umsl.corrina_lakin.ShpList.utils.DataRepository
import edu.umsl.corrina_lakin.proj3shplist.R
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository
import kotlinx.android.synthetic.main.activity_dashboard.*

class ShpListActivity : AppCompatActivity() {

    private val repository= DataRepository
    private lateinit var adapter: ShpListAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(dashboard_toolbar)
        title = "Dashboard"

        adapter = ShpListAdapter()
        rv_dashboard.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rv_dashboard.adapter = adapter

        fab_dashboard.setOnClickListener {
            createNewShpList(null)
        }

        getShpList()
    }

    fun createNewShpList(sList: ShpList?) {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_dashboard, null)
        val shpListName = view.findViewById<EditText>(R.id.ev_shpList)

        if (sList != null) {
            if (sList.name.isNotEmpty()){
                shpListName.setText(sList.name)
            }
        }
        dialog.setView(view)
        dialog.setPositiveButton("Save") { _ : DialogInterface, _ : Int ->
            val text = shpListName.text.toString()
            if (text.isNotEmpty()){
                if (sList != null){
                    sList.name = shpListName.text.toString()
                    repository.updateShpList(sList) {
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    addShpList(text)
                }
            }
        }
        dialog.setNegativeButton("Cancel") { _ : DialogInterface, _ : Int ->

        }
        dialog.show()
    }

    private fun addShpList(name: String) {
        val shpList = ShpList(name = name)
        repository.addShpList(shpList) {
            adapter.addItem(it)
        }
    }

    private fun getShpList() {
        repository.getShpLists {
            adapter.submitList(it)
        }
    }

    private fun deleteShpList() {

    }
}
