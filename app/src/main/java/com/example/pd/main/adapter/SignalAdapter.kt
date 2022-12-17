package com.example.pd.main.adapter

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.pd.R
import com.example.pd.database.models.Signal
import com.example.pd.databinding.SignalItemBinding
import java.util.*

class SignalAdapter(
    private var onUpdateClicked: (Signal) -> Unit,
    private var onDeleteClicked: (Signal) -> Unit
): ListAdapter<Signal, SignalAdapter.SignalViewHolder>(DiffCallback) {

    class SignalViewHolder(
        binding: SignalItemBinding
    ): RecyclerView.ViewHolder(binding.root) {
        val signal = binding.signal
        val type = binding.signalType
        val direction = binding.signalDirection
        val updateButton = binding.updateButton
        val deleteButton = binding.deleteButton
        val cardView = binding.cardView

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

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: SignalViewHolder, position: Int) {
        val item = getItem(position)
        holder.signal.text = item.signal.toString() + " Hz"
        holder.cardView.setCardBackgroundColor(Color.parseColor("#82E7E8"))
//         holder.type.text = item.type.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
//        holder.direction.text = "(${
//            item.direction.replaceFirstChar {
//                if (it.isLowerCase()) it.titlecase(
//                    Locale.getDefault()
//                ) else it.toString()
//            }
//        } Direction)"

        when (item.type) {
            "brain" -> {

                holder.type.setImageResource(R.drawable.eegxicon)
            }
            "muscle" -> {
                //holder.cardView.setCardBackgroundColor(Color.parseColor("#82E7E8"))
                holder.type.setImageResource(R.drawable.emgxicon)
            }
            "eyes" -> {
                //holder.cardView.setCardBackgroundColor(Color.parseColor("#82E7E8"))
                holder.type.setImageResource(R.drawable.eogxicon)
            }
        }

        when (item.direction) {
            "right" -> {
                holder.direction.setImageResource(R.drawable.rightx16)
            }
            "left" -> {
                holder.direction.setImageResource(R.drawable.leftx16)
            }
            "backward" -> {
                holder.direction.setImageResource(R.drawable.downx16)
            }
            "forward" -> {
                holder.direction.setImageResource(R.drawable.upx16)
            }
        }

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