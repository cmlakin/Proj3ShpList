package edu.umsl.corrina_lakin.proj3shplist.modules.shpitem

import android.content.DialogInterface
import android.os.Bundle
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import edu.umsl.corrina_lakin.proj3shplist.R
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository
import kotlinx.android.synthetic.main.activity_item.*
import java.lang.Exception
import java.util.*

class ItemActivity  : AppCompatActivity() {

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
            createNewShpItem()
        }

        getShpListInfo()
    }

    private fun createNewShpItem() {
        val dialog = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.dialog_child_dashboard, null)
        val shpItemName = view.findViewById<EditText>(R.id.ev_shpItemName)
        val shpItemQuantity = view.findViewById<EditText>(R.id.ev_shpItemQuantity)
        val shpItemPrice = view.findViewById<EditText>(R.id.ev_shpItemPrice)
        dialog.setView(view)
        dialog.setPositiveButton("Add") { _ : DialogInterface, _ : Int ->
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
                addShpListItem(text, quantity, price)
            }

        }
        dialog.setNegativeButton("Cancel") { _ : DialogInterface, _ : Int ->
            // TODO shplist add removing item here
        }
        dialog.show()
    }
    // TODO change shpListId to shpItemid
    private fun addShpListItem(name: String, quantity: Long, price: Double) {
    //private fun addShpListItem(name: String, quantity: Long, price: Double) {
        val now = Date()
        val shpList = ShpItem(
            shpListId = shpList.id,
            itemName = name,
            itemQuantity = quantity,
            itemPrice = price,
            isCompleted = false,
            createdAt = now.time
        )
        repository.addShpItem(shpList) {
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