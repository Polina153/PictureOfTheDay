package com.example.pictureoftheday

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
            Data("Earth"),
            Data("Earth"),
            Data("Mars", ""),
            Data("Earth"),
            Data("Earth"),
            Data("Earth"),
            Data("Mars", null),
            Data("Earth"),
            Data("Earth"),
            Data("Earth"),
            Data("Mars", null)
        )

        data.add(0, Data("Header"))

        binding.recyclerView.addItemDecoration(
            DividerItemDecoration(
                this,
                LinearLayoutManager.VERTICAL
            )
        )

        binding.recyclerView.adapter = RecyclerActivityAdapter(
            object : RecyclerActivityAdapter.OnListItemClickListener {
                override fun onItemClick(data: Data) {
                    Toast.makeText(this@RecyclerActivity, data.someText, Toast.LENGTH_SHORT).show()
                }
            },
            data
        )

    }

    class RecyclerActivityAdapter(
        private var onListItemClickListener: OnListItemClickListener,
        private var data: List<Data>
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
                data[position].someDescription.isNullOrBlank() -> TYPE_MARS
                else -> TYPE_EARTH
            }
        }

        inner class EarthViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(data: Data) {
                if (layoutPosition != RecyclerView.NO_POSITION) {
                    itemView.findViewById<TextView>(R.id.descriptionTextView).text =
                        data.someDescription
                    itemView.findViewById<ImageView>(R.id.wikiImageView).setOnClickListener {
                        onListItemClickListener.onItemClick(
                            data
                        )
                    }
                }
            }
        }

        inner class MarsViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(data: Data) {
                itemView.findViewById<ImageView>(R.id.marsImageView)
                    .setOnClickListener { onListItemClickListener.onItemClick(data) }
            }
        }

        inner class HeaderViewHolder(view: View) : BaseViewHolder(view) {

            override fun bind(data: Data) {
                itemView.setOnClickListener { onListItemClickListener.onItemClick(data) }
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