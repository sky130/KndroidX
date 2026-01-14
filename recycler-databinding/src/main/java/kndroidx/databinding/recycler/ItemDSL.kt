package kndroidx.databinding.recycler

import androidx.annotation.LayoutRes

typealias MutableItemList = MutableList<Item>
typealias ItemList = List<Item>


@Suppress("FunctionName")
inline fun Any.ItemList(block: MutableItemList.() -> Unit) =
    mutableListOf<Item>().apply(block).toList()

@Suppress("FunctionName")
inline fun <T> Any.ItemList(
    baseList: List<T>,
    noinline transform: (T) -> Item,
    content: MutableItemList.() -> Unit = {}
) = baseList.asSequence().map(transform).toMutableList().apply(content).toList()

@Suppress("FunctionName")
inline fun Any.ItemList(baseList: List<Item>, block: MutableItemList.() -> Unit) =
    baseList.toMutableList().apply(block).toList()

@Suppress("FunctionName")
fun <T> MutableItemList.DataList(
    list: List<T>,
    @LayoutRes layout: Int,
    isClickable: Boolean = true,
    onEmpty: MutableItemList.() -> Unit = {},
    onEach: (T) -> Unit = {}
) = if (list.isEmpty()) {
    onEmpty()
} else {
    list.forEach { Data(it, layout, isClickable) }
}

@Suppress("FunctionName")
fun <T, R> MutableItemList.DataList(
    list: List<T>,
    transform: (T) -> R,
    @LayoutRes layout: Int,
    isClickable: Boolean = true,
    onEmpty: MutableItemList.() -> Unit = {},
    onEach: (R) -> Unit = {}
) = if (list.isEmpty()) {
    onEmpty()
} else {
    list.forEach { Data(transform(it), layout, isClickable) }
}

fun <T> MutableItemList.Data(data: T, @LayoutRes layout: Int, clickable: Boolean = true) =
    Item.Data(data, layout, clickable).apply { add(this) }

fun MutableItemList.Layout(@LayoutRes layout: Int, index: Int? = null) =
    Item.Layout(layout).apply {
        if (index == null)
            add(this)
        else
            add(index, this)
    }

