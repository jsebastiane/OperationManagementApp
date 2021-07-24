package saymobile.company.operationsmanagementapp.view.addentry

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import kotlinx.android.synthetic.main.fragment_add_entry.*
import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.viewmodel.AddEntryViewModel
import saymobile.company.operationsmanagementapp.viewmodel.workerdetails.EmployeeDetailViewModel

/**
 * A simple [Fragment] subclass.
 */
class AddEntryFragment : Fragment() {

    private var itemOptions = arrayOf<String>()
    private lateinit var viewModel: AddEntryViewModel
    private var itemSelected: String? = ""
    private var workerId = ""

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_entry, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddEntryViewModel::class.java)

        arguments?.let {
            workerId = AddEntryFragmentArgs.fromBundle(it).workerIdEntry
        }

        viewModel.theItems.observe(viewLifecycleOwner, Observer { items->
            items?.let {
                itemOptions = items.toTypedArray()
                setupSpinner()
            }
        })

        viewModel.loading.observe(viewLifecycleOwner, Observer { loading->
            if (loading){
                add_entry_progress.visibility = View.VISIBLE
            }else{
                add_entry_progress.visibility = View.GONE
            }
        })

        viewModel.retrieveItems()

        submit_entry_button.setOnClickListener {
            if(item_quantity_input.text.isEmpty() || item_quantity_input.text.toString().toDouble() <= 0.0 || itemSelected == null){
                Toast.makeText(activity?.applicationContext, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.submitData(itemSelected!!, item_quantity_input.text.toString().toDouble(), workerId)
                Navigation.findNavController(it).navigateUp()
            }
        }
    }

    private fun setupSpinner(){
        item_spinner.adapter = activity?.let { ArrayAdapter(it, android.R.layout.simple_list_item_1, itemOptions) }
        item_spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                itemSelected = null
            }
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                itemSelected = itemOptions[position]
            }
        }
    }

}
