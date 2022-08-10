package com.example.tbc_course_19.adapter

import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.tbc_course_19.databinding.RecyclerLayoutBinding
import com.example.tbc_course_19.models.ModelClass

class CustomAdapter:ListAdapter<ModelClass,CustomAdapter.ViewHolder>(DiffCallBack()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
        ViewHolder(
            RecyclerLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        )

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ViewHolder(private val binding:RecyclerLayoutBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(option: ModelClass){

            binding.apply {
                Glide.with(this.root).load(option.icon).into(imageView)
                editText.hint = option.hint
                reqText.apply {
                    visibility = if (option.required == true){
                        View.VISIBLE
                    }else{
                        View.GONE
                    }
                }

            }

            if (option.keyboard == null){
                binding.editText.inputType = InputType.TYPE_CLASS_TEXT
            }else{
                binding.apply {
                    editText.apply {
                        inputType = if (option.keyboard == "text"){
                            InputType.TYPE_CLASS_TEXT
                        }else{
                            InputType.TYPE_CLASS_NUMBER
                        }
                    }


                }
            }

        }


    }

    class DiffCallBack:DiffUtil.ItemCallback<ModelClass>() {
        override fun areItemsTheSame(
            oldItem: ModelClass,
            newItem: ModelClass
        ): Boolean {
            return oldItem.fieldId == newItem.fieldId
        }

        override fun areContentsTheSame(
            oldItem: ModelClass,
            newItem: ModelClass
        ): Boolean {
            return oldItem == newItem
        }
    }

}


