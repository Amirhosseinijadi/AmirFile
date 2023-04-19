package com.example.amirfile

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.amirfile.databinding.ActivityMainBinding
import com.example.amirfile.databinding.FragmentFileBinding
import com.example.amirfile.databinding.ItemFileBinding
import java.io.File

class FileFragmet(val path:String):Fragment(),FileAdapter.FileEvents {
    lateinit var binding: FragmentFileBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
       binding = FragmentFileBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ourfile = File(path)

        binding.txtPath.text = ourfile.name + ">"

        if (ourfile.isDirectory){

            val listoffiles = arrayListOf<File>()
            listoffiles.addAll(ourfile.listFiles()!!)

            if(listoffiles.size > 0){

                binding.recyclerMain.visibility = View.VISIBLE
                binding.imgNoData.visibility = View.GONE


                binding.recyclerMain.adapter = FileAdapter(listoffiles,this)
                binding.recyclerMain.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)


            }else{
                binding.recyclerMain.visibility = View.GONE
                binding.imgNoData.visibility = View.VISIBLE
            }





        }




    }

    override fun onFileclicked(file: File, type: String) {

    }

    override fun onFolderclicked(path: String) {

        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.frame_main_container,FileFragmet(path))
        transaction.addToBackStack(null)
        transaction.commit()

    }
}