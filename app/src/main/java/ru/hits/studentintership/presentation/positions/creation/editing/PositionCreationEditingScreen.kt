package ru.hits.studentintership.presentation.positions.creation.editing

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.hits.studentintership.presentation.common.ErrorSnackbar
import ru.hits.studentintership.presentation.companies.model.CompaniesScreenEvent
import ru.hits.studentintership.presentation.positions.creation.editing.model.PositionCreatingEditingScreenEvent
import ru.hits.studentintership.presentation.positions.model.PositionStatus

@Composable
fun PositionCreationEditingScreen(
    isCreation: Boolean,
    navigateToPositionsScreen: (userId: String) -> Unit,
    viewModel: PositionCreationEditingViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsState()

    val snackbarState = remember { SnackbarHostState() }
    val snackbarScope = rememberCoroutineScope()
    val showSnackbar: (message: String) -> Unit = { message ->
        snackbarScope.launch {
            snackbarState.showSnackbar(message)
        }
    }

    viewModel.screenEvents.CollectEvent { event ->
        when (event) {
            is PositionCreatingEditingScreenEvent.ShowSnackbar -> showSnackbar(
                event.message
            )
        }
    }

    val positionStatuses = PositionStatus.entries.toMutableList()
    positionStatuses.remove(PositionStatus.CONFIRMED_RECEIVED_OFFER)
    if (state.selectedStatus != PositionStatus.CONFIRMED_RECEIVED_OFFER) {
        positionStatuses.remove(PositionStatus.ACCEPTED_OFFER)
        positionStatuses.remove(PositionStatus.REJECTED_OFFER)
    }

    if (state.isCompanySelectDialogOpen)
        state.selectedCompany?.let { company ->
            CompanySelectDialog(
                companies = state.companies,
                selectedCompany = company,
                onCompanySelect = { viewModel.onSelectedCompanyChange(it) },
                onCompanySelectCancelClick = { viewModel.onCompanySelectDialogCancelClick() },
                onCompanySelectConfirmClick = { viewModel.onCompanySelectDialogConfirmClick(it) }
            )
        }

    if (state.isSpecialitySelectDialogOpen)
        state.selectedSpeciality?.let { speciality ->
            SpecialitySelectDialog(
                specialities = state.specialities,
                selectedSpeciality = speciality,
                onSpecialitySelect = { viewModel.onSelectedSpecialityChange(it) },
                onSpecialitySelectCancelClick = { viewModel.onSpecialitySelectDialogCancelClick() },
                onSpecialitySelectConfirmClick = { viewModel.onSpecialitySelectDialogConfirmClick(it) },
            )
        }

    if (state.isProgramLanguageSelectDialogOpen)
        state.selectedProgramLanguage?.let { programLanguage ->
            ProgramLanguageSelectDialog(
                programLanguages = state.programLanguages,
                selectedProgramLanguage = programLanguage,
                onProgramLanguageSelect = { viewModel.onSelectedProgramLanguageChange(it) },
                onProgramLanguageSelectDialogCancelClick = { viewModel.onProgramLanguageSelectDialogCancelClick() },
                onProgramLanguageSelectDialogConfirmClick = { viewModel.onProgramLanguageSelectDialogConfirmClick(it) },
            )
        }

    if (state.isPositionStatusSelectDialogOpen)
        PositionStatusSelectDialog(
            statuses = positionStatuses,
            selectedStatus = state.selectedStatus,
            onStatusSelect = { viewModel.onSelectedPositionStatusChange(it) },
            onStatusSelectDialogCancelClick = { viewModel.onPositionStatusSelectDialogCancelClick() },
            onStatusSelectDialogConfirmClick = { viewModel.onPositionStatusSelectDialogConfirmClick(it) },
        )

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            // Company
            TextField(
                value = state.company,
                onValueChange = {},
                label = { Text(text = "Компания") },
                trailingIcon = if (isCreation) {
                    {
                        IconButton(onClick = viewModel::onCompanyTextFieldClick) {
                            Image(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    {}
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )
            Spacer(modifier = Modifier.height(16.dp))

            // Speciality
            TextField(
                value = state.speciality,
                onValueChange = {},
                label = { Text(text = "Специальность") },
                trailingIcon = if (isCreation) {
                    {
                        IconButton(onClick = viewModel::onSpecialityTextFieldClick) {
                            Image(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    {}
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth(),
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Program language
            TextField(
                value = state.programLanguage,
                onValueChange = {},
                label = { Text(text = "Язык программирования") },
                trailingIcon = if (isCreation) {
                    {
                        IconButton(onClick = viewModel::onProgramLanguageTextFieldClick) {
                            Image(
                                imageVector = Icons.Default.KeyboardArrowDown,
                                contentDescription = null,
                            )
                        }
                    }
                } else {
                    {}
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(16.dp))

            TextField(
                value = state.status,
                onValueChange = {},
                label = { Text(text = "Статус") },
                trailingIcon = {
                    IconButton(onClick = viewModel::onPositionStatusTextFieldClick) {
                        Image(
                            imageVector = Icons.Default.KeyboardArrowDown,
                            contentDescription = null,
                        )
                    }
                },
                readOnly = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.weight(1f))

            TextButton(
                onClick = if (isCreation) {
                    {
                        viewModel.createPosition(
                            priority = state.positions.size,
                            status = state.selectedStatus.name,
                            programLanguageId = state.selectedProgramLanguage!!.id,
                            specialityId = state.selectedSpeciality!!.id,
                            companyId = state.selectedCompany!!.id
                        )
                        navigateToPositionsScreen(viewModel.userId)
                    }
                } else {
                    {
                        viewModel.saveChanges(status = state.selectedStatus.name)
                        navigateToPositionsScreen(viewModel.userId)
                    }
                },
                enabled = state.programLanguage != "" && state.speciality != "" && state.company != "",
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = if (isCreation) "Добавить позицию" else "Сохранить изменения")
            }

        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .padding(16.dp)
        ) {
            ErrorSnackbar(
                snackbarHostState = snackbarState
            )
        }
    }
}