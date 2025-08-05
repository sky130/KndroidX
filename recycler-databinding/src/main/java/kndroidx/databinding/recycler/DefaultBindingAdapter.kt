package kndroidx.databinding.recycler

import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding

class DefaultBindingAdapter(@param:LayoutRes @field:LayoutRes override val layoutRes: Int)
    : BaseBindingAdapter<Any, ViewDataBinding>()
