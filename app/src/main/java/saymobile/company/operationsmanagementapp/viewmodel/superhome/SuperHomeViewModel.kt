package saymobile.company.operationsmanagementapp.viewmodel.superhome

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import saymobile.company.operationsmanagementapp.model.Buyer
import saymobile.company.operationsmanagementapp.model.WorkEntry
import saymobile.company.operationsmanagementapp.model.Worker
import java.util.ArrayList


class SuperHomeViewModel : ViewModel() {

    private var mFirebaseDatabase = FirebaseFirestore.getInstance()
    val docRef = mFirebaseDatabase.collection("buyers")
    var buyersList = MutableLiveData<List<Buyer>>()
    var workersList = MutableLiveData<List<Worker>>()
    var workers = mutableListOf<Worker>()
    var buyers = mutableListOf<Buyer>()
    val ordersLoadError = MutableLiveData<Boolean>()
    val loading = MutableLiveData<Boolean>()

    fun retrieveWorkers() {

        docRef.addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.d("LiveData", "Listen failed")
                    return@addSnapshotListener
                }
                if (snapshot != null){
                    val workerSnapshotData = ArrayList<Buyer>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val entry = it.toObject(Buyer::class.java)
                        if(entry != null){
                            workerSnapshotData.add(entry)
                        }
                    }
                    buyersList.value = workerSnapshotData

                }
            }


    }

    fun filterOrders() {
        workersList.value = workers
    }


    
}