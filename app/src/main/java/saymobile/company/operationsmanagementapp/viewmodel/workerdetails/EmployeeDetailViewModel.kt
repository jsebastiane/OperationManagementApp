package saymobile.company.operationsmanagementapp.viewmodel.workerdetails

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import saymobile.company.operationsmanagementapp.model.Item
import saymobile.company.operationsmanagementapp.model.SummaryEntryItem
import saymobile.company.operationsmanagementapp.model.WorkEntry
import java.sql.Timestamp
import java.text.SimpleDateFormat
import java.util.*

class EmployeeDetailViewModel : ViewModel() {

    private val mFirebaseDatabase = FirebaseFirestore.getInstance()
    //This should direct us to the workers file so an id is needed
    //Change this later
    val workerId = 0
    private val docRef = mFirebaseDatabase.collection("buyers")
            //this will change to a variable for Id
    var summaryData = mutableListOf<SummaryEntryItem>()
    private val summaryDataLive = MutableLiveData<List<SummaryEntryItem>>()
    val viewSummaryData: LiveData<List<SummaryEntryItem>>
        get() = summaryDataLive
    val workEntries = MutableLiveData<List<WorkEntry>>()
    val totalCostOfEntries = MutableLiveData<Double>()



    fun retrieveWorkerData(workerId: String){
        docRef.document(workerId).collection("entries")
            .addSnapshotListener { snapshot, error ->
                if (error != null){
                    Log.d("LiveData", "Listen failed")
                    return@addSnapshotListener
                }
                if (snapshot != null){
                    val workerSnapshotData = ArrayList<WorkEntry>()
                    val documents = snapshot.documents
                    documents.forEach {
                        val entry = it.toObject(WorkEntry::class.java)
                        if(entry != null){
                            workerSnapshotData.add(entry)
                        }
                    }
                    workEntries.value = workerSnapshotData
                    summariseData(workerSnapshotData)

                }
            }
    }

    fun summariseData(workerData: ArrayList<WorkEntry>){
        for (i in workerData){
            val currentDate = i.dateOfEntry!!.toDate()
            val sdf = SimpleDateFormat("W", Locale.US)
            val formattedDate = sdf.format(currentDate)
            println("PRINTING FORMATTED DATE")
            println(formattedDate)
        }
        var totalOwed = 0.0
        summaryData.clear()
        val filteredEntries = workerData.groupBy { it.item }
        val uniqueItems = filteredEntries.keys.toList()
        println(uniqueItems)
        for (i in uniqueItems){
            var quantity: Double = 0.0
            for (entry in workerData){
                if(entry.item == i){
                    quantity += entry.quantity
                    totalOwed += entry.totalCost
                }
            }
            val finalQuantity = String.format("%.2f", quantity).toDouble()
            summaryData.add(SummaryEntryItem(i, finalQuantity))
        }
        println(summaryData)
        totalCostOfEntries.value = totalOwed
        summaryDataLive.value = summaryData

    }

}