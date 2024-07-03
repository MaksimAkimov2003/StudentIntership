package ru.hits.studentintership.presentation.change_practice_application_creation

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import ru.hits.studentintership.presentation.positions.creation.editing.CompanySelectDialog
import ru.hits.studentintership.presentation.positions.creation.editing.SemesterSelectDialog

@Composable
fun ChangePracticeApplicationCreationScreen(
    navigateToProfile: () -> Unit,
    viewModel: ChangePracticeApplicationCreationViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsState()

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

    if (state.isSemesterSelectDialogOpen)
        state.selectedSemester?.let { semester ->
            SemesterSelectDialog(
                semesters = state.semesters,
                selectedSemester = semester,
                onSemesterSelect = { viewModel.onSelectedSemesterChange(it) },
                onSemesterSelectCancelClick = { viewModel.onSemesterSelectDialogCancelClick() },
                onSemesterSelectConfirmClick = { viewModel.onSemesterSelectDialogConfirmClick(it) }
            )
        }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        TextField(value = state.comment, onValueChange = { viewModel.onCommentChange(it) }, label = {
            Text(text = "Комментарий")
        }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        // Company
        TextField(
            value = state.company,
            onValueChange = {},
            label = { Text(text = "Компания") },
            trailingIcon =
            {
                IconButton(onClick = viewModel::onCompanyTextFieldClick) {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                    )
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(16.dp))

        // Semester
        TextField(
            value = state.semester,
            onValueChange = {},
            label = { Text(text = "Семестр") },
            trailingIcon =
            {
                IconButton(onClick = viewModel::onSemesterTextFieldClick) {
                    Image(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                    )
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth()
        )

        if (state.company == "Другая") {
            Spacer(modifier = Modifier.height(16.dp))
            TextField(
                value = state.notPartner ?: "",
                onValueChange = { viewModel.onNotPartnerChange(it) },
                label = {
                    Text(text = "Компания не партнер")
                },
                modifier = Modifier.fillMaxWidth(),
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        TextButton(
            onClick = {
                viewModel.createChangePracticeApplication(
                    comment = state.comment,
                    notPartner = state.notPartner,
                    companyId = state.selectedCompany!!.id,
                    semesterId = state.selectedSemester!!.id
                )
                navigateToProfile()
            },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(text = "Создать заявку")
        }
    }
}