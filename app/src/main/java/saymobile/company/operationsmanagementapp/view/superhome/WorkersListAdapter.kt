package saymobile.company.operationsmanagementapp.view.superhome

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.Glide.with
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.item_worker.view.*
import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.getProgressDrawable
import saymobile.company.operationsmanagementapp.loadImage
import saymobile.company.operationsmanagementapp.model.Buyer
import saymobile.company.operationsmanagementapp.model.GlideAPI
import saymobile.company.operationsmanagementapp.model.Worker
import java.util.*
import kotlin.collections.ArrayList

class WorkersListAdapter(val buyersList: ArrayList<Buyer>) :
    RecyclerView.Adapter<WorkersListAdapter.WorkerViewHolder>() {

    fun updateWorkersList(newBuyersList: List<Buyer>) {
        buyersList.clear()
        buyersList.addAll(newBuyersList)
        notifyDataSetChanged()
    }

    class WorkerViewHolder(var view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WorkerViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.item_worker, parent, false)
        return WorkerViewHolder(
            view
        )
    }

    override fun getItemCount() = buyersList.size

    override fun onBindViewHolder(holder: WorkerViewHolder, position: Int) {
        holder.view.worker_name.text = buyersList[position].name
        holder.view.setOnClickListener {
            val action =
                SupervisorHomeFragmentDirections.actionSupervisorHomeFragmentToEmployeeDetailFragment()
            action.workerId = buyersList[position].buyerId
            action.workerName = buyersList[position].name
            Navigation.findNavController(it).navigate(action)
        }

        val workerId = buyersList[position].buyerId
        val gsReference = FirebaseStorage.getInstance().reference
            .child("/$workerId/profileImage.jpeg")

        holder.view.profile_image.loadImage(gsReference, getProgressDrawable(holder.view.profile_image.context))
        //load image here last
        //our image is not an image view so we will have to come up with something else
    }
}