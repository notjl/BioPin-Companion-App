package com.example.pd.pd.crud.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pd.database.models.Signal
import com.example.pd.databinding.SignalItemBinding

class SignalAdapter: ListAdapter<Signal, SignalAdapter.SignalViewHolder>(DiffCallback) {
    class SignalViewHolder(
        private var binding: SignalItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val signal = binding.signal
        val type = binding.signalType
        val updateButton = binding.updateButton
        val deleteButton = binding.deleteButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        val viewHolder = SignalViewHolder(
            SignalItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )

        viewHolder.updateButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            Toast.makeText(parent.context, "Update ${getItem(position).signal.toString()}", Toast.LENGTH_LONG).show()
        }
        viewHolder.deleteButton.setOnClickListener {
            val position = viewHolder.adapterPosition
            Toast.makeText(parent.context, "Delete ${getItem(position).signal.toString()}", Toast.LENGTH_LONG).show()
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val item = getItem(position)
        holder.signal.text = item.signal.toString() + " Hz"
        holder.type.text = item.type.capitalize().toString()
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<Signal>() {
            override fun areItemsTheSame(oldItem: Signal, newItem: Signal): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Signal, newItem: Signal): Boolean {
                return oldItem == newItem
            }
        }
    }

}