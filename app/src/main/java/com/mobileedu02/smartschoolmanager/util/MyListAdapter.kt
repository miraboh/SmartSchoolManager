package com.mobileedu02.smartschoolmanager.util

import android.app.Activity
import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import com.mobileedu02.smartschoolmanager.R

class MyListAdapter(private val context: Activity, private val name: Array<String>, private val address: Array<String>, private val email: Array<String>, private val role: Array<String>, private val imageId: Array<Int>)
    : ArrayAdapter<String>(context, R.layout.list_item_team, name) {

    override fun getView(position: Int, view: View?, parent: ViewGroup): View {
        val inflater = context.layoutInflater
        val rowView = inflater.inflate(R.layout.list_item_team, null, true)

        val nameText = rowView.findViewById(R.id.name) as TextView
        val addressText = rowView.findViewById(R.id.address) as TextView
        val emailText = rowView.findViewById(R.id.email) as TextView
        val roleText = rowView.findViewById(R.id.role) as TextView
        val image = rowView.findViewById(R.id.display_profile) as ImageView

        nameText.text = name[position]
        addressText.text = address[position]
        emailText.text = email[position]
        roleText.text = role[position]
        image.setImageResource(imageId[position])

        return rowView
    }
}