package ru.hits.studentintership.common.hilt

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.hits.studentintership.data.positions.repository.PositionsRepository

// @Module
// @InstallIn(SingletonComponent::class)
// interface RepositoryModule {
//     @Binds
//     fun bindPositionsRepository(positionsRepository: PositionsRepository):
// }