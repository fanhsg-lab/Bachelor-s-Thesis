package com.example.ptyxiakh3

import android.content.Context
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat

class MyCustomAdapter(private val context: Context, private val data: LinkedHashMap<String, List<Triple<String, Int, Long>>>) : BaseExpandableListAdapter() {

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
        val groupTitle = getGroup(groupPosition) as String
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.group_header, parent, false)
        view.findViewById<TextView>(R.id.groupHeader).text = groupTitle
        return view
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val (quizText, difficulty, questionId) = getChild(groupPosition, childPosition) as Triple<String, Int, Long>
        val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.child_item, parent, false)
        Log.d("AllQ","mphka")
        var (countF, countT) =  FindState(questionId)
        val textView = view.findViewById<TextView>(R.id.childItem)
        textView.text = quizText
        val color = getAnswerColor(countT, countT+countF)

        // Change text color based on difficulty
        Log.d("AllQ2", "${countT} , ${countF} , ${countF==0 && countT!=0}")
        textView.setTextColor(color)


        // Set an OnClickListener to show a Toast message with the quiz text
        view.setOnClickListener {
            //Toast.makeText(context, "Quiz: $quizText", Toast.LENGTH_SHORT).show()
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

