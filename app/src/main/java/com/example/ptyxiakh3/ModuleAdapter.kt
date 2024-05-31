package com.example.ptyxiakh3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView

class AdapterModule(private val context: Context, private val ModuleModels: ArrayList<ModuleModel>,private val navController: NavController) :
    RecyclerView.Adapter<AdapterModule.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.module_item_layout, parent, false)

        return MyViewHolder(view)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {

        Log.d("QuestionLog", "Question ID ${ModuleModels[position].answered} + ${ModuleModels[position].tests}")
        val progress = ((ModuleModels[position].answered).toFloat() / (ModuleModels[position].tests).toFloat()) * 100
        val formattedProgress = String.format("%.1f", progress)
        Log.d("QuestionLog", "Progress: $formattedProgress")
        holder.myView.setProgress(formattedProgress.toFloat(),ModuleModels[position].text)
    }



    override fun getItemCount(): Int {
        return ModuleModels.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val myView: MyView = itemView.findViewById(R.id.my_view)
        val button: Button = itemView.findViewById(R.id.button2) // Assuming button2 is your button ID
        val buttonframe: FrameLayout = itemView.findViewById(R.id.framebutton2)
        private var currentButtonState = ButtonState.SHADOW // Track the current state
        val cardView = itemView.findViewById<CardView>(R.id.my_card_view)

        init {

            cardView.setOnClickListener {
                // Handle the click event for the CardView
                //Toast.makeText(context, "AHA", Toast.LENGTH_SHORT).show()
                Log.d("ModuleA", "${adapterPosition}")

                val action = when (currentButtonState) {
                    ButtonState.CIRCLE -> ModuleFragmentDirections.actionModuleFragmentToQuizFragment(adapterPosition+1, 0, "Module")
                    ButtonState.HISTORY -> ModuleFragmentDirections.actionModuleFragmentToQuizFragment(adapterPosition+1, 1, "Module")
                    ButtonState.SHADOW -> ModuleFragmentDirections.actionModuleFragmentToQuizFragment(adapterPosition+1, 3, "Module")
                }

                navController.navigate(action) // Use the provided NavController
            }

            buttonframe.setOnClickListener {
                Log.d("patao", "Patao")
                // Cycle through the drawables
                when (currentButtonState) {
                    ButtonState.CIRCLE -> {
                        button.setBackgroundResource(R.drawable.modulebutton4)
                        currentButtonState = ButtonState.HISTORY
                    }
                    ButtonState.HISTORY -> {
                        button.setBackgroundResource(R.drawable.modulebutton2)
                        currentButtonState = ButtonState.SHADOW
                    }
                    ButtonState.SHADOW -> {
                        button.setBackgroundResource(R.drawable.modulebutton3)
                        currentButtonState = ButtonState.CIRCLE
                    }
                }

                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val clickedItem = ModuleModels[position]
                    // Use clickedItem to get details about the clicked item
                   // Toast.makeText(context, "Button clicked at position $position", Toast.LENGTH_SHORT).show()
                }
            }


        }
    }


}

enum class ButtonState {
    SHADOW, HISTORY, CIRCLE
}

