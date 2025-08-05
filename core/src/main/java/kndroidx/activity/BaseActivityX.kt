@file:Suppress("UNCHECKED_CAST")

package kndroidx.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import java.lang.reflect.ParameterizedType

abstract class BaseActivityX : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        beforeSetContent()
        setContentView(onCreateView())
        init()
    }

    open fun beforeSetContent() {}

    open fun init() {}

    abstract fun onCreateView(): View

    @Deprecated("")
    fun <VM : ViewModel> Any.createViewModel(position: Int = 0): VM {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VM>>()
        val viewModel = vbClass[position]
        return ViewModelProvider(this@BaseActivityX)[viewModel]
    }

    @Deprecated("")
    fun <VB : ViewBinding> Any.createViewBinding(
        layoutInflater: LayoutInflater = this@BaseActivityX.layoutInflater,
        position: Int = 0,
    ): VB {
        val vbClass =
            (javaClass.genericSuperclass as ParameterizedType).actualTypeArguments.filterIsInstance<Class<VB>>()
        val inflate = vbClass[position].getDeclaredMethod("inflate", LayoutInflater::class.java)
        return inflate.invoke(null, layoutInflater) as VB
    }
}