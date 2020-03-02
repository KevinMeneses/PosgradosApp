package co.edu.uniautonoma.posgradosapp.di

import co.edu.uniautonoma.posgradosapp.data.datasource.remote.Interceptors.ServerInterceptor
import co.edu.uniautonoma.posgradosapp.data.datasource.remote.retrofit.PeticionesApi
import co.edu.uniautonoma.posgradosapp.data.repository.*
import co.edu.uniautonoma.posgradosapp.domain.repository.*
import co.edu.uniautonoma.posgradosapp.domain.usecase.*
import co.edu.uniautonoma.posgradosapp.presentation.ui.docentes.DocentesViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.escuela.EscuelaViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.iniciosesion.InicioSesionViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.modulos.ModulosViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.posgrados.PosgradosViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.pqrs.PqrsViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.principal.PrincipalViewModel
import co.edu.uniautonoma.posgradosapp.presentation.ui.ubicacion.UbicacionViewModel
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

val okHttpClientModule = module {

    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
                .connectTimeout(20, TimeUnit.SECONDS)
                .readTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(ServerInterceptor())
                .build()
    }
    single { provideOkHttpClient() }
}

val retrofitModule = module {

    fun provideRetrofit(okHttpClient: OkHttpClient): PeticionesApi {
        return Retrofit.Builder()
                .baseUrl("https://posgradosapp.uniautonoma.edu.co/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build()
                .create(PeticionesApi::class.java)
    }
    single { provideRetrofit(get()) }
}

val viewModelModule = module {

    viewModel { EscuelaViewModel(get()) }
    viewModel { InicioSesionViewModel(get()) }
    viewModel { PqrsViewModel(get()) }
    viewModel { DocentesViewModel(get()) }
    viewModel { ModulosViewModel(get()) }
    viewModel { PosgradosViewModel(get()) }
    viewModel { UbicacionViewModel(get()) }
    viewModel { PrincipalViewModel(get()) }
}

val useCaseModule = module {

    fun provideEscuelaUseCase(escuelaRepository: EscuelaRepository): EscuelaUseCase
        = EscuelaUseCaseImpl(escuelaRepository)
    fun provideInicioSesionUseCase(usuarioRepository: UsuarioRepository): InicioSesionUseCase
        = InicioSesionUseCaseImpl(usuarioRepository)
    fun provideDocentesUseCase(docentesRepository: DocentesRepository): DocentesUseCase
        = DocentesUseCaseImpl(docentesRepository)
    fun provideModulosUseCase(modulosRepository: ModulosRepository): ModulosUseCase
        = ModulosUseCaseImpl(modulosRepository)
    fun providePosgradosUseCase(posgradosRepository: PosgradosRepository): PosgradosUseCase
        = PosgradosUseCaseImpl(posgradosRepository)
    fun providePrincipalUseCase(principalRepository: PrincipalRepository): PrincipalUseCase
        = PrincipalUseCaseImpl(principalRepository)

    single { provideEscuelaUseCase(get()) }
    single { provideInicioSesionUseCase(get()) }
    single { provideDocentesUseCase(get()) }
    single { provideModulosUseCase(get()) }
    single { providePosgradosUseCase(get()) }
    single { providePrincipalUseCase(get()) }
}

val repositoryModule = module {

    fun provideEscuelaRepository(retrofit: PeticionesApi): EscuelaRepository
        = EscuelaRepositoryImpl(retrofit)
    fun provideUsuarioRepository(retrofit: PeticionesApi): UsuarioRepository
        = UsuarioRepositoryImpl(retrofit)
    fun provideDocentesRepository(retrofit: PeticionesApi): DocentesRepository
        = DocentesRepositoryImpl(retrofit)
    fun provideModulosRepository(retrofit: PeticionesApi): ModulosRepository
        = ModulosRepositoryImpl(retrofit)
    fun providePosgradosRepository(retrofit: PeticionesApi): PosgradosRepository
        = PosgradosRepositoryImpl(retrofit)
    fun providePrincipalRepository(retrofit: PeticionesApi): PrincipalRepository
        = PrincipalRepositoryImpl(retrofit)

    single { provideEscuelaRepository(get()) }
    single { provideUsuarioRepository(get()) }
    single { provideDocentesRepository(get()) }
    single { provideModulosRepository(get()) }
    single { providePosgradosRepository(get()) }
    single { providePrincipalRepository(get()) }
}

