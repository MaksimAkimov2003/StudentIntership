package ru.hits.studentintership.presentation.positions.creation.editing

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import ru.hits.studentintership.common.data.model.Company
import ru.hits.studentintership.common.data.model.Semester
import ru.hits.studentintership.data.positions.model.ProgramLanguage
import ru.hits.studentintership.data.positions.model.Speciality
import ru.hits.studentintership.presentation.positions.model.PositionStatus
import ru.hits.studentintership.presentation.positions.model.toUi

// COMPANY
@Composable
fun CompanySelectDialog(
    companies: List<Company>,
    selectedCompany: Company,
    onCompanySelect: (Company) -> Unit,
    onCompanySelectCancelClick: () -> Unit,
    onCompanySelectConfirmClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onCompanySelectCancelClick) {
        Card(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(companies) { company ->
                        Column(
                            modifier = Modifier.selectable(
                                selected = company == selectedCompany
                            ) { onCompanySelect(company) }
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            CompanyItem(company = company, selectedCompany = selectedCompany)
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(thickness = 1.dp)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = onCompanySelectCancelClick
                    ) {
                        Text(text = "Отмена")
                    }
                    TextButton(
                        onClick = { onCompanySelectConfirmClick(selectedCompany.name) }
                    ) {
                        Text("Выбрать")
                    }
                }
            }
        }
    }
}

@Composable
fun CompanyItem(company: Company, selectedCompany: Company) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = company.name, color = Color.Black)
        RadioButton(
            selected = company == selectedCompany,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black, unselectedColor = Color.Gray)
        )
    }
}

// SPECIALITY
@Composable
fun SpecialitySelectDialog(
    specialities: List<Speciality>,
    selectedSpeciality: Speciality,
    onSpecialitySelect: (Speciality) -> Unit,
    onSpecialitySelectCancelClick: () -> Unit,
    onSpecialitySelectConfirmClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onSpecialitySelectCancelClick) {
        Card(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(specialities) { speciality ->
                        Column(
                            modifier = Modifier.selectable(
                                selected = speciality == selectedSpeciality
                            ) { onSpecialitySelect(speciality) }
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            SpecialityItem(speciality = speciality, selectedSpeciality = selectedSpeciality)
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(thickness = 1.dp)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = onSpecialitySelectCancelClick
                    ) {
                        Text(text = "Отмена")
                    }
                    TextButton(
                        onClick = { onSpecialitySelectConfirmClick(selectedSpeciality.name) }
                    ) {
                        Text("Выбрать")
                    }
                }
            }
        }
    }
}

@Composable
fun SpecialityItem(speciality: Speciality, selectedSpeciality: Speciality) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = speciality.name, color = Color.Black)
        RadioButton(
            selected = speciality == selectedSpeciality,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black, unselectedColor = Color.Gray)
        )
    }
}

// PROGRAM LANGUAGE
@Composable
fun ProgramLanguageSelectDialog(
    programLanguages: List<ProgramLanguage>,
    selectedProgramLanguage: ProgramLanguage,
    onProgramLanguageSelect: (ProgramLanguage) -> Unit,
    onProgramLanguageSelectDialogCancelClick: () -> Unit,
    onProgramLanguageSelectDialogConfirmClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onProgramLanguageSelectDialogCancelClick) {
        Card(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(programLanguages) { programLanguage ->
                        Column(
                            modifier = Modifier.selectable(
                                selected = programLanguage == selectedProgramLanguage
                            ) { onProgramLanguageSelect(programLanguage) }
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            ProgramLanguageItem(programLanguage = programLanguage, selectedProgramLanguage = selectedProgramLanguage)
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(thickness = 1.dp)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = onProgramLanguageSelectDialogCancelClick
                    ) {
                        Text(text = "Отмена")
                    }
                    TextButton(
                        onClick = { onProgramLanguageSelectDialogConfirmClick(selectedProgramLanguage.name) }
                    ) {
                        Text("Выбрать")
                    }
                }
            }
        }
    }
}

@Composable
fun ProgramLanguageItem(programLanguage: ProgramLanguage, selectedProgramLanguage: ProgramLanguage) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = programLanguage.name, color = Color.Black)
        RadioButton(
            selected = programLanguage == selectedProgramLanguage,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black, unselectedColor = Color.Gray)
        )
    }
}

// POSITION STATUS
@Composable
fun PositionStatusSelectDialog(
    statuses: List<PositionStatus>,
    selectedStatus: PositionStatus,
    onStatusSelect: (PositionStatus) -> Unit,
    onStatusSelectDialogCancelClick: () -> Unit,
    onStatusSelectDialogConfirmClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onStatusSelectDialogCancelClick) {
        Card(
            modifier = Modifier
                .width(350.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(statuses) { status ->
                        Column(
                            modifier = Modifier.selectable(
                                selected = status == selectedStatus
                            ) { onStatusSelect(status) }
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            StatusItem(status = status, selectedStatus = selectedStatus)
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(thickness = 1.dp)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = onStatusSelectDialogCancelClick
                    ) {
                        Text(text = "Отмена")
                    }
                    TextButton(
                        onClick = { onStatusSelectDialogConfirmClick(selectedStatus.toUi()) }
                    ) {
                        Text("Выбрать")
                    }
                }
            }
        }
    }
}

@Composable
fun StatusItem(status: PositionStatus, selectedStatus: PositionStatus) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = status.toUi(), color = Color.Black, modifier = Modifier)
        Spacer(modifier = Modifier.weight(0.5f))
        RadioButton(
            selected = status == selectedStatus,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black, unselectedColor = Color.Gray),
        )
    }
}

// SEMESTER
@Composable
fun SemesterSelectDialog(
    semesters: List<Semester>,
    selectedSemester: Semester,
    onSemesterSelect: (Semester) -> Unit,
    onSemesterSelectCancelClick: () -> Unit,
    onSemesterSelectConfirmClick: (String) -> Unit,
) {
    Dialog(onDismissRequest = onSemesterSelectCancelClick) {
        Card(
            modifier = Modifier
                .width(280.dp)
                .wrapContentHeight(),
            shape = RoundedCornerShape(28.dp),
        ) {
            Column {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxWidth()
                        .selectableGroup()
                ) {
                    items(semesters) { semester ->
                        Column(
                            modifier = Modifier.selectable(
                                selected = semester == selectedSemester
                            ) { onSemesterSelect(semester) }
                        ) {
                            Spacer(modifier = Modifier.height(20.dp))
                            SemesterItem(semester = semester, selectedSemester = selectedSemester)
                            Spacer(modifier = Modifier.height(20.dp))
                            Divider(thickness = 1.dp)
                        }
                    }
                }
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.Bottom
                ) {
                    TextButton(
                        onClick = onSemesterSelectCancelClick
                    ) {
                        Text(text = "Отмена")
                    }
                    TextButton(
                        onClick = { onSemesterSelectConfirmClick(selectedSemester.number.toString()) }
                    ) {
                        Text("Выбрать")
                    }
                }
            }
        }
    }
}

@Composable
fun SemesterItem(semester: Semester, selectedSemester: Semester) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = semester.number.toString() + " (" + semester.startDate + " - " + semester.endDate + ")", color = Color.Black)
        RadioButton(
            selected = semester == selectedSemester,
            onClick = null,
            colors = RadioButtonDefaults.colors(selectedColor = Color.Black, unselectedColor = Color.Gray)
        )
    }
}