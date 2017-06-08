package info.adavis.sampleapp

import dagger.Binds
import dagger.Component
import dagger.Module
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Example that shows use of @Binds instead of @Provides.
 * It also demonstrates the order of object creation of injected Maps
 */
@Module
abstract class Test4Module {

    @Binds @IntoMap
    @IntKey(1)
    internal abstract fun bindItem1(item: Item1): Item

    @Binds @IntoMap
    @IntKey(2)
    internal abstract fun bindItem2(item: Item2): Item

}

@Singleton
@Component(modules = arrayOf(Test4Module::class))
interface Test4Component {

    fun items(): Map<Int, Item>

    fun itemContainer(): ItemContainer

}

class ItemContainer @Inject constructor(val items: @JvmSuppressWildcards Map<Int, Item>) {

    fun getItem1() = items[1]

}

class Example4Test {

    @Test
    fun shouldBindMultipleItems() {
        val testComponent = DaggerTest4Component.create()
        println("grabbing the items")
        val items = testComponent.items()

        assertThat(items.size, equalTo(2))
        assertThat(items[1]?.name, equalTo("string1"))
        assertThat(items[2]?.name, equalTo("string2"))
    }

    @Test
    fun shouldReturnSameItem() {
        val testComponent = DaggerTest4Component.create()
        println("grabbing the item container")
        val itemContainer = testComponent.itemContainer()

        println("grabbing the first item")
        val item1 = itemContainer.getItem1()

        assertThat(itemContainer.getItem1(), equalTo(item1))
    }

}