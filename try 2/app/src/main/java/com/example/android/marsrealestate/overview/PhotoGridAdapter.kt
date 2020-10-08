/*
 * Copyright 2019, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *
 */

package com.example.android.marsrealestate.overview


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.marsrealestate.databinding.GridViewItemBinding
import com.example.android.marsrealestate.network.ImgAttr
//import com.example.android.marsrealestate.network.ImgAttr

class PhotoGridAdapter ( private val onClickListener: OnClickListener ): ListAdapter<ImgAttr,
        PhotoGridAdapter.imgattrViewHolder>(DiffCallback){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoGridAdapter.imgattrViewHolder {
        return imgattrViewHolder(GridViewItemBinding.inflate(
                LayoutInflater.from(parent.context)
        ))
    }

    override fun onBindViewHolder(holder: PhotoGridAdapter.imgattrViewHolder, position: Int) {
        val imgAttr = getItem(position)
        holder.itemView.setOnClickListener {
            onClickListener.onClick(imgAttr)
        }
        holder.bind(imgAttr)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<ImgAttr>(){
        override fun areItemsTheSame(oldItem: ImgAttr, newItem: ImgAttr): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: ImgAttr, newItem: ImgAttr): Boolean {
            return oldItem.id == newItem.id
        }
    }
    class imgattrViewHolder (private var binding: GridViewItemBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(imgattr: ImgAttr){
            binding.property = imgattr
            binding.executePendingBindings()
        }

    }

    class OnClickListener(val clickListener: (imgAttr:ImgAttr) -> Unit) {
        fun onClick(imgAttr:ImgAttr) = clickListener(imgAttr)
    }

}


