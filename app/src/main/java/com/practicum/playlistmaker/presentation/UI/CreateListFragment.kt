package com.practicum.playlistmaker.presentation.UI

import android.Manifest
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.markodevcic.peko.PermissionRequester
import com.markodevcic.peko.PermissionResult
import com.practicum.playlistmaker.R
import com.practicum.playlistmaker.databinding.PlayListCreateFragmentBinding
import com.practicum.playlistmaker.presentation.UI.Media.FavoritesMediaFragment
import com.practicum.playlistmaker.presentation.ViewModels.CreateListFragmentViewModel
import com.practicum.playlistmaker.presentation.models.CreateListModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.io.File
import java.io.FileOutputStream


class CreateListFragment(): Fragment() {

    private var binding: PlayListCreateFragmentBinding? = null
    private val viewModel: CreateListFragmentViewModel by viewModel()

    lateinit private var pickMedia: ActivityResultLauncher<PickVisualMediaRequest>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

         super.onCreateView(inflater, container, savedInstanceState)

        binding = PlayListCreateFragmentBinding.inflate(inflater, container, false)
        return binding!!.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val requester = PermissionRequester.instance()

        super.onViewCreated(view, savedInstanceState)

        viewModel.getCreateListModel().observe(viewLifecycleOwner) {

            when (it) {
                is CreateListModel.statCreateList -> setVisibility(it)
            }
        }

        binding!!.buttonCreate.setOnClickListener {

            viewModel.createPlayList(binding!!.etName.editText?.text.toString(), binding!!.etDesc.editText?.text.toString())
            Toast.makeText(requireContext(), "Плейлист " + binding!!.etName.editText?.text.toString() + " создан.", Toast.LENGTH_SHORT).show()
            findNavController().navigateUp()
        }

        val simpleTextWatcherName = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                viewModel.userChangeText(s.toString())
            }

            override fun afterTextChanged(s: Editable?) {
            }


        }

        binding!!.etName.editText?.addTextChangedListener(simpleTextWatcherName)

        binding!!.ivImageAlbum.setOnClickListener {

            /////////////
            lifecycleScope.launch {
                requester.request(
                    Manifest.permission.READ_MEDIA_IMAGES
                ).collect { result ->
                    when (result) {
                        is PermissionResult.Granted ->
                            pickMedia.launch(PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly))

                        is PermissionResult.Denied ->
                           TODO()
                        is PermissionResult.Denied.NeedsRationale ->
                            TODO()
                        is PermissionResult.Denied.DeniedPermanently ->
                            TODO()
                        is PermissionResult.Cancelled ->
                            TODO()
                    }
                }
            }
            ///////////////


        }

        /////////////
        pickMedia =
            registerForActivityResult(ActivityResultContracts.PickVisualMedia()) { uri ->
                if (uri != null) {

                    val rnds = (1000..9000).random()
                    val nameImage = "image_" + rnds.toString() + ".jpg"

                    /////////
                    val filePath = File(
                        requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        getString(R.string.list_image_name)
                    )
                    if (!filePath.exists()) {
                        filePath.mkdirs()
                    }
                    val file = File(filePath, nameImage)

                    val inputStream = requireContext().contentResolver.openInputStream(uri)

                    val outputStream = FileOutputStream(file)

                    BitmapFactory
                        .decodeStream(inputStream)
                        .compress(Bitmap.CompressFormat.JPEG, 70, outputStream)
                    /////////
                    val filePathRead = File(
                        requireActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES),
                        getString(R.string.list_image_name)
                    )
                    val fileRead = File(filePathRead, nameImage)

                    viewModel.loadAlbumImage(fileRead.toUri(), binding!!.etName.editText?.text.toString())

                    ///////
                }
            }

        //////////////

        requireActivity().onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {

                dialogShow()
            }
        })

        binding!!.ivPlBack.setOnClickListener {
            dialogShow()
        }

    }
    fun setVisibility(createListModel: CreateListModel.statCreateList){

        Glide.with(this).load(createListModel.image).placeholder(R.drawable.add_photo)
            .centerCrop().transform(RoundedCorners(8)).into(binding!!.ivImageAlbum)

        binding!!.buttonCreate.isEnabled = createListModel.buttonCreateActive

    }

    fun isUserData(): Boolean{

        if (viewModel.isImageLoad() || binding!!.etName.editText?.text.toString().isNotEmpty() || binding!!.etDesc.editText?.text.toString().isNotEmpty()){
            return true
        }
        return false
    }

    fun dialogShow(){

        if (isUserData()) {

            MaterialAlertDialogBuilder(requireContext())
                .setTitle(getString(R.string.dialog_title))
                .setMessage(getString(R.string.dialog_text))
                .setNegativeButton(getString(R.string.dialog_cancel)) { dialog, which ->
                }
                .setPositiveButton(getString(R.string.dialog_close)) { dialog, which ->
                    findNavController().navigateUp()
                }
                .show()

        } else{
            findNavController().navigateUp()
        }
    }

    companion object {

        fun newInstance() = CreateListFragment()
    }
}