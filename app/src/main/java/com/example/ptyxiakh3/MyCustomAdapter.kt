package com.example.ptyxiakh3

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.RecyclerView

class MyCustomAdapter(
    private val context: Context,
    private val data: LinkedHashMap<String, List<Quartet<String, Int, Long, String>>>,
    private val childItemClickListener: ChildItemClickListener // Add this
) : BaseExpandableListAdapter() {

    override fun getGroupCount(): Int {
        return data.keys.size
    }

    override fun getChildrenCount(groupPosition: Int): Int {
        return data[data.keys.elementAt(groupPosition)]?.size ?: 0
    }

    private val chapters: List<String> = data.keys.toList()

    override fun getGroup(groupPosition: Int): Any = chapters[groupPosition]

    override fun getChild(groupPosition: Int, childPosition: Int): Any = data[chapters[groupPosition]]!![childPosition]


    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        val groupTitle = getGroup(groupPosition) as String // Get the group title

        // Check if an existing view is being reused, otherwise inflate the view
        val view: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.group_item, parent, false)

        // Lookup the TextView in the custom layout
        val textView = view.findViewById<TextView>(R.id.groupName)
        textView.text = groupTitle // Set the group title to the TextView

        // Customize your view here - for example, set an icon indicating expanded/collapsed state if you have one

        return view
    }


    @SuppressLint("SetTextI18n")
    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val (quizText, difficulty, questionId, style) = getChild(groupPosition, childPosition) as Quartet<String, Int, Long, String>
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.child_item, parent, false)
        Log.d("AllQ","mphka")
        var (countF, countT) =  FindState(questionId)
        val textView = view.findViewById<TextView>(R.id.childItem)
        val styletextView = view.findViewById<TextView>(R.id.childItem2)
        val difficultytextView = view.findViewById<TextView>(R.id.childItem3)
        val Circle = view.findViewById<ImageView>(R.id.circleid)
        Log.d("allQ2","Id: $questionId")
        textView.text = questionId.toString()
        difficultytextView.text =difficulty.toString()
        styletextView.text = style
        val color = getAnswerColor(countT, countT + countF) // Get your dynamic color

// Assuming you are in an Activity or have context available
        val drawable = ContextCompat.getDrawable(context, R.drawable.circle_red) as GradientDrawable
        drawable.setColor(color) // Set the color dynamically

// If you are setting this drawable to a View, for example, an ImageView
        Circle.setImageDrawable(drawable)


        // Change text color based on difficulty



        // Set an OnClickListener to show a Toast message with the quiz text
        view.setOnClickListener {
            childItemClickListener.onChildItemClick(groupPosition, childPosition, questionId)
        }
        return view
    }




    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }
}

fun FindState(questionId: Long, ): Pair<Int, Int> {
    val segmentLength = 5
    val startIndex = (questionId - 1) * segmentLength
    val endIndex = startIndex + segmentLength
    var countF = 0
    var countT = 0
    // Ensure the indices are within the bounds of the string
    if (startIndex < DbQuery.myProfile.qHistory.length && endIndex <= DbQuery.myProfile.qHistory.length) {
        val currentSegment = DbQuery.myProfile.qHistory.substring(startIndex.toInt(), endIndex.toInt())
        countF = currentSegment.count { it == 'F' }
        countT = currentSegment.count { it == 'T' }

        Log.d("AllQ","Number of 'F': $countF")
        Log.d("AllQ","Number of 'T': $countT")

    }
    Log.d("QuizFragment2", "${DbQuery.myProfile.qHistory}")
    return Pair(countF,countT)
}
fun getAnswerColor(correctAnswers: Int, totalAnswers: Int): Int {
    val total = totalAnswers.coerceAtLeast(1)  // Avoid division by zero
    val proportionCorrect = correctAnswers.coerceIn(0, total).toFloat() / total

    val red = Color.RED
    val orange = Color.rgb(255, 165, 0) // Standard orange color
    val yellow = Color.YELLOW
    val green = Color.GREEN

    val (startColor, endColor, localProgress) = when {
        totalAnswers == 0 -> Triple(Color.GRAY, Color.GRAY, 0f)
        proportionCorrect < 0.33f -> Triple(red, orange, proportionCorrect * 3)
        proportionCorrect < 0.67f -> Triple(orange, yellow, (proportionCorrect - 0.33f) * 3)
        else -> Triple(yellow, green, (proportionCorrect - 0.67f) * 3)
    }

    return Color.rgb(
        lerp(Color.red(startColor), Color.red(endColor), localProgress),
        lerp(Color.green(startColor), Color.green(endColor), localProgress),
        lerp(Color.blue(startColor), Color.blue(endColor), localProgress)
    )
}

fun lerp(start: Int, end: Int, fraction: Float): Int {
    return (start + (end - start) * fraction).toInt()
}
