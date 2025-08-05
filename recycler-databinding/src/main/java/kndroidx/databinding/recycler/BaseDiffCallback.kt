package kndroidx.databinding.recycler

import androidx.recyclerview.widget.DiffUtil

abstract class BaseDiffCallback<T> : DiffUtil.Callback() {
    abstract val oldList: List<T>
    abstract val newList: List<T>

    override fun getOldListSize() = oldList.size
    override fun getNewListSize() = newList.size
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldList[oldItemPosition] == newList[newItemPosition]
}