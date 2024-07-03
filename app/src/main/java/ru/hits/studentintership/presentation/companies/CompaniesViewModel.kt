package ru.hits.studentintership.presentation.companies

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.companies.model.CompaniesState
import ru.hits.studentintership.presentation.companies.model.CompanyWithWishes
import javax.inject.Inject

@HiltViewModel
class CompaniesViewModel @Inject constructor(
    private val otherService: OtherService,
) : ViewModel() {

    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    init {
        getCompaniesInfo()
    }

    private fun createInitialState() = CompaniesState(
        companies = emptyList()
    )

    private fun getCompaniesInfo() = viewModelScope.launch {
        val companies = otherService.getCompanies()

        val companiesWithWishes = companies.map { company ->
            val wishes = otherService.getCompaniesWishes(companyId = company.id)
            CompanyWithWishes(
                company = company,
                wishes = wishes.map { wishes ->
                    wishes.internAmount + " " + wishes.speciality.name + "а на " + wishes.programLanguage?.name + " (" + wishes.comment + ")"
                },
            )
        }

        _state.update {
            it.copy(
                companies = companiesWithWishes,
            )
        }

    }
}