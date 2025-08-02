package com.example.pictureoftheday

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.pictureoftheday.databinding.ActivityRecyclerBinding

class RecyclerActivity : AppCompatActivity() {

    private var _binding: ActivityRecyclerBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        _binding = ActivityRecyclerBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val data = arrayListOf(
            Pair(Data(1, "Mars", ""), false)//not sure!
        )

        data.add(0, Pair(Data(0, "Header"), false))//not sure!

        val adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

        binding.recyclerView.adapter = adapter
        binding.recyclerActivityFAB.setOnClickListener { adapter.appendItem() }


    }

    class RecyclerActivityAdapter(
        private var onListItemClickListener: OnListItemClickListener,
        private var data: MutableList<Pair<Data, Boolean>>
    ) :
        RecyclerView.Adapter<BaseViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
            val inflater = LayoutInflater.from(parent.context)
            return when (viewType) {
                TYPE_EARTH -> EarthViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_earth, parent, false) as View
                )

                TYPE_MARS ->
                    MarsViewHolder(
                        inflater.inflate(
                            R.layout.activity_recycler_item_mars,
                            parent,
                            false
                        ) as View
                    )

                else -> HeaderViewHolder(
                    inflater.inflate(R.layout.activity_recycler_item_header, parent, false) as View
                )

            }
        }

        override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
            holder.bind(data[position])
        }

        override fun getItemCount(): Int {
            return data.size
        }

        override fun getItemViewType(position: Int): Int {
            /*return if (data[position].someDescription.isNullOrBlank()) TYPE_MARS else TYPE_EARTH*/
            return when {
                position == 0 -> TYPE_HEADER
                data[position].first.someDescription.isNullOrBlank() -> TYPE_MARS
                else -> TYPE_EARTH
            }
        }

        @SuppressLint("NotifyDataSetChanged")
        fun appendItem() {
            data.add(generateItem())
            notifyItemInserted(itemCount - 1)
        }

        private fun generateItem() = Pair(Data(1, "Mars", ""), false)

        inner class EarthViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(dataItem: Pair<Data, Boolean>) {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    itemView.findViewById<TextView>(R.id.descriptionTextView).text =
                        dataItem.first.someDescription
                    itemView.findViewById<ImageView>(R.id.wikiImageView).setOnClickListener {
                        onListItemClickListener.onItemClick(
                            dataItem.first
                        )
                    }
                }
            }
        }

        inner class MarsViewHolder(view: View) : BaseViewHolder(view) {

            /*override fun bind(data: Data) {
                itemView.findViewById<ImageView>(R.id.marsImageView)
                    .setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }*/


            override fun bind(dataItem: Pair<Data, Boolean>) {
                itemView.findViewById<ImageView>(R.id.marsImageView)
                    .setOnClickListener { onListItemClickListener.onItemClick(dataItem.first) }
                itemView.findViewById<ImageView>(R.id.addItemImageView)
                    .setOnClickListener { addItem() }
                itemView.findViewById<ImageView>(R.id.removeItemImageView)
                    .setOnClickListener { removeItem() }
                itemView.findViewById<ImageView>(R.id.moveItemDown)
                    .setOnClickListener { moveDown() }
                itemView.findViewById<ImageView>(R.id.moveItemUp)
                    .setOnClickListener { moveUp() }
                itemView.findViewById<TextView>(R.id.marsDescriptionTextView).visibility =
                    if (dataItem.second) View.VISIBLE else View.GONE
                itemView.findViewById<TextView>(R.id.marsTextView).setOnClickListener { toggleText() }

            }

            private fun toggleText() {
                data[layoutPosition] = data[layoutPosition].let {
                    it.first to !it.second
                }
                notifyItemChanged(layoutPosition)
            }


            private fun moveUp() {
                layoutPosition.takeIf { it > 1 }?.also { currentPosition ->
                    data.removeAt(currentPosition).apply {
                        data.add(currentPosition - 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition - 1)
                }
            }

            private fun moveDown() {
                layoutPosition.takeIf { it < data.size - 1 }?.also { currentPosition ->
                    data.removeAt(currentPosition).apply {
                        data.add(currentPosition + 1, this)
                    }
                    notifyItemMoved(currentPosition, currentPosition + 1)
                }
            }


            private fun addItem() {
                data.add(layoutPosition, generateItem())
                notifyItemInserted(layoutPosition)
            }

            private fun removeItem() {
                data.removeAt(layoutPosition)
                notifyItemRemoved(layoutPosition)
            }
        }


        inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(dataItem: Pair<Data, Boolean>) {
                itemView.setOnClickListener { onListItemClickListener.onItemClick(dataItem.first) }
            }
        }

        interface OnListItemClickListener {
            fun onItemClick(data: Data)
        }

        companion object {
            private const val TYPE_EARTH = 0
            private const val TYPE_MARS = 1
            private const val TYPE_HEADER = 2
        }
    }
}