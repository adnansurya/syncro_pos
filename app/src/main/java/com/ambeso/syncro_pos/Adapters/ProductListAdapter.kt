package com.ambeso.syncro_pos.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ambeso.syncro_pos.R
import com.ambeso.syncro_pos.Utility.StringUtil

import kotlinx.android.synthetic.main.product_list_single.view.*

class ProductListAdapter(private val context: Activity, private val id: Array<Int>, private val nama: Array<String>, private val modal: Array<String>
                    , private val jual: Array<String>, private val stok: Array<String>, private val satuan: Array<String>)
    : ArrayAdapter<String>(context, R.layout.product_list_single, nama) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.product_list_single, null, true)


        rowView.namaTxt.text = "${nama[position]}"
        rowView.modalTxt.text = "Modal : Rp. ${StringUtil().money(modal[position])},-"
        rowView.jualTxt.text = "Jual : Rp. ${StringUtil().money(jual[position])},-"
        rowView.stokTxt.text = "Stok : ${stok[position]}"
        rowView.satuanTxt.text = "${satuan[position]}"


        return rowView
    }


}