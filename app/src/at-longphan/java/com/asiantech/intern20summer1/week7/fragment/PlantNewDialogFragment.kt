package com.asiantech.intern20summer1.week7.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R

class PlantNewDialogFragment : DialogFragment() {

    companion object {
        internal fun newInstance() = PlantNewDialogFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_new_dialog, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun handleButtonBackListener() {
        /*imgBackIconDialog.setOnClickListener {
            dialog?.dismiss()
        }*/
    }
}
