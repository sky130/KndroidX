package kndroidx.databinding.recycler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import kndroidx.databinding.recycler.BaseBindingAdapter.OnItemClickListener

class ItemBindingAdapter :
    RecyclerView.Adapter<BindingViewHolder<Item, ViewDataBinding>>() {

    var itemClickListener: OnItemClickListener<Any>? = null
    var itemLongClickListener: OnItemClickListener<Any>? = null
    var itemEventHandler: Any? = null
    var itemHelper: Any? = null
    var viewModel: Any? = null
    var activity: Any? = null
    var fragment: Any? = null
    var item: List<Item> = emptyList()
        set(data) {
            DiffUtil
                .calculateDiff(CommonDiffCallback(field, data))
                .dispatchUpdatesTo(this)
            field = data
        }
    val layoutItemType = mutableListOf<Int>()
    val dataItemType = mutableListOf<Int>()

    fun getItem(position: Int): Item? {
        return item.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<Item, ViewDataBinding> {
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            viewType,
            parent,
            false
        )
        val holder = BindingViewHolder<Item, ViewDataBinding>(binding)
        with(holder) {
            setBinding()
            setActivity(activity)
            setViewModel(viewModel)
            setItemEventHandler(itemEventHandler)
            setFragment(fragment)
            setItemHelper(itemHelper)
        }
        if (viewType in dataItemType) {
            bindClick(holder, binding)
        }
        return holder
    }

    fun bindClick(holder: BindingViewHolder<*, *>, binding: ViewDataBinding) {
        fun getItem(): Any? {
            val position = holder.layoutPosition
            return getItem(position).let {
                if (it is Item.Data<*>)
                    it.data as Any
                else
                    null
            }
        }
        itemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                getItem()?.let {
                    listener.onItemClick(it, holder.layoutPosition)
                }
            }
        }
        itemLongClickListener?.let { listener ->
            binding.root.setOnLongClickListener {
                getItem()?.let {
                    listener.onItemClick(it, holder.layoutPosition)
                }
                return@setOnLongClickListener true
            }
        }
    }


    override fun getItemViewType(position: Int): Int {
        return item[position].apply {
            when (this) {
                is Item.Layout -> layoutItemType.add(layout)
                is Item.Data<*> -> {
                    if (isClickable) {
                        dataItemType.add(layout)
                    } else {
                        layoutItemType.add(layout)
                    }
                }
            }
        }.layout
    }

    override fun onBindViewHolder(
        holder: BindingViewHolder<Item, ViewDataBinding>,
        position: Int,
        payloads: List<Any?>
    ) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
        }
        super.onBindViewHolder(holder, position, payloads)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<Item, ViewDataBinding>, position: Int) {
        with(holder) {
            with(getItem(position)) {
                if (this !is Item.Data<*>) return
                forceBind(data)
            }
            setItemPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return item.size
    }

}
