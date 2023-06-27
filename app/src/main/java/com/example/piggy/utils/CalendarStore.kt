package com.example.piggy.utils

object CalendarStore {
    private val store = mutableMapOf<String, List<SpendItem>>()

    fun getItems(key: String): List<SpendItem> {
        if (store[key] == null) {
            store[key] = mutableListOf()
        }
        return store[key]!!
    }

    fun putItems(key: String, items: List<SpendItem>) {
        store[key] = items
    }

    override fun toString(): String {
        return store.map { "${it.key} : ${it.value.toString()}" }.joinToString("\n")
    }
}