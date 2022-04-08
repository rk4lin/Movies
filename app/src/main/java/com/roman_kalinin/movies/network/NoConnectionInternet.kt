package com.roman_kalinin.movies.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.roman_kalinin.movies.AppEvent
import com.roman_kalinin.movies.EventBus
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoConnectionInternet @Inject constructor(private val context: Context) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        return if (!isConnectionOn()) {
            println("No connect to network")
            throw NoConnectivityException()
        } else {
            chain.proceed(chain.request())
        }
    }

    private fun isConnectionOn(): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val network = connectivityManager.activeNetwork
        val connection = connectivityManager.getNetworkCapabilities(network)
        return connection != null && (
                connection.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                        connection.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR))

    }
}

class NoConnectivityException : IOException() {

    init{
        CoroutineScope(Dispatchers.Default).launch{
            EventBus.invokeEvent(AppEvent.NoConnection)
        }
    }

    override val message: String
        get() = "No network available, please check your WiFi or Data connection"
}
