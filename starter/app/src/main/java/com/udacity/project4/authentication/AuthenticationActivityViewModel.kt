package com.udacity.project4.authentication

import android.app.Application
import androidx.lifecycle.map
import com.udacity.project4.base.BaseViewModel

class AuthenticationActivityViewModel(app: Application) : BaseViewModel(app) {
    enum class AuthenticationState {
        AUTHENTICATED, UNAUTHENTICATED
    }

    val authenticationState = FirebaseUserLiveData().map { when(it) {
            null -> AuthenticationState.UNAUTHENTICATED
            else -> AuthenticationState.AUTHENTICATED
        }
    }
}