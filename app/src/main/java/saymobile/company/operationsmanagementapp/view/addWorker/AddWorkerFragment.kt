package saymobile.company.operationsmanagementapp.view.addWorker

import android.Manifest
import android.app.Activity.RESULT_OK
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController

import kotlinx.android.synthetic.main.fragment_add_worker.*

import saymobile.company.operationsmanagementapp.R

import saymobile.company.operationsmanagementapp.viewmodel.addworker.AddWorkerViewModel
import java.io.File
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

private lateinit var currentPhotoPath: String
private const val REQUEST_TAKE_PHOTO = 1

class AddWorkerFragment : Fragment() {

    private var imageUri: Uri? = null
    private lateinit var viewModel: AddWorkerViewModel
//    private lateinit var testingPath: File


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_worker, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(AddWorkerViewModel::class.java)
        viewModel.loading.observe(viewLifecycleOwner, Observer { loading->
            loading?.let {
                if(it){
                    progressBar_addWorker.visibility = View.VISIBLE
                }else{
                    progressBar_addWorker.visibility = View.GONE
                }
            }
        })

        viewModel.finishedUpload.observe(viewLifecycleOwner, Observer { uploadComplete->
            uploadComplete?.let {
                if(it){
                    findNavController().navigateUp()
                }
            }
        })

        add_workerPicture.setOnClickListener {
            dispatchTakePictureIntent()
        }

        uploadPic.setOnClickListener {
            if(worker_name.text.isEmpty() || imageUri == null){
                Toast.makeText(activity, "All fields must be filled", Toast.LENGTH_SHORT).show()
            }else{
                viewModel.uploadWorkerInfo(worker_name.text.toString(), imageUri!!)
            }

        }
    }

    private fun dispatchTakePictureIntent() {
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(requireActivity().packageManager)?.also {
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    // Error occurred while creating the File
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        requireActivity(),
                        "saymobile.company.operationsmanagementapp.fileprovider",
                        it
                    )
                    imageUri = photoURI
//                    testingPath = photoFile
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO)
                }
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_TAKE_PHOTO && resultCode == RESULT_OK) {
            val imageTaken = BitmapFactory.decodeFile(currentPhotoPath)
            picture_preview.setImageBitmap(imageTaken)
        }

    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name and add unique file name. Will change this to and ID associated
        // with worker or product in the saytech app
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = activity?.getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }





}
