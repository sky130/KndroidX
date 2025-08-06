package kndroidx.databinding.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import kndroidx.databinding.recycler.BaseBindingAdapter.OnItemClickListener

class ItemBindingAdapter :
    RecyclerView.Adapter<BindingViewHolder<Item, ViewDataBinding>>() {

    var itemClickListener: OnItemClickListener<Any>? = null
    var itemLongClickListener: OnItemClickListener<Any>? = null
    var itemEventHandler: Any? = null
    var itemHelper: Any? = null
    var viewModel: Any? = null
    var activity: Any? = null
    var item: List<Item>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(data) {
            DiffUtil
                .calculateDiff(CommonDiffCallback<Item>(field ?: emptyList(), data ?: emptyList()))
                .dispatchUpdatesTo(this)
            field = data
        }

    fun getItem(position: Int): Item? {
        return item?.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<Item, ViewDataBinding> {
        val layout = viewType
        val binding = DataBindingUtil.inflate<ViewDataBinding>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        val holder = BindingViewHolder<Item, ViewDataBinding>(binding)
        with(holder) {
            setBinding()
            setActivity(activity)
            setViewModel(viewModel)
            setItemEventHandler(itemEventHandler)
            setItemHelper(itemHelper)
        }
        bindClick(holder, binding)
        return holder
    }

    override fun getItemViewType(position: Int): Int {
        return item!![position].layout
    }

    override fun onBindViewHolder(holder: BindingViewHolder<Item, ViewDataBinding>, position: Int) {
        with(holder) {
            with(getItem(position)){
                if (this !is Item.Data<*>) return
                forceBind(data)
            }
            setItemPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return item?.size ?: 0
    }


    fun bindClick(holder: BindingViewHolder<*, *>, binding: ViewDataBinding) {
        val position = holder.layoutPosition
        val item = getItem(position)
        if (item !is Item.Data<*>) return
        val data = item.data as Any
        itemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                listener.onItemClick(data, position)
            }
        }
        itemLongClickListener?.let { listener ->
            binding.root.setOnLongClickListener {
                listener.onItemClick(data, position)
                return@setOnLongClickListener true
            }
        }
    }


}
