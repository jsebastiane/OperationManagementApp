package saymobile.company.operationsmanagementapp.view.superhome

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.fragment_supervisor_home.*

import saymobile.company.operationsmanagementapp.R
import saymobile.company.operationsmanagementapp.view.LoginActivity
import saymobile.company.operationsmanagementapp.viewmodel.superhome.SuperHomeViewModel


class SupervisorHomeFragment : Fragment() {

    private lateinit var viewModel: SuperHomeViewModel
    private var buyerAdapter =
        WorkersListAdapter(
            arrayListOf()
        )


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_supervisor_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //Signs out and returns to log in page
        sign_out_x.setOnClickListener {
            val intent = Intent(activity, LoginActivity::class.java)
            FirebaseAuth.getInstance().signOut()
            activity?.finish()
            startActivity(intent)

        }

        add_worker.setOnClickListener {
            val action = SupervisorHomeFragmentDirections.actionSupervisorHomeFragmentToAddWorkerFragment()
            Navigation.findNavController(it).navigate(action)
        }

        workersList_recycler.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = buyerAdapter
        }


        viewModel = ViewModelProviders.of(this).get(SuperHomeViewModel::class.java)
        viewModel.retrieveWorkers()
        observeViewModel()

    }

    fun observeViewModel(){
        viewModel.buyersList.observe(viewLifecycleOwner, Observer { buyers ->
            buyers?.let {
                buyerAdapter.updateWorkersList(buyers)
            }
        })
    }



}
