package saymobile.company.operationsmanagementapp.view.workerdetails

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.worker_product_summary.view.*
import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.model.SummaryEntryItem

class DetailsSummaryAdapter (val summaryItems: ArrayList<SummaryEntryItem>) : RecyclerView.Adapter<DetailsSummaryAdapter.EmployeeDetailsViewHolder>() {

    class EmployeeDetailsViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    fun updateSummaryList(newSummaryItems: List<SummaryEntryItem>){
        summaryItems.clear()
        summaryItems.addAll(newSummaryItems)
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EmployeeDetailsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.worker_product_summary, parent, false)
        return EmployeeDetailsViewHolder(view)
    }

    override fun getItemCount(): Int {
        return summaryItems.size
    }

    override fun onBindViewHolder(holder: EmployeeDetailsViewHolder, position: Int) {
        holder.view.item_worker_details.text = summaryItems[position].item
        holder.view.item_quantity_details.text = summaryItems[position].quantity.toString()
    }
}