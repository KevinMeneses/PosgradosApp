package co.edu.uniautonoma.posgradosapp.di

import co.edu.uniautonoma.posgradosapp.data.repository.EscuelaRepositoryImpl
import co.edu.uniautonoma.posgradosapp.domain.repository.EscuelaRepository
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCase
import co.edu.uniautonoma.posgradosapp.domain.usecase.EscuelaUseCaseImpl
import co.edu.uniautonoma.posgradosapp.presentation.ui.escuela.EscuelaViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { EscuelaViewModel(get()) }
}

val useCaseModule = module {
    fun provideEscuelaUseCase(escuelaRepository: EscuelaRepository): EscuelaUseCase{
        return EscuelaUseCaseImpl(escuelaRepository)
    }
    single { provideEscuelaUseCase(get()) }
}

val repositoryModule = module {
    fun provideEscuelaRepository(): EscuelaRepository {
        return EscuelaRepositoryImpl()
    }
    single { provideEscuelaRepository() }
}