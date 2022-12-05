package com.example.pd.pd.crud.adapter

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.pd.database.models.Signal
import com.example.pd.databinding.SignalItemBinding

//class SignalAdapter: RecyclerView.Adapter<SignalAdapter.SignalViewHolder>() {
//    class SignalViewHolder(
//        private var binding: SignalItemBinding
//    ): RecyclerView.ViewHolder(binding.root) {
//        val signal = binding.signal
//        val type = binding.signalType
//        val updateButton = binding.updateButton
//        val deleteButton = binding.deleteButton
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
//        TODO("Not yet implemented")
//    }
//
//    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
//        TODO("lamo")
//    }
//
//    companion object {
//        private val DiffCallback = object : DiffUtil.ItemCallback<Signal>() {
//            override fun areItemsTheSame(oldItem: Signal, newItem: Signal): Boolean {
//                return oldItem.id == newItem.id
//            }
//
//            override fun areContentsTheSame(oldItem: Signal, newItem: Signal): Boolean {
//                return oldItem == newItem
//            }
//        }
//    }
//
//}