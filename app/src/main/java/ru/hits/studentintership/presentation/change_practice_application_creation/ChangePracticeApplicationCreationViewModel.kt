package ru.hits.studentintership.presentation.change_practice_application_creation

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.common.data.model.ChangePracticeApplicationCreateBody
import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.common.data.model.Semester
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.presentation.change_practice_application_creation.model.ChangePracticeApplicationCreationState
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class ChangePracticeApplicationCreationViewModel @Inject constructor(
    private val otherService: OtherService,
) : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    init {
        getSemesters()
        getCompanies()
    }

    private fun createInitialState() =
        ChangePracticeApplicationCreationState(
            comment = "",
            company = "",
            selectedCompany = null,
            notPartner = null,
            semester = "",
            selectedSemester = null,
            semesters = emptyList(),
            companies = emptyList(),
        )

    fun onNotPartnerChange(partner: String) {
        _state.update {
            it.copy(notPartner = partner)
        }
    }
    fun onCommentChange(comment: String) {
        _state.update {
            it.copy(comment = comment)
        }
    }

    fun onSemesterTextFieldClick() {
        _state.update {
            it.copy(
                isSemesterSelectDialogOpen = true
            )
        }
    }

    fun onCompanyTextFieldClick() {
        _state.update {
            it.copy(
                isCompanySelectDialogOpen = true
            )
        }
    }

    fun onSelectedSemesterChange(semester: Semester) {
        _state.update {
            it.copy(
                selectedSemester = semester,
            )
        }
    }

    fun onSelectedCompanyChange(company: Company) {
        _state.update {
            it.copy(
                selectedCompany = company,
            )
        }
    }

    fun onSemesterSelectDialogCancelClick() {
        _state.update {
            it.copy(
                isSemesterSelectDialogOpen = false,
            )
        }
    }

    fun onCompanySelectDialogCancelClick() {
        _state.update {
            it.copy(
                isCompanySelectDialogOpen = false,
            )
        }
    }

    private fun onSemesterChange(semester: String) =
        _state.update {
            it.copy(
                semester = semester,
            )
        }

    private fun onCompanyChange(company: String) =
        _state.update {
            it.copy(
                company = company,
            )
        }

    fun onSemesterSelectDialogConfirmClick(semester: String) {
        onSemesterChange(semester)
        _state.update {
            it.copy(
                isSemesterSelectDialogOpen = false,
            )
        }
    }

    fun onCompanySelectDialogConfirmClick(company: String) {
        onCompanyChange(company)
        _state.update {
            it.copy(
                isCompanySelectDialogOpen = false,
            )
        }
    }

    private fun getSemesters() = viewModelScope.launch {
        val allSemesters = otherService.getSemesters()
        val filteredSemesters = allSemesters.filter { compareDates(getCurrentDate(), it.changeCompanyApplicationDeadline) < 0 }
        _state.update {
            it.copy(
                semesters = filteredSemesters,
                selectedSemester = filteredSemesters.firstOrNull(),
            )
        }
    }

    fun getCompanies() = viewModelScope.launch {
        val companies = otherService.getCompanies().toMutableList()
        companies.add(Company("", true, "Другая", null))
        _state.update {
            it.copy(
                companies = companies,
                selectedCompany = companies.firstOrNull()
            )
        }
    }

    fun createChangePracticeApplication(comment: String, notPartner: String?, companyId: String, semesterId: String) = viewModelScope.launch {
        otherService.createChangePracticeApplication(
            changePracticeApplicationCreateBody = if (notPartner != null )
             ChangePracticeApplicationCreateBody(
                comment = comment,
                notPartner = notPartner,
                companyId = "",
                semesterId = semesterId,
            ) else ChangePracticeApplicationCreateBody(
                comment = comment,
                notPartner = null,
                companyId = companyId,
                semesterId = semesterId,
            )
        )
    }

    private fun getCurrentDate(): String {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val currentDate = Date()
        return sdf.format(currentDate)
    }

    fun compareDates(currentDate: String, otherDate: String): Int {
        val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
        val currentDateObj = sdf.parse(currentDate)
        val otherDateObj = sdf.parse(otherDate)

        return currentDateObj.compareTo(otherDateObj)
    }
}