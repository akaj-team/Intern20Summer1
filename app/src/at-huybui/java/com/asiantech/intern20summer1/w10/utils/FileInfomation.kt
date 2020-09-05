package com.asiantech.intern20summer1.w10.utils

import android.content.ContentUris
import android.content.Context
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.DocumentsContract
import android.provider.MediaStore
import android.provider.OpenableColumns
import java.io.File

internal class FileInformation {

    companion object {
        private const val CONTENT_URI = "content://downloads/public_downloads"
    }

    fun getFile(context: Context, uri: Uri): File {
        return File(getPath(context, uri).toString())
    }

    /**
     * Get a file path from a Uri. This will get the the path for Storage Access
     * Framework Documents, as well as the _data field for the MediaStore and
     * other file-based ContentProviders.
     *
     * @param context The context.
     */
    fun getPath(context: Context, uri: Uri): String? {
        var pathReturn: String? = "unknown"

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT
            && DocumentsContract.isDocumentUri(context, uri)
        ) {
            getPathE(context, uri)
        } else if ("content".equals(uri.scheme, ignoreCase = true)) {
            pathReturn = getDataColumn(context, uri, null, null)
        } else if ("file".equals(uri.scheme, ignoreCase = true)) {
            pathReturn = uri.path
        }
        return pathReturn
    }

    private fun getPathE(context: Context, uri: Uri): String? {
        var pathReturn: String? = "unknown"
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (isExternalStorageDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                if ("primary".equals(type, ignoreCase = true)) {
                    @Suppress("DEPRECATION")
                    pathReturn = "${Environment.getExternalStorageDirectory()}/${split[1]}"
                }
            } else if (isDownloadsDocument(uri)) {
                val contentUri = ContentUris.withAppendedId(
                    Uri.parse(CONTENT_URI),
                    java.lang.Long.valueOf(DocumentsContract.getDocumentId(uri))
                )
                pathReturn = getDataColumn(context, contentUri, null, null)
            } else if (isMediaDocument(uri)) {
                val docId = DocumentsContract.getDocumentId(uri)
                val split = docId.split(":".toRegex()).toTypedArray()
                val type = split[0]
                var contentUri: Uri? = null
                when (type) {
                    "image" -> {
                        contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    }
                    "video" -> {
                        contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI
                    }
                    "audio" -> {
                        contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI
                    }
                }
                pathReturn = getDataColumn(context, contentUri, "_id=?", arrayOf(split[1]))
            }
        }
        return pathReturn
    }

    fun getName(context: Context, uri: Uri?): String? {
        var fileName: String? = null
        try {
            val cursor = context.contentResolver.query(uri!!, null, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {
                // get file name
                fileName = cursor.getString(
                    cursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
                )
            }
            cursor?.close()
        } catch (ae: ArithmeticException) {
            ae.printStackTrace()
        } catch (ne: NumberFormatException) {
            ne.printStackTrace()
        } catch (ea: IllegalArgumentException) {
            ea.printStackTrace()
        }
        return fileName
    }

    fun getSize(context: Context, uri: Uri?): String? {
        var fileSize: String? = null
        try {
            val cursor = context.contentResolver
                .query(uri!!, null, null, null, null, null)
            if (cursor != null && cursor.moveToFirst()) {

                // get file size
                val sizeIndex = cursor.getColumnIndex(OpenableColumns.SIZE)
                if (!cursor.isNull(sizeIndex)) {
                    fileSize = cursor.getString(sizeIndex)
                }
            }
            cursor?.close()
        } catch (ae: ArithmeticException) {
            ae.printStackTrace()
        } catch (ne: NumberFormatException) {
            ne.printStackTrace()
        } catch (ea: IllegalArgumentException) {
            ea.printStackTrace()
        }
        return fileSize
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private fun getDataColumn(
        context: Context, uri: Uri?, selection: String?,
        selectionArgs: Array<String>?
    ): String? {
        try {
            val column = "_data"
            val projection = arrayOf(column)
            var cursor: Cursor? = null
            cursor = context.contentResolver.query(
                uri!!, projection, selection, selectionArgs,
                null
            )
            if (cursor != null && cursor.moveToFirst()) {
                val columnIndex = cursor.getColumnIndexOrThrow(column)
                return cursor.getString(columnIndex)
            }
            cursor?.close()
        } catch (ae: ArithmeticException) {
            ae.printStackTrace()
        } catch (ne: NumberFormatException) {
            ne.printStackTrace()
        } catch (ea: IllegalArgumentException) {
            ea.printStackTrace()
        }
        return null
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private fun isExternalStorageDocument(uri: Uri): Boolean {
        return "com.android.externalstorage.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private fun isDownloadsDocument(uri: Uri): Boolean {
        return "com.android.providers.downloads.documents" == uri.authority
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private fun isMediaDocument(uri: Uri): Boolean {
        return "com.android.providers.media.documents" == uri.authority
    }
}
