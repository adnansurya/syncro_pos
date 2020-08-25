package com.ambeso.syncro_pos.Utility


import android.app.Activity
import android.app.ProgressDialog
import android.net.Uri
import android.os.Environment
import android.util.Log
import android.webkit.MimeTypeMap
import android.widget.Toast
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.DataInput
import java.io.File

class UploadUtility(activity: Activity) {

    var activity = activity;
    var dialog: ProgressDialog? = null
    var serverURL: String = "https://mksrobotics.000webhostapp.com/uploadToServer.php"
    var serverUploadDirectoryPath: String = "http://mksrobotics.000webhostapp.com/uploads/"
    val client = OkHttpClient()

    fun uploadFile(sourceFilePath: String, uploadedFileName: String? = null) {
        var defaultDir =  Environment.getExternalStorageDirectory().absolutePath
        var dbPath = File.separator + "KasirToko" + File.separator + "database" + File.separator
        var dbDir = defaultDir + dbPath + sourceFilePath
        Log.e("DB DIR : ", dbDir);
        uploadFile(File(dbDir), uploadedFileName);
    }
//
//    fun uploadFile(sourceFileUri: Uri, uploadedFileName: String? = null) {
//        val pathFromUri = URIPathHelper().getPath(activity,sourceFileUri)
//        uploadFile(File(pathFromUri), uploadedFileName)
//    }

    fun uploadFile(sourceFile: File, uploadedFileName: String? = null) {
        Thread {


//            val mimeType = getMimeType(sourceFile)
            val mimeType = "application/vnd.sqlite3"
            var MEDIA_TYPE_PNG : MediaType? = mimeType.toMediaTypeOrNull()
            if (mimeType == null) {
                Log.e("file error", "Not able to get mime type")
                return@Thread
            }
            val fileName: String = if (uploadedFileName == null)  sourceFile.name else uploadedFileName
            toggleProgressDialog(true)
            try {
                val requestBody: RequestBody =
                    MultipartBody.Builder().setType(MultipartBody.FORM)
                        .addFormDataPart("uploaded_file", fileName,sourceFile.asRequestBody())
                        .build()

                val request: Request = Request.Builder().url(serverURL).post(requestBody).build()

                val response: Response = client.newCall(request).execute()

                if (response.isSuccessful) {
                    Log.d("File upload","success, path: $serverUploadDirectoryPath$fileName" + "\n" +  uploadedFileName)
                    showToast("File uploaded successfully at $serverUploadDirectoryPath$fileName")
                } else {
                    Log.e("File upload", "failed")
                    showToast("File uploading failed (A) : " + uploadedFileName)
                }
            } catch (ex: Exception) {
                ex.printStackTrace()
                Log.e("File upload failed", ex.toString())
                showToast("File uploading failed (B) : " + uploadedFileName)
            }
            toggleProgressDialog(false)
        }.start()
    }

    // url = file path or whatever suitable URL you want.
    fun getMimeType(file: File): String? {
        var type: String? = null
        val extension = MimeTypeMap.getFileExtensionFromUrl(file.path)
        Log.e("extension", extension)
        if (extension != null) {
            type = MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
            Log.e("TYPE", type)
        }
        return type
    }

    fun showToast(message: String) {
        activity.runOnUiThread {
            Toast.makeText( activity, message, Toast.LENGTH_LONG ).show()
        }
    }

    fun toggleProgressDialog(show: Boolean) {
        activity.runOnUiThread {
            if (show) {
                dialog = ProgressDialog.show(activity, "", "Uploading file...", true);
            } else {
                dialog?.dismiss();
            }
        }
    }

}