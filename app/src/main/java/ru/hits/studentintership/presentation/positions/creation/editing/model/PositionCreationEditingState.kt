package ru.hits.studentintership.presentation.positions.creation.editing.model

import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.data.positions.model.PositionDto
import ru.hits.studentintership.data.positions.model.ProgramLanguage
import ru.hits.studentintership.data.positions.model.Speciality
import ru.hits.studentintership.presentation.positions.model.PositionStatus

data class PositionCreationEditingState(
    val position: PositionDto?,
    val positions: List<PositionDto>,
    val companies: List<Company>,
    val specialities: List<Speciality>,
    val programLanguages: List<ProgramLanguage>,
    val status: String,
    val selectedStatus: PositionStatus,
    val company: String,
    val selectedCompany: Company?,
    val speciality: String,
    val selectedSpeciality: Speciality?,
    val programLanguage: String,
    val selectedProgramLanguage: ProgramLanguage?,
    val isCompanySelectDialogOpen: Boolean = false,
    val isSpecialitySelectDialogOpen: Boolean = false,
    val isProgramLanguageSelectDialogOpen: Boolean = false,
    val isPositionStatusSelectDialogOpen: Boolean = false,
)
