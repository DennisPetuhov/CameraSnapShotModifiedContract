package com.example.camerasnapshotmodifiedcontract

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.MediaStore
import androidx.activity.result.contract.ActivityResultContract
import androidx.annotation.CallSuper

class ModifyedContractCamera:ActivityResultContract<Uri,Pair<Boolean,Uri>>() {


    private lateinit var myImageUri: Uri
    @CallSuper // любые перопределяющие методы должны переопределять этот
    override fun createIntent(context: Context, input: Uri): Intent {

        myImageUri = input
        return Intent(MediaStore.ACTION_IMAGE_CAPTURE).putExtra(MediaStore.EXTRA_OUTPUT, input)
    }
    @Suppress("AutoBoxing") //Подавляет данные предупреждения компиляции в аннотированном элементе
    override fun parseResult(resultCode: Int, intent: Intent?): Pair<Boolean, Uri> {
        return (resultCode == Activity.RESULT_OK) to myImageUri
    }

    override fun getSynchronousResult(
        context: Context,
        input: Uri
    ): SynchronousResult<Pair<Boolean, Uri>>? {
        return null
    }

}