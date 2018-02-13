package com.android.movies.interactor.preferences

import com.android.movies.Result
import com.android.movies.interactor.Interactor
import com.android.movies.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesInteractor @Inject constructor(
        private val preferencesRepository: PreferencesRepository)
    : Interactor<String, PreferencesInteractor.PreferencesBundle> () {

    override fun run(params: PreferencesBundle) = when {

        params.type == EDIT_TYPE.PUT ->
            if (params.key == null || params.value == null) throw IllegalArgumentException()
            else preferencesRepository.put(params.key, params.value)

        params.type == EDIT_TYPE.GET ->
            if (params.key == null) throw IllegalArgumentException()
            else preferencesRepository.get(params.key)

        params.type == EDIT_TYPE.DELETE ->
            if (params.key == null) throw IllegalArgumentException()
            else preferencesRepository.delete(params.key)

        params.type == EDIT_TYPE.DROP -> preferencesRepository.clear()

        else -> Result.Failure()
    }


    companion object {
        enum class EDIT_TYPE { PUT, GET, DELETE, DROP }
    }

    data class PreferencesBundle(val type: EDIT_TYPE,
                                 val key: String? = null,
                                 val value: String? = null)

}