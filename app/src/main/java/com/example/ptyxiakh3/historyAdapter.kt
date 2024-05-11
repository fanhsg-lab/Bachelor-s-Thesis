package com.example.ptyxiakh3

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.example.navigationtry2.HistoryModel

class AdapterHistory(
    private val context: Context,
    private val historyModels: ArrayList<HistoryModel>,
    private val navController: NavController // Add the NavController parameter
) : RecyclerView.Adapter<AdapterHistory.MyViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.history_item_layout, parent, false)
        return MyViewHolder(view)
    }

    var aristera = 0
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.Text.text = historyModels[position].text
        holder.Chaptertext.text = historyModels[position].chapter
        holder.ChapterInfo.text = historyModels[position].inforext
        holder.imageView.setImageResource(historyModels[position].image)
        var tests = historyModels[position].tests

        val buttons = listOf(
            holder.Button1, holder.Button2, holder.Button3, holder.Button4, holder.Button5,
            holder.Button6, holder.Button7, holder.Button8, holder.Button9, holder.Button10,
            holder.Button11, holder.Button12
        )

        val buttonPosition = position + 1

        val ImageView = listOf(
            holder.Image1, holder.Image2
        )


        for (i in tests until buttons.size) {
            buttons[i].visibility = View.GONE
        }

        Log.d("sxolia ", aristera.toString())
        margin(holder, tests, position, aristera)
        if (tests < 7) {
            aristera++
        }
    }

    private fun margin(holder: MyViewHolder, tests: Int, position: Int, aristera: Int) {
        val button2LayoutParams = holder.Button2.layoutParams as ViewGroup.MarginLayoutParams
        val button3LayoutParams = holder.Button3.layoutParams as ViewGroup.MarginLayoutParams
        val button4LayoutParams = holder.Button4.layoutParams as ViewGroup.MarginLayoutParams
        val button5LayoutParams = holder.Button5.layoutParams as ViewGroup.MarginLayoutParams
        val button6LayoutParams = holder.Button6.layoutParams as ViewGroup.MarginLayoutParams
        val button7LayoutParams = holder.Button7.layoutParams as ViewGroup.MarginLayoutParams
        val button8LayoutParams = holder.Button8.layoutParams as ViewGroup.MarginLayoutParams
        val button9LayoutParams = holder.Button9.layoutParams as ViewGroup.MarginLayoutParams

        val buttons = arrayOf(holder.Button1,holder.Button2,holder.Button3,holder.Button4,holder.Button5,holder.Button6,holder.Button7,holder.Button8,holder.Button9,holder.Button10,holder.Button11,holder.Button12 )

        buttons.forEachIndexed { index, button ->

                val str = "${position+1}.${index+1}"
                println("Button $str clicked")

                if (DbQuery.myProfile.quizs.contains(str)) {
                    println("The string $str exists in the list.")
                    button.setBackgroundResource(R.drawable.historybutton2)
                    button.isEnabled = true
                }else{
                    button.isEnabled = false
                }

        }



        for (i in 0 until 12) {
            val str = "$position.$i" // Using string interpolation to create the string
            println(str)
        }



        val image1LayoutParams = holder.Image1.layoutParams as ViewGroup.MarginLayoutParams
        val image2LayoutParams = holder.Image2.layoutParams as ViewGroup.MarginLayoutParams




        val leftMarginList = when (tests) {
            5 -> if (aristera % 2 == 1) {listOf(300, 500, 300)}else listOf(-300, -500, -300)
            6 -> if (aristera % 2 == 1) listOf(300, 550, 450, 250) else listOf(-300, -550, -450, -250)
            7 -> if (aristera % 2 == 1) listOf(300, 500, 300, 100, -100) else listOf(-300, -500, -300, -100, +100)
            8 -> if (aristera % 2 == 1) listOf(300, 500, 300, 0, -300, -200) else listOf(-300, -500, -300, -0, +300, +200)
            9 -> if (aristera % 2 == 1) listOf(300, 500, 300, 100, -100, -200, -300) else listOf(-300, -500, -300, -100, +100, +200, +300)
            else -> return
        }


        with(button2LayoutParams) { setMargins(leftMarginList[0], topMargin, 0, bottomMargin) }
        holder.Button2.layoutParams = button2LayoutParams

        with(button3LayoutParams) { setMargins(leftMarginList[1], topMargin, 0, bottomMargin) }
        holder.Button3.layoutParams = button3LayoutParams

        with(button4LayoutParams) { setMargins(leftMarginList[2], topMargin, 0, bottomMargin) }
        holder.Button4.layoutParams = button4LayoutParams

        if (aristera % 2 == 1) {
            with(image1LayoutParams) { setMargins(-400, 400, 0, 0) }
            with(image2LayoutParams) { setMargins(700, 900, 0, 0) }

        }else{
            with(image1LayoutParams) { setMargins(400, 400, 0, 0) }
            with(image2LayoutParams) { setMargins(-700, 900, 0, 0) }
        }
        holder.Image1.layoutParams = image1LayoutParams
        holder.Image2.layoutParams = image2LayoutParams

        if (tests > 5) {
            with(button5LayoutParams) { setMargins(leftMarginList[3], topMargin, 0, bottomMargin) }
            holder.Button5.layoutParams = button5LayoutParams


            if (aristera % 2 == 1) {
                with(image1LayoutParams) { setMargins(-600, 350, 0, 0) }
                with(image2LayoutParams) { setMargins(-350, 800, 0, 0) }

            }else{
                with(image1LayoutParams) { setMargins(600, 350, 0, 0) }
                with(image2LayoutParams) { setMargins(350, 800, 0, 0) }
            }

            holder.Image1.layoutParams = image1LayoutParams
            holder.Image2.layoutParams = image2LayoutParams

            if (tests > 6) {
                with(button6LayoutParams) { setMargins(leftMarginList[4], topMargin, 0, bottomMargin) }
                holder.Button6.layoutParams = button6LayoutParams


                if (aristera % 2 == 1) {
                    with(image1LayoutParams) { setMargins(-400, 500, 0, 0) }
                    with(image2LayoutParams) { setMargins(600, 1300, 0, 0) }

                }else{

                    with(image1LayoutParams) { setMargins(400, 500, 0, 0) }
                    with(image2LayoutParams) { setMargins(-600, 1300, 0, 0) }
                }

                holder.Image1.layoutParams = image1LayoutParams
                holder.Image2.layoutParams = image2LayoutParams


                if (tests > 7) {
                    with(button7LayoutParams) { setMargins(leftMarginList[5], topMargin, 0, bottomMargin) }
                    holder.Button7.layoutParams = button7LayoutParams
                    if (tests > 8) {
                        with(button8LayoutParams) { setMargins(leftMarginList[6], topMargin, 0, bottomMargin) }
                        holder.Button8.layoutParams = button8LayoutParams


                        if (aristera % 2 == 1) {
                            with(image1LayoutParams) { setMargins(-400, 500, 0, 0) }
                            with(image2LayoutParams) { setMargins(600, 1700, 0, 0) }

                        }else{

                            with(image1LayoutParams) { setMargins(400, 500, 0, 0) }
                            with(image2LayoutParams) { setMargins(-600, 1700, 0, 0) }
                        }

                        holder.Image1.layoutParams = image1LayoutParams
                        holder.Image2.layoutParams = image2LayoutParams



                        if (tests > 9) {
                            with(button9LayoutParams) { setMargins(leftMarginList[7], topMargin, 0, bottomMargin) }
                            holder.Button9.layoutParams = button9LayoutParams
                        }
                    }
                }
            }
        }
    }

    override fun getItemCount(): Int {
        return historyModels.size
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var imageView: ImageView = itemView.findViewById(R.id.image_view)
        var Chaptertext: TextView = itemView.findViewById(R.id.chapterID)
        var Text: TextView = itemView.findViewById(R.id.text_view)
        var Button1: Button = itemView.findViewById(R.id.Btn1)
        var Button2: Button = itemView.findViewById(R.id.Btn2)
        var Button3: Button = itemView.findViewById(R.id.Btn3)
        var Button4: Button = itemView.findViewById(R.id.Btn4)
        var Button5: Button = itemView.findViewById(R.id.Btn5)
        var Button6: Button = itemView.findViewById(R.id.Btn6)
        var Button7: Button = itemView.findViewById(R.id.Btn7)
        var Button8: Button = itemView.findViewById(R.id.Btn8)
        var Button9: Button = itemView.findViewById(R.id.Btn9)
        var Button10: Button = itemView.findViewById(R.id.Btn10)
        var Button11: Button = itemView.findViewById(R.id.Btn11)
        var Button12: Button = itemView.findViewById(R.id.Btn12)

        var Image1: ImageView = itemView.findViewById(R.id.image_view)
        var Image2: ImageView = itemView.findViewById(R.id.imageView2)
        var infoImage: ImageView = itemView.findViewById(R.id.h_info)
        var closeBtn: ImageView = itemView.findViewById(R.id.closeBtn)

        var infoLayout: LinearLayout = itemView.findViewById(R.id.ChapterpopupLayout)

        var ChapterInfo: TextView = itemView.findViewById(R.id.chapterInfo)


        init {


            infoImage.setOnClickListener{
                infoLayout?.visibility=View.VISIBLE
            }

            closeBtn.setOnClickListener{
                infoLayout?.visibility=View.GONE
            }


            // Set a click listener for Button1
            Button1.setOnClickListener {
                val buttonPosition = adapterPosition + 1 // Adding 1 to match the position starting from 1
                val message = "Button1 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(1,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button2.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button2 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(2,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button3.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button3 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(3,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button4.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button4 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(4,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button5.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button5 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(5,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button6.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button6 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(6,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button7.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button7 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(7,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button8.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button8 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(8,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button9.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button9 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(9,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button10.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button10 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(10,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button11.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button11 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(11,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

            Button12.setOnClickListener {
                val buttonPosition = position + 1
                val message = "Button12 clicked, position $buttonPosition"


                val action = HistoryFragmentDirections.actionHistoryFragmentToQuizFragment(12,buttonPosition,"History")
                navController.navigate(action) // Use the provided NavController
            }

        }
    }
}
