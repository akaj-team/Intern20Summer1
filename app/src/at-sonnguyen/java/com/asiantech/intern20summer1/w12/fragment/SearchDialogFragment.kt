package com.asiantech.intern20summer1.w12.fragment

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.w12.data.model.User
import com.asiantech.intern20summer1.w12.ui.home.HomeFragment
import kotlinx.android.synthetic.`at-sonnguyen`.w12_fragment_search_dialog.*

class SearchDialogFragment : DialogFragment() {

    private var user = User(0,"","","")

    companion object {
        internal fun newInstance(user: User): SearchDialogFragment {
            val dialogFragment = SearchDialogFragment()
            val bundle = Bundle()
            bundle.putSerializable(HomeFragment.USER_KEY, user)
            dialogFragment.arguments = bundle
            return dialogFragment
        }
    }

    private fun getData(){
        (arguments?.getSerializable(HomeFragment.USER_KEY) as? User)?.let {
            user.id = it.id
            user.email = it.email
            user.full_name = it.full_name
            user.token = it.token
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getData()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.w12_fragment_search_dialog, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleClickingOk()
    }

    private fun initView() {
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    private fun handleClickingOk() {
        imgSearch?.setOnClickListener {
//            var string = edtSearch.text.toString()
//            if (string.isEmpty()) {
//                Toast.makeText(requireContext(), "Enter a message", Toast.LENGTH_SHORT).show()
//            } else {
//                sendResult(string)
//            }
            dialog?.dismiss()
        }
    }

    private fun sendResult(string: String) {
//        if (targetFragment == null){
//            Toast.makeText(requireContext()," no thing",Toast.LENGTH_SHORT).show()
//        } else{
//            val intent : Intent? = HomeFragment.newInstance(user).newIntent(string)
//            targetFragment.onActivityResult(targetRequestCode,RESULT_OK,intent)
//            dismiss()
//        }
    }
}
