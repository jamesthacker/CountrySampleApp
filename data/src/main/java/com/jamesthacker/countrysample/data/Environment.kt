package com.jamesthacker.countrysample.data

interface Environment {
    fun baseUrl(): String
    fun enableLogging(): Boolean
}
