package com.asiantech.intern20summer1

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment : Fragment() {

    private var FRAGMENT_NAME = javaClass.simpleName
    private var TAG = FRAGMENT_NAME

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.i(TAG, "$FRAGMENT_NAME onAttach ")
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.i(TAG, "$FRAGMENT_NAME onCreate ")
    }

    override fun onStart() {
        super.onStart()
        Log.i(TAG, "$FRAGMENT_NAME onStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.i(TAG, "$FRAGMENT_NAME onResume ")
    }

    override fun onPause() {
        super.onPause()
        Log.i(TAG, "$FRAGMENT_NAME onPause ")
    }

    override fun onStop() {
        super.onStop()
        Log.i(TAG, "$FRAGMENT_NAME onStop ")
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.i(TAG, "$FRAGMENT_NAME onViewCreated ")
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        Log.i(TAG, "$FRAGMENT_NAME onViewStateRestored ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$FRAGMENT_NAME onDestroy ")

    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.i(TAG, "$FRAGMENT_NAME onDestroyView ")
    }
}
