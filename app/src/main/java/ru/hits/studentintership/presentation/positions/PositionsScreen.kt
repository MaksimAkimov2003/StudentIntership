package ru.hits.studentintership.presentation.positions

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import kotlinx.coroutines.launch
import ru.hits.studentintership.presentation.common.ErrorSnackbar
import ru.hits.studentintership.presentation.positions.model.PositionsScreenEvent
import ru.hits.studentintership.presentation.positions.model.toPositionStatusEnum
import ru.hits.studentintership.presentation.positions.model.toUi
import ru.hits.studentintership.presentation.profile.model.ProfileEvent
import sh.calvin.reorderable.ReorderableItem
import sh.calvin.reorderable.rememberReorderableLazyListState

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
@Composable
fun PositionsScreen(
    navigateToCreationEditingScreen: (positionId: String?, positionsSize: Int, userId: String) -> Unit,
    viewModel: PositionsViewModel = hiltViewModel(),
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
            is PositionsScreenEvent.ShowSnackbar -> showSnackbar(
                event.message
            )
        }
    }

    LaunchedEffect(key1 = state.positions) {
        viewModel.getPositions()
    }

    val lazyListState = rememberLazyListState()
    val reorderableLazyListState = rememberReorderableLazyListState(lazyListState) { from, to ->
        viewModel.swapPositions(to.index, from.index)
    }

    Box {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp)
        ) {
            Text(
                text = "Позиции",
                fontSize = 30.sp,
                color = Color.Black,
                fontWeight = FontWeight.ExtraBold,
            )

            Spacer(modifier = Modifier.width(16.dp))

            LazyColumn(modifier = Modifier, state = lazyListState) {
                items(state.positions, key = { it.id }) { position ->
                    ReorderableItem(reorderableLazyListState, key = position.id) { isDragging ->
                        val interactionSource = remember { MutableInteractionSource() }

                        Card(
                            interactionSource = interactionSource,
                            onClick = { navigateToCreationEditingScreen(position.id, state.positions.size, viewModel.userId) },
                            elevation = CardDefaults.elevatedCardElevation(defaultElevation = 10.dp),
                            colors = CardDefaults.elevatedCardColors(
                                containerColor = Color.White,
                            ),
                            shape = RoundedCornerShape(24.dp),
                            modifier = Modifier
                                .padding(8.dp)

                        ) {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                modifier = Modifier.padding(10.dp)
                            ) {
                                Column(modifier = Modifier.weight(1f)) {
                                    Text(
                                        text = position.speciality.name,
                                        fontSize = 22.sp,
                                        color = Color.DarkGray,
                                        fontWeight = FontWeight.Medium,
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = position.company.name,
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = position.programLanguage.name,
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                    Spacer(modifier = Modifier.height(6.dp))
                                    Text(
                                        text = position.positionStatus.toPositionStatusEnum().toUi(),
                                        fontSize = 18.sp,
                                        color = Color.Black,
                                        fontWeight = FontWeight.Medium
                                    )
                                }
                                Spacer(modifier = Modifier.width(30.dp))
                                IconButton(onClick = { viewModel.deletePosition(position.id) }) {
                                    Icon(imageVector = Icons.Filled.Clear, contentDescription = null)
                                }
                                Spacer(modifier = Modifier.width(16.dp))
                                IconButton(
                                    onClick = { }, modifier = Modifier
                                        .size(30.dp)
                                        .draggableHandle()
                                ) {
                                    Icon(Icons.Rounded.Menu, contentDescription = null)
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.weight(1f))

            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                TextButton(
                    onClick = { navigateToCreationEditingScreen(null, state.positions.size, viewModel.userId) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(text = "Добавить позицию")
                }
                TextButton(onClick = { viewModel.savePositionPriority() }, modifier = Modifier.fillMaxWidth()) {
                    Text(text = "Сохранить")
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
