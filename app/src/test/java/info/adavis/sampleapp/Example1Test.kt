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
 * Example that shows using a String for the Map key
 */
@Module
class Test1Module {

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

}

@Singleton
@Component(modules = arrayOf(Test1Module::class))
interface Test1Component {

    fun items(): Map<String, Item>
}

class Example1Test {

    @Test
    fun shouldBindMultipleItems() {
        val testComponent = DaggerTest1Component.create()
        val items = testComponent.items()

        assertThat(items["item1"]?.name, equalTo("string1"))
        assertThat(items["item2"]?.name, equalTo("string2"))
    }

}