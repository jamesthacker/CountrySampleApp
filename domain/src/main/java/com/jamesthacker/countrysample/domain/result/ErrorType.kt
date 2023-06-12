package com.jamesthacker.countrysample.domain.result

sealed class DomainError {
    object ApiError: DomainError()
    object NetworkError: DomainError()
    object Unknown: DomainError()
}
