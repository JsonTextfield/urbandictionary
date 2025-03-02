package com.jsontextfield.urbandictionary

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform