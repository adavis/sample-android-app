package info.adavis.sampleapp

import info.adavis.sampleapp.Logger.log
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import java.lang.Thread.sleep
import java.util.concurrent.TimeUnit.SECONDS

class ObservableCreate {

    fun create(): Observable<Int> {
        return Observable.create<Int> { subscriber ->
            log("create")
            subscriber.onNext(5)
            subscriber.onNext(6)
            subscriber.onNext(7)
            subscriber.onComplete()
            log("complete")
        }
    }

}

/**
 * Examples using [Observable.create].
 *
 * 1) Only one subscriber, on the same thread
 * 2) Two subscribers, on the same thread
 * 3) Two subscribers, with the [Observable.cache], on the same thread
 * 4) One subscriber, with the [Observable.subscribeOn]
 * 5) One subscriber, with the [Observable.subscribeOn] and [Observable.observeOn]
 */
fun main(args: Array<String>) {
    log("before")
    ObservableCreate()
            .create()
            .subscribe{i -> log("item: $i")}
    log("after")

    sleep(SECONDS.toMillis(1))

    log("before")
    var observable = ObservableCreate().create()
    observable.subscribe { i -> log("item A: $i") }
    observable.subscribe { i -> log("item B: $i") }
    log("after")

    sleep(SECONDS.toMillis(1))

    log("before")
    observable = ObservableCreate().create().cache()
    observable.subscribe { i -> log("item C: $i") }
    observable.subscribe { i -> log("item D: $i") }
    log("after")

    sleep(SECONDS.toMillis(1))

    log("before")
    observable = ObservableCreate().create().subscribeOn(Schedulers.io())
    observable.subscribe { i -> log("item E: $i") }
    log("after")

    sleep(SECONDS.toMillis(1))

    log("before")
    observable = ObservableCreate().create()
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
    observable.subscribe { i -> log("item F: $i") }
    log("after")
}