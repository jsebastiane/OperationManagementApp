package saymobile.company.operationsmanagementapp.view.workerdetails

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.storage.FirebaseStorage
import kotlinx.android.synthetic.main.fragment_employee_detail.*

import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.getProgressDrawable
import saymobile.company.operationsmanagementapp.loadImage
import saymobile.company.operationsmanagementapp.viewmodel.workerdetails.EmployeeDetailViewModel

class EmployeeDetailFragment : Fragment() {

    private lateinit var viewModel: EmployeeDetailViewModel
    private var detailsSummaryAdapter = DetailsSummaryAdapter(arrayListOf())
    private var workDetailListAdapter = WorkerDetailListAdapter(arrayListOf())
    private var workerId = ""
    private var workerName = ""


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_employee_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arguments?.let {
            workerId = EmployeeDetailFragmentArgs.fromBundle(it).workerId
        }

        arguments?.let {
            workerName = EmployeeDetailFragmentArgs.fromBundle(it).workerName
        }

        add_work_details.setOnClickListener {
            val action = EmployeeDetailFragmentDirections.actionEmployeeDetailFragmentToAddEntryFragment()
            action.workerIdEntry = workerId
            Navigation.findNavController(it).navigate(action)
        }

        work_entries_summary.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = detailsSummaryAdapter
        }

        work_entries_detail.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = workDetailListAdapter
        }


        viewModel = ViewModelProviders.of(this).get(EmployeeDetailViewModel::class.java)
        viewModel.retrieveWorkerData(workerId)
        worker_name_details.text = workerName
        observeViewModel()
        loadProfileImage()

    }

    fun observeViewModel(){
        viewModel.workEntries.observe(viewLifecycleOwner, Observer { entries->
            entries?.let {
                workDetailListAdapter.updateDetailedEntryList(entries)
            }
        })

        viewModel.viewSummaryData.observe(viewLifecycleOwner, Observer { summaries->
            summaries?.let {
                detailsSummaryAdapter.updateSummaryList(summaries)
            }
        })

        viewModel.totalCostOfEntries.observe(viewLifecycleOwner, Observer { totalOwed->
            total_worker_details.text = String.format("%.2f", totalOwed).toDouble().toString()
        })
    }

    private fun loadProfileImage(){
        val gsReference = FirebaseStorage.getInstance().reference
            .child("/$workerId/profileImage.jpeg")

        profile_image_details.loadImage(gsReference, getProgressDrawable(profile_image_details.context))
    }

}
