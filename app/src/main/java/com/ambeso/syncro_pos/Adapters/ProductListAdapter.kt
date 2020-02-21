package com.ambeso.syncro_pos.Adapters

import android.annotation.SuppressLint
import android.app.Activity
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.ambeso.syncro_pos.R

import kotlinx.android.synthetic.main.product_list_single.view.*

class ProductListAdapter(private val context: Activity, private val id: Array<Int>, private val nama: Array<String>, private val modal: Array<String>
                    , private val jual: Array<String>, private val stok: Array<String>, private val satuan: Array<String>)
    : ArrayAdapter<String>(context, R.layout.product_list_single, nama) {

    @SuppressLint("ViewHolder")
    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.product_list_single, null, true)

//        val namaTxt = rowView.findViewById(R.id.namaTxt) as TextView
//        val modalTxt = rowView.findViewById(R.id.modalTxt) as TextView
//        val jualTxt = rowView.findViewById(R.id.jualTxt) as TextView
//        val stokTxt = rowView.findViewById(R.id.stokTxt) as TextView
//        val satuanTxt = rowView.findViewById(R.id.satuanTxt) as TextView

//        name.text = "Id: ${id[position]}"
//        nameText.text = "Name: ${name[position]}"
//        emailText.text = "Email: ${email[position]}"

        rowView.namaTxt.text = "${nama[position]}"
        rowView.modalTxt.text = "Modal : ${modal[position]}"
        rowView.jualTxt.text = "Jual : ${jual[position]}"
        rowView.stokTxt.text = "Stok: ${stok[position]}"
        rowView.satuanTxt.text = "${satuan[position]}"


        return rowView
    }
}