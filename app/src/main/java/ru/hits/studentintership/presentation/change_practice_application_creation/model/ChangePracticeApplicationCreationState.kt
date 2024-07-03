package ru.hits.studentintership.presentation.change_practice_application_creation.model

import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.common.data.model.Semester

data class ChangePracticeApplicationCreationState(
    val comment: String,
    val company: String,
    val selectedCompany: Company?,
    val notPartner: String?,
    val semester: String,
    val selectedSemester: Semester?,
    val semesters: List<Semester>,
    val companies: List<Company>,
    val isCompanySelectDialogOpen: Boolean = false,
    val isSemesterSelectDialogOpen: Boolean = false,
)
