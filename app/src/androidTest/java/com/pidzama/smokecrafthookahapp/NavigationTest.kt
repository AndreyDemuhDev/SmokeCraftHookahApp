package com.pidzama.smokecrafthookahapp

import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class NavigationTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)


    @Test
    fun myUIComponentTest() {
        // Set up the test environment


        // Interact with the UI and trigger user actions
        hiltRule.componentReady()

    }

}