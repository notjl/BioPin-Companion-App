package com.example.pd.main

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pd.database.models.Signal
import com.example.pd.databinding.SignalItemBinding

class SignalAdapter(
    private var onUpdateClicked: (Signal) -> Unit,
    private var onDeleteClicked: (Signal) -> Unit
): ListAdapter<Signal, SignalAdapter.SignalViewHolder>(DiffCallback) {

    class SignalViewHolder(
        private var binding: SignalItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val signal = binding.signal
        val type = binding.signalType
        val updateButton = binding.updateButton
        val deleteButton = binding.deleteButton

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SignalViewHolder {
        return SignalViewHolder(
            SignalItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val item = getItem(position)
        holder.signal.text = item.signal.toString() + " Hz"
        holder.type.text = item.type.capitalize()
        holder.updateButton.setOnClickListener {
            onUpdateClicked(item)
        }
        holder.deleteButton.setOnClickListener {
            onDeleteClicked(item)
        }
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