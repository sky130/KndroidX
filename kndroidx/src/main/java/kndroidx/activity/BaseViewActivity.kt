package kndroidx.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType


abstract class BaseViewActivity<VB : ViewBinding> : AppCompatActivity() {
    val binding: VB by lazy(mode = LazyThreadSafetyMode.NONE) {
        getViewBinding(layoutInflater)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContentView()
        setContentView(binding.root)
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