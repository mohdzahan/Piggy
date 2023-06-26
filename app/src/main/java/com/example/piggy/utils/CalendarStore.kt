package com.example.piggy.utils

object CalendarStore {
    private val store = mutableMapOf<Long, List<SpendItem>>()

    fun getItems(key: Long): List<SpendItem> {
        if (store[key] == null) {
            store[key] = mutableListOf()
        }
        return store[key]!!
    }

    fun putItems(key: Long, items: List<SpendItem>) {
        store[key] = items
    }
}