package com.example.newsroom.domain.usecases.app_entry

import com.example.newsroom.domain.manager.LocalUserManager
import kotlinx.coroutines.flow.Flow

class ReadAppEntry(
    private val localUserManager: LocalUserManager
) {

     operator fun invoke(): Flow<Boolean>{
        return localUserManager.readAppEntry()
    }
}