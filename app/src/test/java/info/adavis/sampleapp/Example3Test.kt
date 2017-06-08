package info.adavis.sampleapp

import dagger.Component
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import javax.inject.Singleton

/**
 * Example that shows an Integer for the Map key
 */
@Module
class Test3Module {

    @Provides @IntoMap
    @IntKey(1)
    internal fun provideItem1(): Item {
        return Item("string1")
    }

    @Provides @IntoMap
    @IntKey(2)
    internal fun provideItem2(): Item {
        return Item("string2")
    }

}

@Singleton
@Component(modules = arrayOf(Test3Module::class))
interface Test3Component {

    fun items(): Map<Int, Item>

}

class Example3Test {

    @Test
    fun shouldBindMultipleItems() {
        val testComponent = DaggerTest3Component.create()
        val items = testComponent.items()

        assertThat(items.size, equalTo(2))
        assertThat(items[1]?.name, equalTo("string1"))
        assertThat(items[2]?.name, equalTo("string2"))
    }

}