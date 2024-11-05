package com.example.beercellar

import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasClickAction
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onAllNodesWithContentDescription
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.beercellar.screens.Authentication
import com.example.beercellar.screens.BeerAdd

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Rule

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class AuthenticationTest {

    @get: Rule
    val  composeTestRule = createComposeRule()

    @Test
    fun testRegister() {
        var emailCaptured: String? = null
        var passwordCaptured: String? = null



        composeTestRule.setContent {
            Authentication(
                register = { email, password ->
                    emailCaptured = email
                    passwordCaptured = password
                }
            )
        }


        composeTestRule.onNodeWithText("Email").performTextInput("test@example.com")
        Thread.sleep(2000)

        composeTestRule.onNodeWithText("Password").performTextInput("password123")
        Thread.sleep(2000)

        composeTestRule.onNodeWithText("Register").performClick()
        Thread.sleep(2000)


        assertEquals("test@example.com", emailCaptured)
        assertEquals("password123", passwordCaptured)

        composeTestRule.onNodeWithText("Registration Successful").assertIsDisplayed()
        composeTestRule.onNodeWithText("You have successfully registered!").assertIsDisplayed()

        Thread.sleep(2000)
        composeTestRule.onNodeWithText("OK").performClick()


    }

}