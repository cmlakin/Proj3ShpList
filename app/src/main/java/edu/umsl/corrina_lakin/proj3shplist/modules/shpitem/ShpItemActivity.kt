package edu.umsl.corrina_lakin.proj3shplist.modules.shpitem

import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.umsl.corrina_lakin.proj3shplist.R
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository
import kotlinx.android.synthetic.main.activity_item.*
import java.util.*

class ShpItemActivity  : AppCompatActivity() {

    private val repository= DataRepository
    private lateinit var adapter: ShpItemAdapter
    lateinit var shpList: ShpList

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)

        shpList = intent.getParcelableExtra(KEY_SHP_LIST)


        setSupportActionBar(item_toolbar)
        // toolbar button as back arrow
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.title = shpList.name

        adapter = ShpItemAdapter()
        rv_item.adapter = adapter

        fab_item.setOnClickListener{
            createNewShpItem(null)
        }

        getShpListInfo()
    }

     fun createNewShpItem(item: ShpItem?) {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_child_dashboard, null)
        val shpItemName = view.findViewById<EditText>(R.id.ev_shpItemName)
        val shpItemQuantity = view.findViewById<EditText>(R.id.ev_shpItemQuantity)
        val shpItemPrice = view.findViewById<EditText>(R.id.ev_shpItemPrice)

        if (item != null) {
            if (item.itemName.isNotEmpty()) {
                shpItemName.setText(item.itemName)
            }
            if (item.itemQuantity.toString().isNotEmpty()) {
                shpItemQuantity.setText(item.itemQuantity.toString())
            }
            if (item.itemPrice.toString().isNotEmpty()) {
                shpItemPrice.setText(item.itemPrice.toString())
            }
        }

        dialog.setView(view)
        dialog.setPositiveButton("Save") { _ : DialogInterface, _ : Int ->
            val text = shpItemName.text.toString()
            //val price = shpItemPrice.text.toString().toDouble()
            var quantity: Long
            var price: Double
            //var quantity = shpItemQuantity.text.toString().toLong()
            if (shpItemQuantity.text.isNotEmpty()){
                quantity = shpItemQuantity.text.toString().toLong()
            } else {
                quantity = 0
            }

            if (shpItemPrice.text.isNotEmpty()){
                price = shpItemPrice.text.toString().toDouble()
            } else {
                price = 0.00
            }

            if (text.isNotEmpty()){
                if (item != null){
                    item.itemName = shpItemName.text.toString()
                    item.itemQuantity = shpItemQuantity.text.toString().toLong()
                    item.itemPrice = shpItemPrice.text.toString().toDouble()
                    repository.updateShpItem(item) {
                        adapter.notifyDataSetChanged()
                    }
                } else {
                    addShpListItem(text, quantity, price)
                }
            }

        }
        dialog.setNegativeButton("Cancel") { _ : DialogInterface, _ : Int ->

        }
        dialog.show()
    }

    private fun addShpListItem(name: String, quantity: Long, price: Double) {
    //private fun addShpListItem(name: String, quantity: Long, price: Double) {
        val now = Date()
        val shpItem = ShpItem(
            shpListId = shpList.id,
            itemName = name,
            itemQuantity = quantity,
            itemPrice = price,
            isCompleted = false,
            createdAt = now.time
        )
        repository.addShpItem(shpItem) {
            adapter.addItem(it)
        }
    }

    private fun getShpListInfo() {
        repository.getShpListsById(shpList.id) {
            adapter.submitList(it.items)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return if (item?.itemId == android.R.id.home){
            finish()
            true
        } else
            super.onOptionsItemSelected(item)
    }

    companion object {
        const val KEY_SHP_LIST = "shp_list_key"
    }
}