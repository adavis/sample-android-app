package info.adavis.sampleapp

import javax.inject.Inject

open class Item(val name: String)

class Item1 @Inject constructor() : Item("string1") {

    init {
        println("** Item1: I am created **")
    }
}

class Item2 @Inject constructor() : Item("string2") {

    init {
        println("** Item2: I am created **")
    }
}
