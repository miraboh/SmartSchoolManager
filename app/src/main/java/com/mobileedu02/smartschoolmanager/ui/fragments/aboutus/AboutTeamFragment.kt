package com.mobileedu02.smartschoolmanager.ui.fragments.aboutus

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.ui.activity.AboutUs
import com.mobileedu02.smartschoolmanager.util.MyListAdapter
import kotlinx.android.synthetic.main.fragment_about_team.*

class AboutTeamFragment : Fragment() {

    val name = arrayOf(
        "Leo Assam",
        "Ikechi Myraboh",
        "Peculiar C Umeh",
        "Augustine Nliam",
        "Ifeoma Onwurah",
        "Lawrence Ebo"
    )
    val address = arrayOf(
        "Buea, Cameroon",
        "Enugu, Nigeria",
        "Port Harcourt, Nigeria",
        "Imo, Nigeria",
        "Uyo, Nigeria",
        "Lagos, Nigeria"
    )
    val email = arrayOf(
        "leotrally@gmail.com",
        "myrabohuche@gmail.com",
        "peculiarumeh02@gmail.com",
        "nliaustemac@gmail.com",
        "onifeomavivian@gmail.com",
        "saintlawrencep4u@yahoo.com"
    )
    val role = arrayOf(
        "Mentor",
        "Team Lead / Android Developer",
        "Android Developer / Mentor",
        "Cloud Engineer",
        "Cloud Engineer",
        "Android Developer"
    )
    val imageId = arrayOf(
        R.drawable.team_leo,
        R.drawable.team_myraboh,
        R.drawable.team_peculiar,
        R.drawable.team_augustine,
        R.drawable.team_ifeoma,
        R.drawable.team_lawrence
    )

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_about_team, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val context = context as AboutUs

        val myListAdapter = MyListAdapter(context, name, address, email, role, imageId)
        listView.adapter = myListAdapter
    }
}