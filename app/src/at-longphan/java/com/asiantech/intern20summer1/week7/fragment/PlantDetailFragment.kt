package com.asiantech.intern20summer1.week7.fragment

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.asiantech.intern20summer1.R
import com.asiantech.intern20summer1.week7.PlantRoomDatabase
import com.asiantech.intern20summer1.week7.dto.PlantAndCultivation
import com.asiantech.intern20summer1.week7.other.DATETIME_FORMAT
import com.asiantech.intern20summer1.week7.other.ModeGarden
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.`at-longphan`.fragment_plant_detail.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class PlantDetailFragment : Fragment() {

    private var cultivationId: Int? = null
    private var database: PlantRoomDatabase? = null
    private var plantAndCultivation: PlantAndCultivation? = null

    internal var onDelete: () -> Unit = {}

    companion object {

        private const val ID_KEY = "cultivationId"
        fun newInstance(cultivationId: Int) = PlantDetailFragment().apply {
            arguments = Bundle().apply {
                putInt(ID_KEY, cultivationId)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        cultivationId = arguments?.getInt(ID_KEY)
        database = PlantRoomDatabase.getDatabase(requireContext())
        plantAndCultivation = cultivationId?.let { database?.cultivationDao()?.getById(it) }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_plant_detail, container, false)
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        handleListeners()
    }

    @SuppressLint("ResourceType")
    private fun initView() {
        if (plantAndCultivation != null) {
            turnOnViews()

            plantAndCultivation?.let {
                imgDisplayPlantDetail?.let { imageView ->
                    Glide.with(imageView).load(it.imageUrl).into(imageView)
                }

                var plantMode = it.getMode()
                if (plantMode == "") {
                    plantMode = it.name
                }
                tvPlantMainDescription?.text = plantMode
                when (plantMode) {
                    ModeGarden.WORMED -> tvPlantMainDescription?.setTextColor(
                        Color.parseColor(
                            resources.getString(R.color.colorTextViewWormedModeWeek7)
                        )
                    )
                    ModeGarden.DEHYDRATED -> tvPlantMainDescription?.setTextColor(
                        Color.parseColor(
                            resources.getString(R.color.colorTextViewDehydratedModeWeek7)
                        )
                    )
                    ModeGarden.ABOUT_TO_HARVEST -> tvPlantMainDescription?.setTextColor(
                        Color.parseColor(
                            resources.getString(R.color.colorTextViewAboutToHarvestModeWeek7)
                        )
                    )
                }

                tvPlantAtDetail?.text = it.dateCultivation
                tvLastWaterDetail?.text = it.dateWatering
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    tvPlantDescription?.text =
                        Html.fromHtml(it.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    tvPlantDescription?.text = Html.fromHtml(it.description)
                }
            }
        } else {
            turnOffViews()
        }
    }

    private fun turnOnViews() {
        nestedScrollViewFragmentPlantDetail?.visibility = View.VISIBLE
        rlButtonsFragmentPlantDetail?.visibility = View.VISIBLE
        rlNoLongerExistPlant?.visibility = View.INVISIBLE
    }

    private fun turnOffViews() {
        nestedScrollViewFragmentPlantDetail?.visibility = View.INVISIBLE
        rlButtonsFragmentPlantDetail?.visibility = View.INVISIBLE
        rlNoLongerExistPlant?.visibility = View.VISIBLE
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleListeners() {
        handleButtonPlantWatering()
        handleButtonCutOff()
        handleImageButtonBack()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun handleButtonPlantWatering() {
        btnPlantWatering?.setOnClickListener {
            val timeNow = DateTimeFormatter
                .ofPattern(DATETIME_FORMAT)
                .format(LocalDateTime.now())
            cultivationId?.let { id ->
                database?.cultivationDao()?.updateDateWateringById(id, timeNow)
            }
            tvLastWaterDetail?.text = timeNow
            Toast.makeText(
                requireContext(),
                getString(R.string.toast_watering_plant_successfully_description),
                Toast.LENGTH_SHORT
            )
                .show()
        }
    }

    private fun handleImageButtonBack() {
        imgBackPlantDetail?.setOnClickListener {
            // TODO: 22/08/2020
            fragmentManager?.popBackStack()
        }
    }

    private fun handleButtonCutOff() {
        btnPlantCutOff?.setOnClickListener {
            showDialogConfirmPlantCutOff()
        }
    }

    private fun showDialogConfirmPlantCutOff() {
        val builder = AlertDialog.Builder(this.context)
            .setTitle(getString(R.string.confirm_action_dialog_title))
            .setPositiveButton(getString(R.string.confirm_action_dialog_positive)) { _: DialogInterface, _: Int ->
                cultivationId?.let { id -> database?.cultivationDao()?.deleteById(id) }
                Toast.makeText(
                    requireContext(),
                    getString(R.string.toast_cut_off_plant_successfully_description),
                    Toast.LENGTH_SHORT
                )
                    .show()
                // TODO: 22/08/2020
                //onResult(-1, null)
                onDelete.invoke()
                fragmentManager?.popBackStack()
            }
            .setNegativeButton(getString(R.string.confirm_action_dialog_negative)) { _: DialogInterface, _: Int -> }
        builder.show()
    }
}
