@file:Suppress("UNCHECKED_CAST")

package kndroidx.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseFragmentX : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        beforeSetContent()
        return onCreateView(inflater)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
    }

    open fun init() {}

    open fun beforeSetContent() {}

    abstract fun onCreateView(inflater: LayoutInflater): View

    fun <VM : ViewModel> createViewModel(position: Int = 0): VM {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VM>>()
        val viewModel = vbClass[position]
        return ViewModelProvider(requireActivity())[viewModel]
    }

    fun <VB : ViewBinding> createViewBinding(
        layoutInflater: LayoutInflater,
        position: Int = 0,
    ): VB {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
        val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
        return inflate.invoke(null, layoutInflater) as VB
    }
}