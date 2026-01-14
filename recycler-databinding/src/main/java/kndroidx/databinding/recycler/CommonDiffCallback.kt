package kndroidx.databinding.recycler

import androidx.recyclerview.widget.DiffUtil


class CommonDiffCallback<T>(
    override val oldList: List<T>,
    override val newList: List<T>
) : BaseDiffCallback<T>(){
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return super.areContentsTheSame(oldItemPosition, newItemPosition)
    }


}