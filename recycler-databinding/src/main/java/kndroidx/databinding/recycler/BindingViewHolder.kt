package kndroidx.databinding.recycler

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import kndroidx.recycler.databinding.BR

class BindingViewHolder<T, BINDING : ViewDataBinding>(val binding: BINDING)
    : RecyclerView.ViewHolder(binding.root){

    fun forceBind(t: Any?) {
        binding.setVariable(BR.item, t)
    }

    fun setBinding(){
        binding.setVariable(BR.binding, binding)
    }

    fun bind(t: T?) {
        binding.setVariable(BR.item, t)
    }

    fun setItemEventHandler(handler:Any?){
        binding.setVariable(BR.handler, handler)
    }

    fun setItemHelper(helper:Any?){
        binding.setVariable(BR.helper, helper)
    }

    fun setItemPosition(position:Int){
        binding.setVariable(BR.position, position)
    }

    fun setViewModel(viewModel:Any?){
        binding.setVariable(BR.viewModel, viewModel)
    }

    fun setActivity(activity:Any?){
        binding.setVariable(BR.activity, activity)
    }

}
