package info.adavis.sampleapp

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import javax.inject.Singleton

/**
 * Example that shows you must use @IntoMap and a Key for item to be included
 */
@Module
class Test2Module {

    @Provides @IntoMap
    @StringKey("item1")
    internal fun provideItem1(): Item {
        return Item("string1")
    }

    @Provides @IntoMap
    @StringKey("item2")
    internal fun provideItem2(): Item {
        return Item("string2")
    }

    @Provides
    internal fun provideItem3(): Item {
        return Item("string3")
    }
}

@Singleton
@Component(modules = arrayOf(Test2Module::class))
interface Test2Component {

    fun items(): Map<String, Item>

    fun item(): Item
}

class Example2Test {

    @Test
    fun shouldBindMultipleItems() {
        val testComponent = DaggerTest2Component.create()
        val items = testComponent.items()

        assertThat(items.size, equalTo(2))
        assertThat(items["item1"]?.name, equalTo("string1"))
        assertThat(items["item2"]?.name, equalTo("string2"))
    }

    @Test
    fun shouldBindSingleItem() {
        val testComponent = DaggerTest2Component.create()

        assertThat(testComponent.item().name, equalTo("string3"))
    }

}