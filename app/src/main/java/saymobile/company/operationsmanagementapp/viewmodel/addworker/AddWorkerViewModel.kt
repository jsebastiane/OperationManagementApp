package saymobile.company.operationsmanagementapp.viewmodel.addworker

import android.app.Application
import android.net.Uri
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import saymobile.company.operationsmanagementapp.model.Buyer


class AddWorkerViewModel(application: Application): AndroidViewModel(application) {

    private var mFirebaseDatabase = FirebaseFirestore.getInstance()
    var loading = MutableLiveData<Boolean>()
    var finishedUpload = MutableLiveData<Boolean>()
    val docRef = mFirebaseDatabase.collection("buyers")

    fun uploadImage(imageUri: Uri, buyerId: String){
        val imageRef = FirebaseStorage.getInstance().reference.child("/$buyerId/profileImage.jpeg")
        //just be sure this is always false at the beginning of the process
        finishedUpload.value = false
        val imageTask = imageRef.putFile(imageUri)
        imageTask.addOnSuccessListener { Toast.makeText(getApplication(), "Successfully uploaded", Toast.LENGTH_SHORT).show() }
        imageTask.addOnFailureListener { Toast.makeText(getApplication(), "Failed to upload image", Toast.LENGTH_SHORT).show() }
        imageTask.addOnCompleteListener { finishedUpload.value = true
        loading.value = false}
    }

    fun uploadWorkerInfo(name: String, imageUri: Uri){
        val doc  = docRef.document()
        val newBuyer = Buyer(doc.id, name, 0.0, 0.0, 0.0)
        loading.value = true
        doc.set(newBuyer).addOnSuccessListener { Log.d("WorkerUpload", "Successfully uploaded worker") }
            .addOnFailureListener { Log.d("WorkerUpload", "Failed to upload worker") }
            .addOnCompleteListener { uploadImage(imageUri, doc.id)}
    }
}