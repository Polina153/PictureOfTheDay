package com.example.pictureoftheday.ui.picture

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import coil.api.load
import com.example.pictureoftheday.MainActivity
import com.example.pictureoftheday.R
import com.example.pictureoftheday.databinding.FragmentPictureOfTheDayBinding
import com.example.pictureoftheday.ui.api.ApiActivity
import com.example.pictureoftheday.ui.apibottom.ApiBottomActivity
import com.example.pictureoftheday.ui.settings.SettingsFragment
import com.google.android.material.bottomappbar.BottomAppBar
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.textfield.TextInputLayout

class PictureOfTheDayFragment : Fragment() {

    private var _binding: FragmentPictureOfTheDayBinding? = null
    private val binding get() = _binding!!

    private lateinit var title: TextView
    private lateinit var description: TextView
    private lateinit var inputLayout: TextInputLayout
    private lateinit var inputEditText: EditText
    private lateinit var fab: FloatingActionButton

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<ConstraintLayout>

    //Ленивая инициализация модели
    private val viewModel: PictureOfTheDayViewModel by lazy {
        ViewModelProviders.of(this)[PictureOfTheDayViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //return inflater.inflate(R.layout.fragment_picture_of_the_day, container, false)
        viewModel.getData()
            .observe(viewLifecycleOwner) { renderData(it) }

        _binding = FragmentPictureOfTheDayBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init();

        inputLayout.setEndIconOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("https://en.wikipedia.org/wiki/${inputEditText.text.toString()}")
            })
        }
        setBottomSheetBehavior(view.findViewById(R.id.bottom_sheet_container))
        bottomSheetBehavior.addBottomSheetCallback(object :
            BottomSheetBehavior.BottomSheetCallback() {
            override fun onStateChanged(bottomSheet: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        description.isScrollContainer = false
                    }

                    BottomSheetBehavior.STATE_EXPANDED -> {
                        description.isScrollContainer = true
                        description.movementMethod = ScrollingMovementMethod()
                    }
                    /* BottomSheetBehavior.STATE_DRAGGING -> TODO("not implemented")
                     BottomSheetBehavior.STATE_COLLAPSED -> TODO("not implemented")
                     BottomSheetBehavior.STATE_EXPANDED -> TODO("not implemented")
                     BottomSheetBehavior.STATE_HALF_EXPANDED -> TODO("not implemented")
                     BottomSheetBehavior.STATE_HIDDEN -> TODO("not implemented")
                     BottomSheetBehavior.STATE_SETTLING -> TODO("not implemented")*/
                }
            }

            override fun onSlide(bottomSheet: View, slideOffset: Float) {
                // TODO("not implemented")
            }
        })
        setBottomAppBar(view)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.menu_bottom_bar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.app_bar_fav -> activity?.let { startActivity(Intent(it, ApiActivity::class.java)) }
            R.id.app_bar_settings -> activity?.let { startActivity(
                Intent(
                    it,
                    ApiBottomActivity::class.java
                )
            ) }

            android.R.id.home -> {
                activity?.let {
                    BottomNavigationDrawerFragment().show(it.supportFragmentManager, "tag")
                }
            }
            android.R.id.title -> {
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun navigateToSettingsFragment() {
        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container, SettingsFragment())
            .addToBackStack(null)
            .commit()
    }

    private fun setBottomAppBar(view: View) {
        val context = activity as MainActivity
        context.setSupportActionBar(view.findViewById(R.id.bottom_app_bar))
        setHasOptionsMenu(true)
        binding.fab.setOnClickListener {
            if (isMain) {
                isMain = false
                binding.bottomAppBar.navigationIcon = null
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_END
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_back_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar_other_screen)
                navigateToSettingsFragment()
            } else {
                isMain = true
                binding.bottomAppBar.navigationIcon =
                    ContextCompat.getDrawable(context, R.drawable.ic_hamburger_menu_bottom_bar)
                binding.bottomAppBar.fabAlignmentMode = BottomAppBar.FAB_ALIGNMENT_MODE_CENTER
                binding.fab.setImageDrawable(
                    ContextCompat.getDrawable(
                        context,
                        R.drawable.ic_plus_fab
                    )
                )
                binding.bottomAppBar.replaceMenu(R.menu.menu_bottom_bar)
            }
        }
    }



    private fun init() {
        title = requireView().findViewById(R.id.bottom_sheet_description_header)
        description = requireView().findViewById(R.id.bottom_sheet_description)
        inputLayout = requireView().findViewById(R.id.input_layout)
        inputEditText = requireView().findViewById(R.id.input_edit_text)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel.getData()
            .observe(viewLifecycleOwner, Observer<PictureOfTheDayData> { renderData(it) })
    }

    private fun setBottomSheetBehavior(bottomSheet: ConstraintLayout) {
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
    }

    private fun renderData(data: PictureOfTheDayData) {
        when (data) {
            is PictureOfTheDayData.Success -> {
                val serverResponseData = data.serverResponseData
                val url = serverResponseData.url
                if (url.isNullOrEmpty()) {
                    //Отобразите ошибку
                    //showError("Сообщение, что ссылка пустая")
                } else {
                    val imageView: ImageView = requireView().findViewById(R.id.image_view)
                    imageView.load(url) {
                        lifecycle(this@PictureOfTheDayFragment)
                        error(R.drawable.ic_load_error_vector)
                        placeholder(R.drawable.ic_no_photo_vector)
                    }
                    title.text = serverResponseData.title
                    description.text = serverResponseData.explanation
                    //Отобразите фото
                    //showSuccess()
                    //Coil в работе: достаточно вызвать у нашего ImageView
                    //нужную extension-функцию и передать ссылку и заглушки для placeholder

                }
            }

            is PictureOfTheDayData.Loading -> {
                //Отобразите загрузку
                //showLoading()
            }

            is PictureOfTheDayData.Error -> {
            }
        }
    }

    companion object {
        fun newInstance() = PictureOfTheDayFragment()
        private var isMain = true
    }
}