package com.ssafy.presentation.ui.study

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.ssafy.presentation.R
import com.ssafy.presentation.base.BaseFragment
import com.ssafy.presentation.databinding.FragmentStudyBinding
import com.ssafy.presentation.ui.study.adapter.StudyRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class StudyFragment : BaseFragment<FragmentStudyBinding>(
    { FragmentStudyBinding.bind(it) }, R.layout.fragment_study
), StudyRecyclerAdapter.StudyTypeClickListener {
    private val studyRecyclerAdapter by lazy { StudyRecyclerAdapter(this) }
    private val viewModel by viewModels<StudyViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initObserve()
        initEvent()
    }

    private fun initObserve() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.getStudyList().collect {
                    studyRecyclerAdapter.submitData(it)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            viewModel.refreshEvent.collect {
                if (it) {
                    studyRecyclerAdapter.refresh()
                }
            }
        }
    }

    private fun initView() {
        with(binding) {
            rvStudyList.apply {
                adapter = studyRecyclerAdapter
                itemTouch()
                setHasFixedSize(true)
            }
        }
    }

    private fun initEvent() {
        with(binding) {
            tvMusicAdd.setOnClickListener {
                findNavController().navigate(R.id.action_studyFragment_to_musicSearchFragment)
            }
        }
    }

    private fun itemTouch() {
        val itemTouchCallback = object : ItemTouchHelper.SimpleCallback(
            ItemTouchHelper.UP or ItemTouchHelper.DOWN, ItemTouchHelper.LEFT
        ) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.bindingAdapterPosition
                val item = studyRecyclerAdapter.peek(position)
                if (item != null) {
                    viewModel.deleteStudyList(item.id)
                }
            }
        }

        ItemTouchHelper(itemTouchCallback).attachToRecyclerView(binding.rvStudyList)
    }

    override fun fillStudy(id: String) {
        findNavController().navigate(StudyFragmentDirections.actionStudyFragmentToFillFragment(id))
    }

    override fun pickStudy(id: String) {
        findNavController().navigate(StudyFragmentDirections.actionStudyFragmentToShuffleFragment(id))
    }

    override fun expressStudy(id: String) {
        findNavController().navigate(StudyFragmentDirections.actionStudyFragmentToExpressFragment(id))
    }

    override fun pronounceStudy(id: String) {
        findNavController().navigate(StudyFragmentDirections.actionStudyFragmentToPronounce(id))
    }
}