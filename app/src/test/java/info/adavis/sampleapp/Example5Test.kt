package info.adavis.sampleapp

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertNotEquals
import org.junit.Assert.assertThat
import org.junit.Test
import javax.inject.Inject
import javax.inject.Provider
import javax.inject.Singleton

/**
 * Example that shows use of @Provider in the injected Map.
 * It also demonstrates the order of object creation of injected Maps
 */
@Module
abstract class Test5Module {

    @Binds @IntoMap
    @IntKey(1)
    internal abstract fun bindItem1(item: Item1): Item

    @Binds @IntoMap
    @IntKey(2)
    internal abstract fun bindItem2(item: Item2): Item

}

@Singleton
@Component(modules = arrayOf(Test5Module::class))
interface Test5Component {

    fun itemContainer(): Item2Container

}

class Item2Container @Inject constructor(val items: @JvmSuppressWildcards Map<Int, Provider<Item>>) {

    fun getItem1() = items[1]?.get()

}

class Example5Test {

    @Test
    fun shouldNotReturnSameItem() {
        val testComponent = DaggerTest5Component.create()
        println("grabbing the item container")
        val itemContainer = testComponent.itemContainer()

        println("grabbing the first item")
        val item1 = itemContainer.getItem1()

        assertNotEquals(itemContainer.getItem1(), item1)
    }

}