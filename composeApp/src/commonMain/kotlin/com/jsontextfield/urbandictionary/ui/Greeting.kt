package com.jsontextfield.urbandictionary.ui

import com.jsontextfield.urbandictionary.getPlatform

class Greeting {
    private val platform = getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}