package com.jamesthacker.countrysample.data.api

import com.jamesthacker.countrysample.domain.result.DomainError
import com.jamesthacker.countrysample.domain.result.DomainResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerializationException
import retrofit2.Response
import timber.log.Timber
import java.io.IOException
import java.net.UnknownHostException
import javax.inject.Inject

class ApiClient @Inject constructor() {

    suspend fun <T : Any, R : Any> execute(
        request: suspend () -> Response<T>,
        mapToDomain: T.() -> R
    ): DomainResult<R> {
        return try {
            withContext(networkDispatcher) {
                val response = request.invoke()
                return@withContext if (response.isSuccessful) {
                    DomainResult.Success(response.body()!!.mapToDomain())
                } else {
                    // Ideally a user-facing message should be determined here based on
                    // HTTP response code and response body
                    DomainResult.Error(DomainError.ApiError)
                }
            }
        } catch (ex: UnknownHostException) {
            Timber.w(ex)
            DomainResult.Error(DomainError.NetworkError)
        } catch (ex: IOException) {
            Timber.w(ex)
            DomainResult.Error(DomainError.NetworkError)
        } catch (ex: NullPointerException) {
            Timber.w(ex)
            DomainResult.Error(DomainError.Unknown)
        } catch (ex: SerializationException) {
            Timber.w(ex)
            DomainResult.Error(DomainError.NetworkError)
        }
    }

    private val networkDispatcher: CoroutineDispatcher = Dispatchers.IO
}
