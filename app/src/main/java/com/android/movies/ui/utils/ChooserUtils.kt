package com.android.movies.ui.utils

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Parcelable

/**
 * Allows to choose a document from the device external storage
 *
 * @see ManagePermissions
 */
class ChooserUtils{

    companion object {
        private val MS_WORD_EXTENSION = "application/msword"
        private val PDF_EXTENSION = "application/pdf"

        private val PACKAGE_DOCUMENTS_UI = "com.android.documentsui"
        private val PACKAGE_FILE_EXPLORER = "com.android.fileexplorer"

        fun getDocumentChooser(packageManager: PackageManager, title: String): Intent {
            val targets = ArrayList<Intent>()
            val intent = Intent()
            intent.action = Intent.ACTION_GET_CONTENT
            intent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
            intent.setType("*/*");
            var mimeTypes = arrayOf(MS_WORD_EXTENSION, PDF_EXTENSION)
            intent.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            val candidates = packageManager.queryIntentActivities(intent, 0)
            for (candidate in candidates) {
                val packageName = candidate.activityInfo.packageName
                if (packageName == PACKAGE_DOCUMENTS_UI || packageName == PACKAGE_FILE_EXPLORER) {
                    val target = Intent()
                    var mimeTypes = arrayOf(MS_WORD_EXTENSION, PDF_EXTENSION)
                    target.type = "*/*"
                    target.action = Intent.ACTION_GET_CONTENT
                    target.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
                    target.`package` = packageName
                    target.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
                    targets.add(target)
                }
            }
            val chooser = Intent.createChooser(targets.removeAt(0), title)
            return chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, targets
                    .toTypedArray<Parcelable>())
        }
    }
}