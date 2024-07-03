package ru.hits.studentintership.presentation.companies

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.hits.studentintership.presentation.common.ErrorSnackbar
import ru.hits.studentintership.presentation.companies.model.CompaniesScreenEvent
import ru.hits.studentintership.presentation.companies.model.CompanyWithWishes
import ru.hits.studentintership.presentation.positions.model.PositionsScreenEvent
import ru.hits.studentintership.presentation.positions.model.toPositionStatusEnum
import ru.hits.studentintership.presentation.positions.model.toUi

@Composable
fun CompaniesScreen(
    viewModel: CompaniesViewModel = hiltViewModel()
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
            is CompaniesScreenEvent.ShowSnackbar -> showSnackbar(
                event.message
            )
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Компании",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
            )
            Spacer(modifier = Modifier.height(16.dp))
            LazyColumn() {
                items(state.companies) {
                    CompanyWithWishes(company = it)
                }
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CompanyWithWishes(company: CompanyWithWishes) {
    var isExpanded by remember { mutableStateOf(false) }
    Card(
        onClick = { isExpanded = !isExpanded },
        elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.White,
        ),
        shape = RoundedCornerShape(24.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)

    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(10.dp)
        ) {
            Column(modifier = Modifier) {
                Text(
                    text = company.company.name,
                    fontSize = 22.sp,
                    color = Color.DarkGray,
                    fontWeight = FontWeight.Medium,
                )
                company.company.websiteLink?.let {
                    Spacer(modifier = Modifier.height(6.dp))
                    Text(
                        text = it,
                        fontSize = 18.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Medium
                    )
                }
                if (isExpanded) {
                    Spacer(modifier = Modifier.height(6.dp))
                    if (company.wishes.isEmpty()) {
                        Text(
                            text = "У компании нет пожеланий",
                            fontSize = 18.sp,
                            color = Color.Black,
                            fontWeight = FontWeight.Medium
                        )
                    } else
                        company.wishes.forEach {
                            Text(
                                text = it,
                                fontSize = 18.sp,
                                color = Color.Black,
                                fontWeight = FontWeight.Medium
                            )
                        }
                }
            }
        }
    }
}