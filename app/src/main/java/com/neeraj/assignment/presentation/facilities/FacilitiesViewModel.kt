package com.neeraj.assignment.presentation.facilities

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.neeraj.assignment.domain.model.Data
import com.neeraj.assignment.domain.use_cases.GetFacilitiesUseCase
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers

class FacilitiesViewModel(
    private val getFacilitiesUseCase: GetFacilitiesUseCase
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()
    private val _state = mutableStateOf(FacilitiesListState())
    val state: State<FacilitiesListState> = _state

    init {
        getFacilities()
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.dispose()
    }

    fun onOptionSelected(option: Data.Facility.Option) {
        var selectedOptions = _state.value.selectedOptions
        if (option.facilityId == "1") {
            selectedOptions = mapOf()
        }
        selectedOptions = selectedOptions.toMutableMap().apply {
            put(option.facilityId, option)
        }
        _state.value = _state.value.let {
            FacilitiesListState(
                facilities = it.facilities,
                exclusionPairs = it.exclusionPairs,
                selectedOptions = selectedOptions
            )
        }
    }

    fun clearSelection() {
        _state.value = _state.value.let {
            FacilitiesListState(
                facilities = it.facilities,
                exclusionPairs = it.exclusionPairs,
                selectedOptions = mapOf()
            )
        }
    }

    fun applySelection() {
        clearSelection()
    }

    private fun getFacilities() {
        _state.value = FacilitiesListState(
            isLoading = true,
        )
        getFacilitiesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({data ->
                _state.value =
                    FacilitiesListState(
                        facilities = data?.facilities ?: emptyList(),
                        exclusionPairs = data?.exclusions ?: emptyList(),
                        isLoading = false,
                        error = ""
                    )
            }, {
                _state.value = FacilitiesListState(
                    error = it.message ?: "An unexpected error occured",
                    isLoading = false,
                )
            })
            .also {
                compositeDisposable.add(it)
            }
    }
}