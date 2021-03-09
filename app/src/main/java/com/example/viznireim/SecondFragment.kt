package com.example.viznireim

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.Button
import androidx.appcompat.widget.AppCompatSpinner
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */
class SecondFragment : Fragment() {

    private lateinit var database: DatabaseReference
    // ...
    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        database = Firebase.database.reference
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onStart() {
        super.onStart()

        database.child("country_list").get().addOnSuccessListener {
            Log.i("firebase", "Got value ${it.value}")
        }.addOnFailureListener{
            Log.e("firebase", "Error getting data", it)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)




                }



        val spinner04 = view.findViewById<AppCompatSpinner>(R.id.spinner04)
        val modelList: List<Model> = readFromAsset()
        val customDropDownAdapter = CustomDropDownAdapter(requireContext(), modelList)
        spinner04.adapter = customDropDownAdapter

        }

    private fun readFromAsset(): List<Model> {
        val file_name = "android_version.json"

        val bufferReader = requireActivity().application.assets.open(file_name).bufferedReader()

        val json_string = bufferReader.use {
            it.readText()
        }
        val gson = Gson()
        val modelList: List<Model> = gson.fromJson(json_string, Array<Model>::class.java).toList()
        return modelList
    }
    }
