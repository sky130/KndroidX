package kndroidx.databinding.recycler

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BaseBindingAdapter<T : Any, BINDING : ViewDataBinding> :
    RecyclerView.Adapter<BindingViewHolder<T, BINDING>>() {

    var itemClickListener: OnItemClickListener<T>? = null
    var itemLongClickListener: OnItemClickListener<T>? = null
    var itemViewTypeCreator: ItemViewTypeCreator? = null
    var itemEventHandler: Any? = null
    var itemHelper: Any? = null
    var viewModel: Any? = null
    var activity: Any? = null
    var item: List<T>? = null
        @SuppressLint("NotifyDataSetChanged")
        set(data) {
            DiffUtil
                .calculateDiff(CommonDiffCallback<T>(field ?: emptyList(), data ?: emptyList()))
                .dispatchUpdatesTo(this)
            field = data
        }


    @get:LayoutRes
    abstract val layoutRes: Int

    fun getItem(position: Int): T? {
        return item?.getOrNull(position)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BindingViewHolder<T, BINDING> {
        val layout = itemViewTypeCreator?.getItemLayout(viewType) ?: layoutRes
        val binding = DataBindingUtil.inflate<BINDING>(
            LayoutInflater.from(parent.context),
            layout,
            parent,
            false
        )
        val holder = BindingViewHolder<T, BINDING>(binding)
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
        return itemViewTypeCreator?.getItemViewType(position, getItem(position))
            ?: super.getItemViewType(position)
    }

    override fun onBindViewHolder(holder: BindingViewHolder<T, BINDING>, position: Int) {
        with(holder) {
            bind(getItem(position))
            setItemPosition(position)
        }
    }

    override fun getItemCount(): Int {
        return item?.size ?: 0
    }

    interface OnItemClickListener<T> {
        fun onItemClick(t: T?, position: Int)
    }


    protected fun bindClick(holder: BindingViewHolder<*, *>, binding: BINDING) {
        itemClickListener?.let { listener ->
            binding.root.setOnClickListener {
                val position = holder.layoutPosition
                listener.onItemClick(getItem(position), position)
            }
        }
        itemLongClickListener?.let { listener ->
            binding.root.setOnLongClickListener {
                val position = holder.layoutPosition
                listener.onItemClick(getItem(position), position)
                return@setOnLongClickListener true
            }
        }
    }


    interface ItemViewTypeCreator {
        /**
         * 通过 item 下标和数据返回布局类型
         * @param position item 下标
         * @param item item 数据
         * @return item 布局类型
         */
        fun getItemViewType(position: Int, item: Any?): Int

        /**
         * 通过 item 布局类型返回布局资源 id
         * @param viewType item 数据类型
         * @return item 布局资源 id
         */
        fun getItemLayout(viewType: Int): Int
    }

}
