package saymobile.company.operationsmanagementapp.model

import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.Timestamp
import java.sql.Date

@IgnoreExtraProperties
data class WorkEntry(
    val item: String = "",
    val quantity: Double = 0.0,
    val totalCost: Double = 0.0,
    val totalValue: Double = 0.0,
    val dateOfEntry: Timestamp? = null
)