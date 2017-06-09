package info.adavis.sampleapp

import android.app.Application
import io.realm.DynamicRealm
import io.realm.Realm
import io.realm.RealmConfiguration
import io.realm.RealmMigration
import io.realm.exceptions.RealmMigrationNeededException
import timber.log.Timber

class SampleApp : Application() {

    override fun onCreate() {
        super.onCreate()

        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }

        configureRealm()

        Timber.i("Creating our Application")
    }

    fun configureRealm() {
        Realm.init(this)

        val configuration = RealmConfiguration.Builder()
                .name("sample_app.realm")
                .schemaVersion(1L) // todo: bump this to 2 to enable the migration
                .migration(SampleAppMigration())
                .build()

        val dRealm = DynamicRealm.getInstance(configuration)
        val schema = dRealm.schema
        if (schema.contains("UserWard")) {
            Timber.i("agentId: " + schema.get("UserWard").isNullable("agentId"))
        } else {
            Timber.i("No UserWard")
        }
        dRealm.close()

        try {
            Realm.setDefaultConfiguration(configuration)
            Realm.getDefaultInstance()
        } catch (e: RealmMigrationNeededException) {
            Timber.e(e, "error initializing realm")

            if (BuildConfig.DEBUG) {
                throw e
            }
            else {
                // In case there is a migration failure, delete realm file and recreate
                Realm.deleteRealm(configuration)
                Realm.setDefaultConfiguration(configuration)
            }
        }
    }

}

class SampleAppMigration : RealmMigration {

    override fun migrate(realm: DynamicRealm?, oldVersion: Long, newVersion: Long) {
        if (oldVersion == 1L) {
            if (!(realm?.schema?.contains("UserWard") ?: false)) {
                realm?.schema?.create("UserWard")
                        ?.addRealmObjectField("ward", realm.schema.get("Ward"))
                        ?.addField("agentId", Long::class.java)
            }

            Timber.d("migration complete")
        }
    }

}
