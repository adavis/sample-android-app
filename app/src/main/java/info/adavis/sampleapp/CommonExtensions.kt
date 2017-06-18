package info.adavis.sampleapp

object Logger {

    fun log(msg: Any) = println("${Thread.currentThread().name}: $msg")
}
