package com.jamesthacker.countrysample.domain.result

sealed class DomainResult<out T: Any> {
    data class Success<out T: Any>(val data: T): DomainResult<T>()
    data class Error(val error: DomainError): DomainResult<Nothing>()
}
