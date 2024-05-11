package com.example.duolingo.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.R
import com.example.ptyxiakh3.RankModel
import java.util.Locale

class RankAdapter(private val userList: List<RankModel>) : RecyclerView.Adapter<RankAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rank_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val user = userList[position]
        holder.setData(user.name, user.score, user.rank)
    }

    override fun getItemCount(): Int {
        return if (userList.size > 10) 10 else userList.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nameTV: TextView = itemView.findViewById(R.id.name)
        private val rankTV: TextView = itemView.findViewById(R.id.rank)
        private val scoreTV: TextView = itemView.findViewById(R.id.score)
        private val imgTV: TextView = itemView.findViewById(R.id.img_text)

        fun setData(name: String, score: Int, rank: Int) {
            nameTV.text = name
            scoreTV.text = "Score: $score"
            rankTV.text = "Rank - $rank"
            imgTV.text = name.toUpperCase(Locale.ROOT).substring(0, 1)
        }
    }
}
