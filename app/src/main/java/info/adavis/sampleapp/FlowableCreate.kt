package info.adavis.sampleapp

import info.adavis.sampleapp.Logger.log
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Observable

class FlowableCreate {

    fun create(): Flowable<Int> {
        return Flowable.create({ emitter ->
            log("create")
            emitter.onNext(5)
            emitter.onNext(6)
            emitter.onNext(7)
            emitter.onComplete()
            log("complete")
        }, BackpressureStrategy.BUFFER)
    }

}

fun main(args: Array<String>) {
    log("before")
    FlowableCreate()
            .create()
            .subscribe{i -> log("item: $i")}
    log("after")
}