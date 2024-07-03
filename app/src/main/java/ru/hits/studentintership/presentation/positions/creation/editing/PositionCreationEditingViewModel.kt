package ru.hits.studentintership.presentation.positions.creation.editing

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import ru.hits.studentintership.data.other.OtherService
import ru.hits.studentintership.data.positions.api.PositionsService
import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.data.positions.model.CreatePositionBodyDto
import ru.hits.studentintership.data.positions.model.PositionDto
import ru.hits.studentintership.data.positions.model.ProgramLanguage
import ru.hits.studentintership.data.positions.model.Speciality
import ru.hits.studentintership.presentation.positions.creation.editing.model.PositionCreationEditingState
import ru.hits.studentintership.presentation.positions.creation.editing.navigation.PositionCreationEditingDestination
import ru.hits.studentintership.presentation.positions.model.PositionStatus
import ru.hits.studentintership.presentation.positions.model.toPositionStatusEnum
import ru.hits.studentintership.presentation.positions.model.toUi
import javax.inject.Inject

@HiltViewModel
class PositionCreationEditingViewModel @Inject constructor(
    private val positionsService: PositionsService,
    private val otherService: OtherService,
    savedStateHandle: SavedStateHandle,
) : ViewModel() {
    private val _state = MutableStateFlow(createInitialState())
    val state = _state.asStateFlow()

    private val currentScreenState: PositionCreationEditingState get() = _state.value

    private val positionId: String? = savedStateHandle[PositionCreationEditingDestination.positionIdArg]
    private val positionsSize: Int = requireNotNull(savedStateHandle[PositionCreationEditingDestination.positionsSizeArg])
    val userId: String = requireNotNull(savedStateHandle[PositionCreationEditingDestination.userId])
    private var positions: MutableList<PositionDto> = mutableListOf()

    init {
        getInfo()
        if (positionId != null)
            getPosition()
        else
            _state.update {
                it.copy(
                    status = "",
                    selectedStatus = PositionStatus.DOESNT_DO_ANYTHING,
                    company = "",
                    selectedCompany = null,
                    speciality = "",
                    selectedSpeciality = null,
                    programLanguage = "",
                    selectedProgramLanguage = null,
                )
            }
    }

    private fun createInitialState() = PositionCreationEditingState(
        position = null,
        positions = emptyList(),
        companies = emptyList(),
        specialities = emptyList(),
        programLanguages = emptyList(),
        status = "",
        selectedStatus = PositionStatus.DOESNT_DO_ANYTHING,
        company = "",
        selectedCompany = null,
        speciality = "",
        selectedSpeciality = null,
        programLanguage = "",
        selectedProgramLanguage = null,
    )

    private fun getPosition() = viewModelScope.launch {
        positions = positionsService.getPositions(userId).toMutableList()
        val position = positions.find { it.id == positionId }!!
        _state.update { state ->
            state.copy(
                position = position,
                positions = positions,
                status = position.positionStatus.toPositionStatusEnum().toUi(),
                selectedStatus = position.positionStatus.toPositionStatusEnum(),
                company = position.company.name,
                selectedCompany = position.company,
                speciality = position.speciality.name,
                selectedSpeciality = position.speciality,
                programLanguage = position.programLanguage.name,
                selectedProgramLanguage = position.programLanguage,
            )
        }
    }

    private fun getInfo() = viewModelScope.launch {
        val companiesRes = async { otherService.getCompanies() }
        val specialitiesRes = async { otherService.getSpecialities() }
        val programLanguagesRes = async { otherService.getProgramLanguages() }

        val result = Triple(companiesRes.await(), specialitiesRes.await(), programLanguagesRes.await())

        val companies = result.first
        val specialities = result.second
        val programLanguages = result.third

        _state.update {
            it.copy(
                companies = companies,
                specialities = specialities,
                programLanguages = programLanguages,
                selectedCompany = companies.firstOrNull(),
                selectedSpeciality = specialities.firstOrNull(),
                selectedProgramLanguage = programLanguages.firstOrNull()
            )
        }
    }

    fun createPosition(priority: Int, status: String, programLanguageId: String, specialityId: String, companyId: String) = viewModelScope.launch {
        positionsService.createPosition(
            createPositionBodyDto = CreatePositionBodyDto(
                priority = positionsSize,
                positionStatus = status,
                programLanguageId = programLanguageId,
                specialityId = specialityId,
                companyId = companyId,
            )
        )
        _state.update {
            it.copy(
                status = "",
                selectedStatus = PositionStatus.DOESNT_DO_ANYTHING,
                company = "",
                selectedCompany = null,
                speciality = "",
                selectedSpeciality = null,
                programLanguage = "",
                selectedProgramLanguage = null,
            )
        }
    }

    fun saveChanges(status: String) = viewModelScope.launch {
        changePositionStatus(positionId!!, status)
    }

    private fun changePositionStatus(positionId: String, status: String) = viewModelScope.launch {
        positionsService.changePositionStatus(positionId = positionId, status = status)
    }

    // COMPANY
    private fun onCompanyChange(company: String) =
        _state.update {
            it.copy(
                company = company,
            )
        }

    fun onSelectedCompanyChange(company: Company) {
        _state.update {
            it.copy(
                selectedCompany = company,
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

    fun onCompanySelectDialogConfirmClick(company: String) {
        onCompanyChange(company)
        _state.update {
            it.copy(
                isCompanySelectDialogOpen = false,
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

    // SPECIALITY
    fun onSelectedSpecialityChange(speciality: Speciality) {
        _state.update {
            it.copy(
                selectedSpeciality = speciality,
            )
        }
    }

    fun onSpecialitySelectDialogCancelClick() {
        _state.update {
            it.copy(
                isSpecialitySelectDialogOpen = false,
            )
        }
    }

    private fun onSpecialityChange(speciality: String) =
        _state.update {
            it.copy(
                speciality = speciality,
            )
        }

    fun onSpecialitySelectDialogConfirmClick(speciality: String) {
        onSpecialityChange(speciality)
        _state.update {
            it.copy(
                isSpecialitySelectDialogOpen = false,
            )
        }
    }

    fun onSpecialityTextFieldClick() {
        _state.update {
            it.copy(
                isSpecialitySelectDialogOpen = true
            )
        }
    }

    // PROGRAM LANGUAGE
    fun onSelectedProgramLanguageChange(programLanguage: ProgramLanguage) {
        _state.update {
            it.copy(
                selectedProgramLanguage = programLanguage,
            )
        }
    }

    fun onProgramLanguageSelectDialogCancelClick() {
        _state.update {
            it.copy(
                isProgramLanguageSelectDialogOpen = false,
            )
        }
    }

    private fun onProgramLanguageChange(programLanguage: String) =
        _state.update {
            it.copy(
                programLanguage = programLanguage,
            )
        }

    fun onProgramLanguageSelectDialogConfirmClick(programLanguage: String) {
        onProgramLanguageChange(programLanguage)
        _state.update {
            it.copy(
                isProgramLanguageSelectDialogOpen = false,
            )
        }
    }

    fun onProgramLanguageTextFieldClick() {
        _state.update {
            it.copy(
                isProgramLanguageSelectDialogOpen = true
            )
        }
    }

    // POSITION STATUS
    private fun onPositionStatusChange(status: String) =
        _state.update {
            it.copy(
                status = status,
            )
        }

    fun onSelectedPositionStatusChange(status: PositionStatus) {
        _state.update {
            it.copy(
                selectedStatus = status,
            )
        }
    }

    fun onPositionStatusTextFieldClick() {
        _state.update {
            it.copy(
                isPositionStatusSelectDialogOpen = true
            )
        }
    }

    fun onPositionStatusSelectDialogCancelClick() {
        _state.update {
            it.copy(
                isPositionStatusSelectDialogOpen = false,
            )
        }
    }

    fun onPositionStatusSelectDialogConfirmClick(status: String) {
        onPositionStatusChange(status)
        _state.update {
            it.copy(
                isPositionStatusSelectDialogOpen = false,
            )
        }
    }
}

