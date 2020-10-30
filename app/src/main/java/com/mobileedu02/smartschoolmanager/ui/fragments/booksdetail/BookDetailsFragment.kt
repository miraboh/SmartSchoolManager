package com.mobileedu02.smartschoolmanager.ui.fragments.booksdetail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import com.mobileedu02.smartschoolmanager.R
import com.mobileedu02.smartschoolmanager.databinding.FragmentBookDetailsBinding
import com.mobileedu02.smartschoolmanager.model.Book
import com.mobileedu02.smartschoolmanager.model.Chapter
import kotlinx.io.InputStream
import kotlinx.serialization.InternalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import java.io.IOException

class BookDetailsFragment : Fragment() {

    private lateinit var binding: FragmentBookDetailsBinding

    private lateinit var option: Spinner
    private lateinit var result: TextView
    private var book: Book? = null

    private lateinit var url: String

    @InternalSerializationApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentBookDetailsBinding.inflate(inflater)

        option = binding.spOption
        result = binding.tvResult
        book = BookDetailsFragmentArgs.fromBundle(requireArguments()).bookArgs
        binding.txtBookTitle.text = book!!.title

        option.adapter = ArrayAdapter(requireContext(), R.layout.support_simple_spinner_dropdown_item, book!!.pages.map { it.chapIndex })
        option.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                result.text = "Select an Option"

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                url = parent!!.getItemAtPosition(position) as String
                display(generateBooksByChap())
            }
        }
        binding.videoID.setOnClickListener {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_LONG).show()
        }
        binding.audioID.setOnClickListener {
            Toast.makeText(context, "Coming Soon", Toast.LENGTH_LONG).show()
        }

        return binding.root
    }

    @InternalSerializationApi
    private fun generateBooksByChap(): Chapter? {
        val chapter: Chapter
        try {
            val inputStream: InputStream = requireContext().assets.open(url + ".json")
            val jsonFile = inputStream.bufferedReader().use { it.readText() }
            val json = Json(JsonConfiguration.Stable)
            chapter = json.parse(Chapter.serializer(), jsonFile)
        }catch (e: IOException){
            e.printStackTrace()
            return null
        }
        return chapter
    }
    private fun display(chapter: Chapter?) {
        binding.txtBody.text = chapter!!.text
        binding.txtChapTitle.text = chapter.chapIndex
    }

}