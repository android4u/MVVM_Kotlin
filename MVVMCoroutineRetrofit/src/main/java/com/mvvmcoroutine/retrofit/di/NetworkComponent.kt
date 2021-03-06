package com.mvvmcoroutine.retrofit.di

import com.mvvm.common.di.RetroRxModule
import com.mvvmcoroutine.retrofit.login.viewmodel.LoginViewModelFactory
import com.mvvmcoroutine.retrofit.userlist.viewmodel.UserListViewModelFactory
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [NetworkModule::class])
interface NetworkComponent {
    fun inject(loginViewModelFactory: LoginViewModelFactory)
    fun inject(userListViewModelFactory: UserListViewModelFactory)
}