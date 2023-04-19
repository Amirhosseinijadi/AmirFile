package com.example.amirfile

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.amirfile.databinding.ItemFileBinding
import java.io.File
import java.net.URLConnection

class FileAdapter(val data:ArrayList<File>,val fileEvents: FileEvents):RecyclerView.Adapter<FileAdapter.FileViewholder>() {

    lateinit var binding:ItemFileBinding


    inner class FileViewholder(itemview: View):RecyclerView.ViewHolder(itemview){

        fun bindviews(file:File){

            var filetype = ""

            binding.textView.text = file.name

            if(file.isDirectory){

                binding.imageView.setImageResource(R.drawable.ic_folder)

            }else{


                when{
                    isImage(file.path) -> {
                        binding.imageView.setImageResource(R.drawable.ic_image)
                    }

                    isVideo(file.path) -> {

                        binding.imageView.setImageResource(R.drawable.ic_video)

                    }

                    isZip(file.path) -> {

                        binding.imageView.setImageResource(R.drawable.ic_zip)

                    }
                    else -> {
                        binding.imageView.setImageResource(R.drawable.ic_file)
                    }


                }





            }

            itemView.setOnClickListener {
                if (file.isDirectory){
                    fileEvents.onFolderclicked(file.path)




                }else{

                    fileEvents.onFileclicked(file,filetype)

                }
            }





        }




    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FileViewholder {
        val layoutinflater = LayoutInflater.from(parent.context)
        binding = ItemFileBinding.inflate(layoutinflater,parent,false)

        return FileViewholder(binding.root)


    }

    override fun getItemCount(): Int {

        return data.size

    }

    override fun onBindViewHolder(holder: FileViewholder, position: Int) {
        holder.bindviews(data[position])

    }

    private fun isImage(path: String): Boolean {
        val mimeType: String = URLConnection.guessContentTypeFromName(path)
        return mimeType.startsWith("image")
    }
    private fun isVideo(path: String): Boolean {
        val mimeType = URLConnection.guessContentTypeFromName(path)
        return mimeType.startsWith("video")
    }
    private fun isZip(name: String): Boolean {
        return name.contains(".zip") || name.contains(".rar")
    }

    interface FileEvents {
        fun onFileclicked(file: File , type:String)
        fun onFolderclicked(path: String)
    }


    }

