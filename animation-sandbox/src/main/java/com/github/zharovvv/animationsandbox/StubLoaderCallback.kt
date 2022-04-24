package com.github.zharovvv.animationsandbox

import android.content.Context
import android.os.Bundle
import android.widget.Toast
import androidx.loader.app.LoaderManager
import androidx.loader.content.Loader

class StubLoaderCallback(private val context: Context) : LoaderManager.LoaderCallbacks<Int> {

    override fun onCreateLoader(id: Int, args: Bundle?): Loader<Int> {
        if (id == R.id.stub_loader_id) {
            return StubLoader(context)
        }
        throw NoSuchElementException("Not found loader implementation for id: $id")
    }

    override fun onLoadFinished(loader: Loader<Int>, data: Int?) {
        if (loader.id == R.id.stub_loader_id) {
            Toast.makeText(context, "Load Finished", Toast.LENGTH_SHORT)
                .show()
        }
    }

    override fun onLoaderReset(loader: Loader<Int>) {
        //do Nothing
    }
}