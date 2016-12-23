package hwp.gametime

import android.app.Application
import hwp.gametime.network.ServiceGenerator
import hwp.gametime.network.StattleshipApiEndpointInterface
import io.realm.Realm
import io.realm.RealmConfiguration

/**
 * Created by ahmedrizwan on 15/03/2016.
 *
 */
class App : Application() {
    var stattleshipService: StattleshipApiEndpointInterface? = null

    override fun onCreate() {
        super.onCreate()
        val realmConfig = RealmConfiguration.Builder(this).deleteRealmIfMigrationNeeded().build()
        Realm.setDefaultConfiguration(realmConfig)

        stattleshipService =
                ServiceGenerator
                        .createService(StattleshipApiEndpointInterface::class.java,
                                getString(R.string.stattleship_api_token))
    }
}