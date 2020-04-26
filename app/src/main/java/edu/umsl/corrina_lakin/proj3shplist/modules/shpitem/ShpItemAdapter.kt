package edu.umsl.corrina_lakin.proj3shplist.modules.shpitem

import android.content.Context
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import edu.umsl.corrina_lakin.proj3shplist.R
import edu.umsl.corrina_lakin.proj3shplist.data.models.ShpItem
import edu.umsl.corrina_lakin.proj3shplist.utils.DataRepository


class ShpItemAdapter() : RecyclerView.Adapter<ShpItemAdapter.ViewHolder>() {
    val list: MutableList<ShpItem> = mutableListOf()

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

    fun submitList(newList: List<ShpItem>) {
        // update existing list
        list.clear()
        list.addAll(newList)
        notifyDataSetChanged()
    }

    fun addItem(ShpList: ShpItem) {
        list.add(ShpList)
        notifyItemInserted(list.size - 1)
    }

    inner class ViewHolder(v: View) : RecyclerView.ViewHolder(v), View.OnClickListener,
        PopupMenu.OnMenuItemClickListener {
        val context: Context = v.context
        val shpItemName: TextView = v.findViewById(R.id.tv_shpItem_name)
        val moreButton: ImageView = v.findViewById(R.id.btn_more)
        private lateinit var ShpItem: ShpItem
        private val repository = DataRepository

        fun bindShpList(item: ShpItem) {
            ShpItem = item
            shpItemName.text = item.itemName

            val color =
                if (item.isCompleted) R.color.colorAccent
                else  R.color.colorPrimary

            val actualColor = ContextCompat.getColor(context, color)
            shpItemName.setTextColor(actualColor)

            shpItemName.setOnClickListener(this)
            moreButton.setOnClickListener(this)
        }

        override fun onClick(view: View) {
            // check which view was clicked
            when (view) {
                shpItemName -> navigateShpListItemList()
                moreButton -> createPopupMenu()
            }
        }

        override fun onMenuItemClick(item: MenuItem): Boolean {
            // check which menu item was clicked
            return when (item.itemId) {
                R.id.deleteShpItem -> {
                    repository.deleteShpItem(ShpItem) {
                        val position = list.indexOf(ShpItem)
                        list.removeAt(position)
                        notifyItemRemoved(position)
                        toast("Deleted ${ShpItem.itemName}")
                    }
                    true
                }

                R.id.markAsCompleted -> {
                    val updatedItem = ShpItem.copy(isCompleted = true)
                    repository.updateShpItem(updatedItem) {
                        // get the current position
                        val position = list.indexOf(ShpItem)
                        // remove old item
                        list.removeAt(position)

                        // add updated item
                        list.add(position, updatedItem)
                        // set update item
                        ShpItem = updatedItem
                        // notify adapter of change
                        notifyItemChanged(position)

                        toast("Clicked Update")
                    }
                    true
                }

                else -> false
            }
        }

        private fun createPopupMenu() {
            // create popup menu
            val popupMenu = PopupMenu(context, moreButton)
            // inflate the menu for ShpList
            popupMenu.menuInflater.inflate(R.menu.menu_shp_item, popupMenu.menu)
            // attach menu-item click callback
            popupMenu.setOnMenuItemClickListener(this)
            // show the popup menu
            popupMenu.show()
        }


        private fun navigateShpListItemList() {
        }

        private fun toast(message: String, duration: Int = Toast.LENGTH_LONG) {
            Toast.makeText(context, message, duration).show()

        }
    }
}