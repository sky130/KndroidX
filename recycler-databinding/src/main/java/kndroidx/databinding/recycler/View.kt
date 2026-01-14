package kndroidx.databinding.recycler

import android.view.View
import androidx.activity.ComponentActivity
import androidx.databinding.BindingAdapter
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LifecycleOwner

@BindingAdapter(value = ["lifecycleOwner", "binding"], requireAll = true)
fun View.bind(lifecycleOwner: LifecycleOwner, binding: ViewDataBinding) {
    try {
        binding.lifecycleOwner = lifecycleOwner
    } catch (_: Exception) {

    }
}