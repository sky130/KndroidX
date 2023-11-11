package kndroidx.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


abstract class BaseViewFragment<VB : ViewBinding> : Fragment() {
    open val binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        beforeSetContentView()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        initView()
        initData()
        initAdapter()
        initListener()
    }

    open fun beforeSetContentView() {}

    open fun initAdapter() {}

    open fun initListener() {}

    open fun initView() {}

    open fun initData() {}

    open fun init() {}

    @Suppress("UNCHECKED_CAST")
    private fun <VB : ViewBinding> Any.getViewBinding(inflater: LayoutInflater): VB {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
        val inflate = vbClass[0].getDeclaredMethod("inflate", LayoutInflater::class.java)
        return inflate.invoke(null, inflater) as VB
    }


}