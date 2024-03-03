import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ptyxiakh3.QueueItem
import com.example.ptyxiakh3.R
import com.example.ptyxiakh3.quizAdapter

class QueueAdapter(
    private val context: Context,
    private val itemList: ArrayList<QueueItem>
) : RecyclerView.Adapter<QueueAdapter.QueueViewHolder>() {

    // ViewHolder class
    class QueueViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        // Define views within the item layout
        val textViewTitle: TextView = itemView.findViewById(R.id.textView)
        // Add more views as needed
    }


    // Create new ViewHolders (invoked by the layout manager)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): QueueViewHolder {
        // Use the context property from the outer class
        val inflater = LayoutInflater.from(context)
        val itemView = inflater.inflate(R.layout.queue_item_layout, parent, false)
        return QueueViewHolder(itemView)
    }

    // Replace the contents of a ViewHolder (invoked by the layout manager)
    // Replace the contents of a ViewHolder (invoked by the layout manager)
    override fun onBindViewHolder(holder: QueueViewHolder, position: Int) {
        // Get the data model for this position
        val currentItem = itemList[position]

        // Bind data to the ViewHolder's views
        holder.textViewTitle.text = currentItem.ItemText
    }


    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount(): Int {
        return itemList.size
    }
}
