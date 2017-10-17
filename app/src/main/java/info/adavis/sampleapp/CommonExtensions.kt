package info.adavis.sampleapp

object Logger {

    fun log(msg: Any) = println("${System.nanoTime()}: ${Thread.currentThread().name}: $msg")
}
