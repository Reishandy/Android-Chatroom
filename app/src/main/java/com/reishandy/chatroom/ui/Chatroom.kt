package com.reishandy.chatroom.ui

import android.app.Activity
import android.app.Application
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.reishandy.chatroom.ui.component.Login
import com.reishandy.chatroom.ui.component.Register
import com.reishandy.chatroom.ui.component.Verify

enum class ChatroomNavItems {
    LOGIN,
    REGISTER,
    VERIFY,
    HOME,
}

@Composable
fun ChatroomApp() {
    val context = LocalContext.current
    val application = context.applicationContext as Application
    val navController: NavHostController = rememberNavController()

    // ViewModels

    Surface(modifier = Modifier.fillMaxSize()) {
        NavHost(
            navController = navController,
            startDestination = ChatroomNavItems.LOGIN.name,
            modifier = Modifier
        ) {
            // Login
            composable(
                route = ChatroomNavItems.LOGIN.name,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                // Close the app when back button is pressed
                BackHandler { (context as? Activity)?.finishAffinity() }

                // TODO
                Login(
                    emailValue = "",
                    onEmailValueChange = {},
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    isPasswordError = false,
                    onLoginClick = {},
                    onChangeToRegisterClick = {
                        // TODO: Clear the fields
                        navController.navigate(ChatroomNavItems.REGISTER.name)
                    },
                )
            }

            // Register
            composable(
                route = ChatroomNavItems.REGISTER.name,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Left,
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Right,
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                BackHandler { (context as? Activity)?.finishAffinity() }

                // TODO
                Register(
                    usernameValue = "",
                    onUsernameValueChange = {},
                    emailValue = "",
                    onEmailValueChange = {},
                    isEmailError = false,
                    passwordValue = "",
                    onPasswordValueChange = {},
                    isPasswordError = false,
                    confirmPasswordValue = "",
                    onConfirmPasswordValueChange = {},
                    isConfirmPasswordError = false,
                    onRegisterClick = {
                        // TODO
                        navController.navigate(ChatroomNavItems.VERIFY.name)
                    },
                    onChangeToLoginClick = {
                        // TODO: Clear the fields
                        navController.navigate(ChatroomNavItems.LOGIN.name)
                    }
                )
            }

            // Verify
            composable(
                route = ChatroomNavItems.VERIFY.name,
                enterTransition = {
                    slideIntoContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Up,
                        animationSpec = tween(300)
                    ) + fadeIn(animationSpec = tween(300))
                },
                exitTransition = {
                    slideOutOfContainer(
                        towards = AnimatedContentTransitionScope.SlideDirection.Down,
                        animationSpec = tween(300)
                    ) + fadeOut(animationSpec = tween(300))
                }
            ) {
                BackHandler {
                    navController.navigate(ChatroomNavItems.REGISTER.name) {
                        popUpTo(navController.graph.findStartDestination().id) {
                            saveState = true
                        }
                    }
                }

                // TODO
                Verify(
                    emailSentTo = "example@example.com",
                    verificationCodeValue = "",
                    onVerificationCodeValueChange = {},
                    onVerifyClick = {
                        // TODO
                        navController.navigate(ChatroomNavItems.LOGIN.name)
                    },
                    onResendClick = {}
                )
            }

            // Home
            composable(route = ChatroomNavItems.HOME.name) {
                // TODO
            }
        }
    }
}