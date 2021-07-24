package saymobile.company.operationsmanagementapp.view.workerdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.worker_data_entry.view.*
import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.model.WorkEntry
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.collections.ArrayList

class WorkerDetailListAdapter(val entriesList: ArrayList<WorkEntry>) :
    RecyclerView.Adapter<WorkerDetailListAdapter.WorkerDetailsViewHolder>() {

    class WorkerDetailsViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    fun updateDetailedEntryList(newEntriesList: List<WorkEntry>){
        entriesList.clear()
        entriesList.addAll(newEntriesList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.worker_data_entry, parent, false)
        return WorkerDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return entriesList.size
    }

    override fun onBindViewHolder(holder: WorkerDetailsViewHolder, position: Int) {
        //enter date format here

        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.US)
        val thedate = entriesList[position].dateOfEntry!!.toDate()
        val formattedDate = sdf.format(thedate)
        val total = entriesList[position].quantity

        holder.view.entry_item.text = entriesList[position].item
//        holder.view.entry_date.text = entriesList[position].dateOfEntry.time.toString()
        holder.view.entry_quantity.text = entriesList[position].quantity.toString()
        holder.view.entry_total.text = entriesList[position].totalValue.toString()
        holder.view.entry_date.text = formattedDate
    }
}