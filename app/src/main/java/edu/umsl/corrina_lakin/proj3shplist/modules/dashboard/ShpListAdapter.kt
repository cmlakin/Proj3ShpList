package edu.umsl.corrina_lakin.proj3shplist.modules.dashboard

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import edu.umsl.corrina_lakin.proj3shplist.R
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpList
import edu.umsl.corrina_lakin.proj3shplist.modules.shpitem.ItemActivity
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository


class ShpListAdapter() : RecyclerView.Adapter<ShpListAdapter.ViewHolder>() {
    val list: MutableList<ShpList> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.rv_child_dashboard,
            parent,
            false
        )
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    // I don't know how to do this without context.
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bindShpList(item)
    }

    fun submitList(newList: List<ShpList>) {
        // update existing list
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(ShpList: ShpList) {
        list.add(ShpList)
        notifyItemInserted(list.size - 1)
    }

    class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener,
        PopupMenu.OnMenuItemClickListener {
        val context: Context = v.context
        val shpListName: TextView = v.findViewById(R.id.tv_shpList_name)
        val moreButton: ImageView = v.findViewById(R.id.btn_more)
        private lateinit var shpList: ShpList
        private val repository = DataRepository

        fun bindShpList(item: ShpList) {
            shpList = item
            shpListName.text = item.name
            shpListName.setOnClickListener(this)
            moreButton.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            // check which view was clicked
            when (view) {
                shpListName -> navigateToItemList()
                moreButton -> createPopupMenu()
            }
        }
        // TODO need to set up an update and delete of original list
        override fun onMenuItemClick(item: MenuItem): Boolean {
            // check which menu item was clicked
            return when (item.itemId) {
//                R.id.deleteShpList -> {
//                    repository.deleteShpList(shpList) {
//                        val position = list.indexOf(shpList)
//                        list.removeAt(position)
//                        notifyListRemoved(position)
//                        toast("Deleted ${shpList.shpListName}")
//                    }
//                    true
//                }

                R.id.updateShpList -> {
                    toast("Clicked Update")
                    true
                }

                else -> false
            }
        }

        private fun createPopupMenu() {
            // create popup menu
            val popupMenu = PopupMenu(context, moreButton)
            // inflate the menu for ShpList
            popupMenu.menuInflater.inflate(R.menu.menu_shp_list, popupMenu.menu)
            // attach menu-item click callback
            popupMenu.setOnMenuItemClickListener(this)
            // show the popup menu
            popupMenu.show()
        }


        private fun navigateToItemList() {
            val intent = Intent(context, ItemActivity::class.java)
            intent.putExtra(ItemActivity.KEY_SHP_LIST, shpList)
            context.startActivity(intent)
        }

        private fun toast(message: String, duration: Int = Toast.LENGTH_LONG) {
            Toast.makeText(context, message, duration).show()
        }
    }
}