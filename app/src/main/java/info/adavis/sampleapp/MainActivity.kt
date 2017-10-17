package info.adavis.sampleapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import info.adavis.sampleapp.data.Injector.getApi
import info.adavis.sampleapp.data.Post
import info.adavis.sampleapp.data.SampleAPI
import info.adavis.sampleapp.data.User
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.toast
import timber.log.Timber
import io.reactivex.Observable.fromIterable as fromObservableIterable

class MainActivity : AppCompatActivity() {

    val myButton: Button by lazy { findViewById(R.id.button) as Button }

    var disposable: Disposable? = null

    lateinit var api: SampleAPI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        api = getApi()

        myButton.setOnClickListener {
            disposable = getPostsForFirstThreeUsers()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            { posts -> toast("There are: ${posts.size} posts") },
                            { ex -> Timber.e(ex, "there was an error processing the request") }
                    )
        }
    }

    override fun onDestroy() {
        disposable?.dispose()
        super.onDestroy()
    }

    fun getAllUsers(): Single<List<User>> {
        return api.getUsers()
    }

    fun getFirstThreeUsers(): Flowable<User> {
        return getAllUsers()
                .flattenAsFlowable { users -> users }
                .doOnNext { Timber.i("a user: ${it.username}") }
                .take(3)
    }

    fun getPostsForFirstThreeUsers(): Single<MutableList<Post>> {
        return getFirstThreeUsers()
                .flatMapSingle { api.getUsersPosts(it.id) }
                .doOnNext { Timber.i("number of posts: ${it.size}") }
                .flatMapIterable { it }
                .doOnNext { Timber.i("the post: $it") }
                .toList()
    }
}
