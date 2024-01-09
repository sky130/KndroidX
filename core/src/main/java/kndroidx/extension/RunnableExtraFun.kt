package kndroidx.extension

class RunnableX(val block: Runnable.() -> Unit): Runnable {
    override fun run() {
        block()
    }
}