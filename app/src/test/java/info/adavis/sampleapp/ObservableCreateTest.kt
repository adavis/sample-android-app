package info.adavis.sampleapp

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Action
import io.reactivex.functions.Consumer
import org.junit.Test
import java.util.Random
import java.util.concurrent.TimeUnit
import android.R.attr.delay
import io.reactivex.functions.BiFunction
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import io.reactivex.schedulers.TestScheduler


class ObservableCreateTest {

    @Test
    fun testCreate() {
        val observable = Observable.create<Int> { subscriber ->
            Logger.log("create")
            subscriber.onNext(5)
            subscriber.onNext(6)
            subscriber.onNext(7)
            subscriber.onComplete()
            Logger.log("complete")
        }

        observable.test().assertValues(5, 6, 7)
    }




    @Test
    fun testCreate_withSubscriber() {
        val observable = Observable.create<Int> { subscriber ->
            Logger.log("create")
            subscriber.onNext(5)
            subscriber.onNext(6)
            subscriber.onNext(7)
            subscriber.onComplete()
            Logger.log("complete")
        }

        observable.subscribe{ Logger.log("next: $it") }

        Logger.log("done")
    }






    @Test
    fun testCreate_withExplicitSubscriberFull() {
        val observable = Observable.create<Int> { subscriber ->
            Logger.log("create")
            subscriber.onNext(5)
            subscriber.onNext(6)
            subscriber.onNext(7)
            subscriber.onComplete()
            Logger.log("complete")
        }

        observable.subscribe{ Logger.log("next: $it") }
    }






    @Test
    fun testJust() {
        val observable = Observable.just(5, 6,7)

        observable
                .map { it * 5 }
                .filter { it > 25 }
                .subscribe{ println(it) }
    }










    @Test
    fun transformWithMap() {
        Observable.just(5, 6, 7)
                .map(object: Function<Int, String> {
                    override fun apply(t: Int): String {
                        return ";-) ".repeat(t)
                    }
                })
                .subscribe { println(it) }

        Thread.sleep(2000)
    }


    @Test
    fun transformWithFlatMap() {
        val scheduler = TestScheduler()

        Observable.just(5, 6, 7)
                .flatMap({
                             val delay = Random().nextInt(5).toLong()
                             Observable.just(it * 5)
                                     .delay(delay, TimeUnit.SECONDS, scheduler)
                         })
                .subscribe { Logger.log("next: $it") }

        scheduler.advanceTimeBy(1, TimeUnit.MINUTES)
    }













    @Test
    fun testCollection() {
        println(listOf(5, 6, 7)
                        .map { it * 5 }
                        .filter { it > 25 })

        val observable = Observable.create<Int> { subscriber ->
            Logger.log("create")
            subscriber.onNext(5)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onNext(6)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onNext(7)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onComplete()
            Logger.log("complete")
        }

        observable
                .map { it * 5 }
                .filter { it > 25 }
                .subscribe{ Logger.log("$it") }
    }

    @Test
    fun testSequence() {
        println(listOf(5, 6, 7)
                        .asSequence()
                        .map { it * 5 }
                        .filter { it > 25 }
                        .toList())

        val observable = Observable.create<Int> { subscriber ->
            Logger.log("create")
            subscriber.onNext(5)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onNext(6)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onNext(7)
            Thread.sleep(TimeUnit.SECONDS.toMillis(1))

            subscriber.onComplete()
            Logger.log("complete")
        }

        observable
                .map { it * 5 }
                .filter { it > 25 }
                .subscribe{ Logger.log("$it") }
    }


    @Test
    fun transformWithZip() {
        Observable.just(5, 6, 7)
                .zipWith(Observable.just("A", "B", "C"),
                         BiFunction { x: Int, y: String -> x.toString() + y })
                .subscribe { println(it) }
    }
}