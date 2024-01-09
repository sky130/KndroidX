package kndroidx.fragment

import android.view.LayoutInflater
import android.view.View
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import androidx.viewbinding.ViewBinding

class ViewFragmentX<VB : ViewBinding, VM : ViewModel> : BaseFragmentX() {
    private var _binding: VB? = null
    val binding get() = _binding!!
    private var _viewModel: VM? = null
    val viewModel get() = _viewModel!!

    override fun onCreateView(inflater: LayoutInflater): View {
        _binding = createViewBinding(inflater)
        if (_binding is ViewDataBinding) {
            (_binding as ViewDataBinding).lifecycleOwner = viewLifecycleOwner
        }
        _viewModel = createViewModel()
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}