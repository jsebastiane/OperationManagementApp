package saymobile.company.operationsmanagementapp.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.FirebaseFirestore
import saymobile.company.operationsmanagementapp.model.Item
import saymobile.company.operationsmanagementapp.model.WorkEntry
import com.google.firebase.Timestamp
import saymobile.company.operationsmanagementapp.model.BuyerEntry
import java.sql.Date

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToLong

class AddEntryViewModel : ViewModel() {

    private var mFirebaseDatabase = FirebaseFirestore.getInstance()
    private var itemRef = mFirebaseDatabase.collection("items")
    private var addEntryRef = mFirebaseDatabase.collection("workerData")
    var tempItemsList = mutableListOf<String>()
    var theItems = MutableLiveData<List<String>>()
    var loading = MutableLiveData<Boolean>()
    private var currentWorkerId: String = ""


    fun addWorkerEntry(entry: BuyerEntry) {
        //the add screen will have the id
        val workerReference = addEntryRef.document(currentWorkerId)
            .collection("entries")
        workerReference.document().set(entry)

    }

    fun retrieveItems() {
        loading.value = true
        tempItemsList.add("Select item")
        itemRef.get().addOnSuccessListener { items ->
            for (i in items) {
                tempItemsList.add(i.id)
            }
            theItems.value = tempItemsList
        }.addOnFailureListener {
        }.addOnCompleteListener {
            loading.value = false
        }

    }

    fun submitData(name: String, quantity: Double, id: String) {
        currentWorkerId = id
        getItemInfo(name, quantity)
    }

    fun getItemInfo(name: String, quantity: Double) {
        val itemReference = mFirebaseDatabase.collection("items")
            .document(name)

        itemReference.get().addOnSuccessListener { document ->
            if (document != null) {
                Log.d("Database", "Success!")
                val item = document.toObject(Item::class.java)!!
                calculateTotalCostAndValue(item, quantity)
            } else {
                Log.d("Database", "Failed!")
            }
        }
    }

    fun calculateTotalCostAndValue(item: Item, quantity: Double) {
        val temptotalValue = item.value * quantity
        val totalValue = String.format("%.2f", temptotalValue).toDouble()


        addWorkerEntry(BuyerEntry(item, quantity, totalValue, Timestamp.now()))
    }
}