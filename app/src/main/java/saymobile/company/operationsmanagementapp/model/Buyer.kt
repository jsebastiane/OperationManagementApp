package saymobile.company.operationsmanagementapp.model

data class Buyer (
    val buyerId: String = "",
    val name: String = "",
    val totalDeposited: Double = 0.0,
    val totalDepositUsed: Double = 0.0,
    val currentSaldo: Double = 0.0
)