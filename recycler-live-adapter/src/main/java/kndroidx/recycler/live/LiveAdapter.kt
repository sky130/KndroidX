package kndroidx.recycler.live

import android.util.ArrayMap
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.ItemTouchHelper.Callback
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import androidx.viewbinding.ViewBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import kotlin.reflect.KClass

typealias OnItem<T> = (LiveAdapter<T>.Item.() -> Unit)
typealias AreTheSame<T> = (old: T, new: T) -> Boolean
typealias MovementFlags = Callback.(view: RecyclerView, holder: ViewHolder) -> Int
typealias OnMove = Callback.(view: RecyclerView, holder: ViewHolder, target: ViewHolder) -> Boolean
typealias OnSwiped = Callback.(holder: ViewHolder, direction: Int) -> Unit
typealias OnSelectedChanged = Callback.(viewHolder: ViewHolder?, actionState: Int) -> Unit
typealias ClearView = Callback.(view: RecyclerView, holder: ViewHolder) -> Unit


class LiveAdapter<T>(
    private val scope: CoroutineScope? = null, private val owner: LifecycleOwner? = null
) : Adapter<LiveAdapter.LiveViewHolder>() {

    private val classMap = ArrayMap<Int, Class<ViewBinding>>()
    private var onItemBlock: OnItem<T>? = {}
    private var mList: List<T>? = null
        set(value) {
            if (value == null) {
                return
            } else {
                val diffResult = DiffUtil.calculateDiff(LiveDiffCallback(list, value))
                diffResult.dispatchUpdatesTo(this)
            }
            field = value
        }
    private var mJob: Job? = null
    private var mObserver: Observer<List<T>> = Observer {
        mList = it
    }
    private var mLiveData: LiveData<List<T>>? = null
    var list
        get() = mList ?: emptyList()
        set(value) {
            mList = value
        }

    class LiveViewHolder(val binding: ViewBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): LiveViewHolder {
        return LiveViewHolder(
            classMap[viewType]!!.getDeclaredMethod(
                "inflate", LayoutInflater::class.java, ViewGroup::class.java, Boolean::class.java
            ).invoke(null, LayoutInflater.from(parent.context), parent, false) as ViewBinding
        )
    }

    override fun getItemViewType(position: Int): Int {
        list[position]!!::class.java.annotations.forEach {
            if (it is BindingClass) {
                it.vbClass.hashCode().let { code ->
                    classMap[code] = it.vbClass.java as Class<ViewBinding>
                    return code
                }
            }
        }
        throw IllegalStateException("Don't miss BindingClass")
    }

    override fun getItemCount() = list.size

    override fun onBindViewHolder(holder: LiveViewHolder, position: Int) {
        onItemBlock?.invoke(Item(list[position], holder.binding))
    }

    inner class Item(val item: T, val binding: ViewBinding) {
        inline fun <reified T, reified VB : ViewBinding> Any.bind(block: VB.(T) -> Unit) {
            (binding as VB).block(item as T)
        }
    }

    fun Any.items(flow: Flow<List<T>>, onItemBlock: OnItem<T>) {
        mJob?.cancel()
        mLiveData?.removeObserver(mObserver)
        (scope ?: throw IllegalStateException()).launch {
            flow.collectLatest {
                mList = it
            }
        }.let { mJob = it }
        this@LiveAdapter.onItemBlock = onItemBlock
    }

    fun Any.items(data: LiveData<List<T>>, onItemBlock: OnItem<T>) {
        mJob?.cancel()
        mLiveData?.removeObserver(mObserver)
        (owner ?: throw IllegalStateException()).let {
            mLiveData = data
            data.observe(it, mObserver)
        }
        this@LiveAdapter.onItemBlock = onItemBlock
    }

    private inner class LiveDiffCallback(
        private val oldList: List<T>, private val newList: List<T>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int {
            return oldList.size
        }

        override fun getNewListSize(): Int {
            return newList.size
        }

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (areItemsTheSame ?: ::simpleReturn)(
                oldList[oldItemPosition], newList[newItemPosition]
            )
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return (areContentsTheSame ?: ::simpleReturn)(
                oldList[oldItemPosition], newList[newItemPosition]
            )
        }
    }

    private var areContentsTheSame: AreTheSame<T>? = null
    private var areItemsTheSame: AreTheSame<T>? = null

    inner class DiffScope {

        fun Any.areContentsTheSame(block: AreTheSame<T>) {
            areContentsTheSame = block
        }

        fun Any.areItemsTheSame(block: AreTheSame<T>) {
            areItemsTheSame = block
        }
    }

    fun Any.diff(block: DiffScope.() -> Unit) = DiffScope().block()

    fun simpleReturn(old: T, new: T) = old == new

    inner class TouchScope {
        fun Any.movementFlags(block: MovementFlags) {
            movementFlags = block
        }

        fun Any.onMove(block: OnMove) {
            onMove = block
        }

        fun Any.onSwiped(block: OnSwiped) {
            onSwiped = block
        }

        fun Any.onSelectedChanged(block: OnSelectedChanged) {
            onSelectedChanged = block
        }

        fun Any.clearView(block: ClearView) {
            clearView = block
        }
    }

    private var movementFlags: MovementFlags? = null
    private var onMove: OnMove? = null
    private var onSwiped: OnSwiped? = null
    private var onSelectedChanged: OnSelectedChanged? = null
    private var clearView: ClearView? = null

    val touchCallback
        get() =
            if (movementFlags != null || onMove != null || onSwiped != null || onSelectedChanged != null || clearView != null) TouchCallback(
                this
            ) else null

    fun Any.touch(block: TouchScope.() -> Unit) = TouchScope().block()


    inner class TouchCallback(val adapter: LiveAdapter<T>) : ItemTouchHelper.Callback() {
        override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: ViewHolder): Int {
            return movementFlags?.invoke(this, recyclerView, viewHolder) ?: makeMovementFlags(0, 0)
        }

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: ViewHolder,
            target: ViewHolder
        ): Boolean {
            return onMove?.invoke(this, recyclerView, viewHolder, target) ?: false
        }

        override fun onSwiped(viewHolder: ViewHolder, direction: Int) {
            onSwiped?.invoke(this, viewHolder, direction)
        }

        override fun onSelectedChanged(viewHolder: ViewHolder?, actionState: Int) {
            super.onSelectedChanged(viewHolder, actionState)
            onSelectedChanged?.invoke(this, viewHolder, actionState)
        }

        override fun clearView(recyclerView: RecyclerView, viewHolder: ViewHolder) {
            super.clearView(recyclerView, viewHolder)
            clearView?.invoke(this, recyclerView, viewHolder)
        }

    }

}

var RecyclerView.liveAdapter: LiveAdapter<*>
    set(value) {
        value.touchCallback?.let {
            ItemTouchHelper(it).attachToRecyclerView(this)
        }
        adapter = value
    }
    get() = adapter as LiveAdapter<*>

inline fun <reified T> liveAdapter(
    scope: CoroutineScope? = null, owner: LifecycleOwner? = null, crossinline block: LiveAdapter<T>.() -> Unit
) = lazy { LiveAdapter<T>(scope = scope, owner = owner).apply(block) }

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
annotation class BindingClass(val vbClass: KClass<out ViewBinding>)


