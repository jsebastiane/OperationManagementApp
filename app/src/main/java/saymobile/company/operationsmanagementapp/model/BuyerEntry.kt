package saymobile.company.operationsmanagementapp.model

import com.google.firebase.Timestamp

data class BuyerEntry (
    val item: Item? = null,
    val quantity: Double = 0.0,
    val totalValue: Double = 0.0,
    val dateOfEntry: Timestamp? = null
)