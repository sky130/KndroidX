package kndroidx.databinding.recycler

import androidx.recyclerview.widget.DiffUtil


class CommonDiffCallback<T>(
    override val oldList: List<T>,
    override val newList: List<T>
) : BaseDiffCallback<T>()