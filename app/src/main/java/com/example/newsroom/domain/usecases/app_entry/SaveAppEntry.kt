package com.example.newsroom.domain.usecases.app_entry

import com.example.newsroom.domain.manager.LocalUserManager

class SaveAppEntry (
    private val localUserManager: LocalUserManager
){
    suspend operator fun invoke(){
        localUserManager.saveAppEntry()
    }

}