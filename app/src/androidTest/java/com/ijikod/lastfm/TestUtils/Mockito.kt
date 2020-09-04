package com.ijikod.lastfm.TestUtils

import org.mockito.Mockito

class Mockito {
    inline fun <reified T> mock(): T = Mockito.mock(T::class.java)
}