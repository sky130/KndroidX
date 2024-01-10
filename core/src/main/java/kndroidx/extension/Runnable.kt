package kndroidx.extension

open class RunnableX(val block: Runnable.() -> Unit) : Runnable {
    override fun run() {
        block()
    }
}