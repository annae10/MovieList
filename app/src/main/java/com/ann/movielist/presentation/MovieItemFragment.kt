package com.ann.movielist.presentation

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.ann.movielist.R
import com.ann.movielist.databinding.FragmentMovieItemBinding
import com.ann.movielist.domain.MovieItem


class MovieItemFragment : Fragment() {

    private lateinit var viewModel: MovieItemViewModel
    private lateinit var onEditingFinishedListener: OnEditingFinishedListener

    private var _binding: FragmentMovieItemBinding? = null
    private val binding: FragmentMovieItemBinding
        get() = _binding ?: throw RuntimeException("FragmentMovieItemBinding == null")

    private var screenMode: String = MODE_UNKNOWN
    private var movieItemId: Int = MovieItem.UNDEFINED_ID


    override fun onAttach(context: Context){
        super.onAttach(context)
        if (context is OnEditingFinishedListener){
            onEditingFinishedListener = context
        } else {
            throw RuntimeException("Activity must implement OnEditingFinishedListener")
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMovieItemBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MovieItemViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        addTextChangeListeners()
        launchRightMode()
        observeViewModel()
    }


    private fun observeViewModel(){
        viewModel.shouldCloseScreen.observe(viewLifecycleOwner){
            onEditingFinishedListener.onEditingFinished()
        }
    }

    private fun launchRightMode(){
        when(screenMode){
            MODE_EDIT -> launchEditMode()
            MODE_ADD -> launchAddMode()
        }
    }

    private fun addTextChangeListeners(){
        binding.etTitle.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){

            }
            override fun onTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
                viewModel.resetErrorInputTitle()
            }
            override fun afterTextChanged(s: Editable?){

            }
        })
    }

    private fun launchEditMode(){

        viewModel.getMovieItem(movieItemId)
        binding.saveButton.setOnClickListener{
            viewModel.editMovieItem(
                binding.etTitle.text?.toString())
        }
    }

    private fun launchAddMode(){

        binding.saveButton.setOnClickListener {
            viewModel.addMovieItem(
                binding.etTitle.text?.toString()
            )
        }
    }

    private fun parseParams(){
        val args = requireArguments()
        if (!args.containsKey(SCREEN_MODE)){
            throw RuntimeException("Param screen mode is absent")
        }
        val mode = args.getString(SCREEN_MODE)
        if(mode != MODE_EDIT && mode != MODE_ADD){
            throw RuntimeException("Unknown screen mode $mode")
        }
        screenMode = mode
        if(screenMode == MODE_EDIT){
            if(!args.containsKey((MOVIE_ITEM_ID))){
                throw RuntimeException("Param shop item id is absent")
            }
            movieItemId = args.getInt(MOVIE_ITEM_ID, MovieItem.UNDEFINED_ID)
        }
    }


    interface OnEditingFinishedListener {

        fun onEditingFinished()
    }

    companion object {

        private const val SCREEN_MODE = "extra_mode"
        private const val MOVIE_ITEM_ID = "extra_movie_item_id"
        private const val MODE_EDIT = "mode_edit"
        private const val MODE_ADD = "mode_add"
        private const val MODE_UNKNOWN = ""


        fun newInstanceAddItem(): MovieItemFragment {
            return MovieItemFragment().apply{
                arguments = Bundle().apply{
                    putString(SCREEN_MODE, MODE_ADD)
                }
            }
        }

        fun newInstanceEditItem( taskItemId: Int): MovieItemFragment{
            return MovieItemFragment().apply {
                arguments = Bundle().apply {
                    putString(SCREEN_MODE, MODE_EDIT)
                    putInt(MOVIE_ITEM_ID, movieItemId)
                }
            }
        }

    }
}