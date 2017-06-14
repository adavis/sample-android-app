package info.adavis.sampleapp

import dagger.Component
import dagger.MapKey
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntKey
import dagger.multibindings.IntoMap
import dagger.multibindings.StringKey
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import javax.inject.Singleton

/**
 * Example that shows a Custom Map Key using an Enum
 */
enum class TestEnum {
    ONE,
    TWO
}

@MapKey
internal annotation class TestEnumKey(val value: TestEnum)

@Module
class Test6Module {

    @Provides @IntoMap
    @TestEnumKey(TestEnum.ONE)
    internal fun provideItem1(): Item {
        return Item("string1")
    }

    @Provides @IntoMap
    @TestEnumKey(TestEnum.TWO)
    internal fun provideItem2(): Item {
        return Item("string2")
    }

}

@Singleton
@Component(modules = arrayOf(Test6Module::class))
interface Test6Component {

    fun items(): Map<TestEnum, Item>

}

class Example6Test {

    @Test
    fun shouldBindMultipleItems() {
        val testComponent = DaggerTest6Component.create()
        val items = testComponent.items()

        assertThat(items.size, equalTo(2))
        assertThat(items[TestEnum.ONE]?.name, equalTo("string1"))
        assertThat(items[TestEnum.TWO]?.name, equalTo("string2"))
    }

}