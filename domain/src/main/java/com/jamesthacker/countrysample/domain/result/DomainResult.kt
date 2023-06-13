package com.jamesthacker.countrysample.domain.result

sealed class DomainResult<out T : Any> {
    object Uninitialized : DomainResult<Nothing>()
    data class Success<out T : Any>(val data: T, val isFromCache: Boolean = false) : DomainResult<T>()
    data class Error(val error: DomainError) : DomainResult<Nothing>()
}
