package ru.hits.studentintership.navigation

import android.net.Uri
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import ru.hits.studentintership.presentation.change_practice_application_creation.ChangePracticeApplicationCreationScreen
import ru.hits.studentintership.presentation.change_practice_application_creation.navigation.ChangePracticeApplicationCreationScreenDestination
import ru.hits.studentintership.presentation.companies.CompaniesScreen
import ru.hits.studentintership.presentation.companies.navigation.CompaniesDestination
import ru.hits.studentintership.presentation.launch.LaunchScreen
import ru.hits.studentintership.presentation.launch.navigation.LaunchDestination
import ru.hits.studentintership.presentation.login.navigation.LoginDestination
import ru.hits.studentintership.presentation.login.ui.LoginScreen
import ru.hits.studentintership.presentation.meetings.MeetingsScreen
import ru.hits.studentintership.presentation.meetings.navigation.MeetingsDestination
import ru.hits.studentintership.presentation.photo.PickPhotoScreen
import ru.hits.studentintership.presentation.photo.navigation.PickPhotoDestination
import ru.hits.studentintership.presentation.positions.PositionsScreen
import ru.hits.studentintership.presentation.positions.creation.editing.PositionCreationEditingScreen
import ru.hits.studentintership.presentation.positions.creation.editing.navigation.PositionCreationEditingDestination
import ru.hits.studentintership.presentation.positions.navigation.PositionsDestination
import ru.hits.studentintership.presentation.profile.ProfileScreen
import ru.hits.studentintership.presentation.profile.navigation.ProfileDestination
import ru.hits.studentintership.presentation.task.TaskScreen
import ru.hits.studentintership.presentation.task.navigation.TaskDestination
import ru.hits.studentintership.presentation.task.solution_creation_editing.navigation.SolutionCreatingEditingDestination
import ru.hits.studentintership.presentation.task.solution_creation_editing.SolutionCreatingEditingScreen
import ru.hits.studentintership.presentation.tasks.TasksScreen
import ru.hits.studentintership.presentation.tasks.navigation.TasksDestination

@Composable
fun SetupNavGraph(navController: NavHostController) {
    NavHost(navController = navController, startDestination = LaunchDestination.route) {
        composable(route = LaunchDestination.route) {
            LaunchScreen(navigateToProfile = { navController.navigate(ProfileDestination.dest) },
                navigateToLogin = { navController.navigate(LoginDestination.dest) })
        }

        composable(
            route = LoginDestination.route,
        ) {
            LoginScreen(navigateNext = { navController.navigate(ProfileDestination.dest) })
        }

        composable(
            route = PositionsDestination.route,
            arguments = listOf(
                navArgument(PositionsDestination.userId) {
                    type = NavType.StringType
                },
            )
        ) {
            PositionsScreen(
                navigateToCreationEditingScreen = { positionId, positionsSize, userId ->
                    navController.navigate(PositionCreationEditingDestination.destWithArgs(positionId, positionsSize, userId))
                }
            )
        }

        composable(
            route = PositionCreationEditingDestination.route,
            arguments = listOf(
                navArgument(PositionCreationEditingDestination.positionIdArg) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(PositionCreationEditingDestination.positionsSizeArg) {
                    type = NavType.IntType
                },
                navArgument(PositionCreationEditingDestination.userId) {
                    type = NavType.StringType
                },
            )
        ) { navBackStackEntry ->
            val positionId = navBackStackEntry.arguments?.getString(PositionCreationEditingDestination.positionIdArg)
            PositionCreationEditingScreen(
                isCreation = positionId == null,
                navigateToPositionsScreen = { userId -> navController.navigate(PositionsDestination.destWithArgs(userId)) })
        }

        composable(route = MeetingsDestination.route, arguments = listOf(
            navArgument(MeetingsDestination.groupId) {
                type = NavType.StringType
                nullable = true
            },
        )) {
            MeetingsScreen(navController = navController)
        }

        composable(route = CompaniesDestination.route) {
            CompaniesScreen()
        }

        composable(route = ProfileDestination.route) {
            ProfileScreen(
                navigateToCompanies =  { navController.navigate(CompaniesDestination.dest) },
                navigateToLogin = { navController.navigate(LoginDestination.dest) },
                navigateToMeetings = { group -> navController.navigate(MeetingsDestination.destWithArgs(group)) },
                navigateToTasks = { navController.navigate(TasksDestination.dest) },
                navigateToMyPositions = { userId -> navController.navigate(PositionsDestination.destWithArgs(userId)) },
                navigateToChangePracticeApplicationCreation = { navController.navigate(ChangePracticeApplicationCreationScreenDestination.dest) }
            )
        }

        composable(route = ChangePracticeApplicationCreationScreenDestination.route) {
            ChangePracticeApplicationCreationScreen(
                navigateToProfile = { navController.navigate(ProfileDestination.dest) }
            )
        }

        composable(route = TasksDestination.route) {
            TasksScreen(navigateToTaskScreen = { navController.navigate(TaskDestination.destWithArgs(it)) })
        }

        composable(
            route = TaskDestination.route,
            arguments = listOf(navArgument(TaskDestination.taskIdArg) {
                type = NavType.StringType
            })
        ) {
            TaskScreen(navigateToSolutionCreationEditing = { taskId ->
                navController.navigate(
                    SolutionCreatingEditingDestination.destWithArgs(
                        null,
                        taskId,
                        null
                    )
                )
            }, { solutionId, taskId -> navController.navigate(SolutionCreatingEditingDestination.destWithArgs(solutionId, taskId, null)) })
        }


        composable(
            route = PickPhotoDestination.route, arguments = listOf(
                navArgument(PickPhotoDestination.solutionId) {
                    type = NavType.StringType
                    nullable = true
                },
                navArgument(PickPhotoDestination.taskId) {
                    type = NavType.StringType
                },
            )
        ) {
            PickPhotoScreen(navigateToSolutionScreen = { solutionId, taskId, uri ->
                navController.previousBackStackEntry
                    ?.savedStateHandle
                    ?.set("PICK_PHOTO_URI_KEY", uri?.toString())
                navController.navigate(SolutionCreatingEditingDestination.destWithArgs(solutionId, taskId, uri))
            }
            )
        }

        composable(route = SolutionCreatingEditingDestination.route, arguments = listOf(
            navArgument(SolutionCreatingEditingDestination.solutionId) {
                type = NavType.StringType
                nullable = true
            },
            navArgument(SolutionCreatingEditingDestination.taskId) {
                type = NavType.StringType
            },
            navArgument(SolutionCreatingEditingDestination.uri) {
                type = NavType.StringType
                nullable = true
            }
        )) {
            SolutionCreatingEditingScreen(
                navigateToTasksScreen = { navController.navigate(TasksDestination.dest) },
                navigateToPickPhotoScreen = { solutionId, taskId -> navController.navigate(PickPhotoDestination.destWithArgs(solutionId, taskId)) })
        }
    }
}