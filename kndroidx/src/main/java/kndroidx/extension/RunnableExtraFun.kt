package kndroidx.extension

fun RunnableX(block: Runnable.() -> Unit): Runnable {
    return object : Runnable {
        override fun run() {
            block()
        }
    }
}