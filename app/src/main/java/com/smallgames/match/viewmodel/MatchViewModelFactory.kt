package com.smallgames.match.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.smallgames.match.data.Storage
import javax.inject.Inject

class MatchViewModelFactory @Inject constructor(val storage: Storage) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return MatchViewModel(storage = storage) as T
    }
}