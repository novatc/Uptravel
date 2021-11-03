package com.novatc.uptravel.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.novatc.uptravel.R
import com.novatc.uptravel.model.PlacesModel
import kotlinx.android.synthetic.main.item_place.view.*

class PlacesAdapter(private val context: Context, private var list: ArrayList<PlacesModel>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var onClickListener: OnClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return MyViewHolder(
            LayoutInflater.from(context).inflate(
                R.layout.item_place,
                parent,
                false
            )
        )
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val model = list[position]

        if (holder is MyViewHolder) {
            holder.itemView.iv_place_image.setImageURI(Uri.parse(model.image))
            holder.itemView.tvName.text = model.title
            holder.itemView.tvDescription.text = model.description
            holder.itemView.setOnClickListener {
                if (onClickListener != null) {
                    onClickListener!!.onClick(position, model)
                }
            }

        }
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun setOnClickListener(onClickListenser: OnClickListener) {
        this.onClickListener = onClickListenser
    }

    interface OnClickListener {
        fun onClick(position: Int, model: PlacesModel)
    }

    private class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)
}
