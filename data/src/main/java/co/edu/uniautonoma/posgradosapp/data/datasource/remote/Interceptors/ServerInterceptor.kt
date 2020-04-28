package co.edu.uniautonoma.posgradosapp.data.datasource.remote.Interceptors

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class ServerInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request()

        val message =
                when {
                    isGetEscuela(request) -> getEscuela
                    isGetUsuario(request) -> getUsuario
                    isGetPosgrado(request) -> getPosgrado
                    isGetHorario(request) -> getHorario
                    isGetAllPosgrados(request) -> getAllPosgrados
                    isGetAllModulos(request) -> getAllModulos
                    isGetAllDocentes(request) -> getAllDocentes
                    isGetSomeModulos(request) -> getSomeModulos
                    isGetSomeDocentes(request) -> getSomeDocentes
                    isGetSomeCalificaciones(request) -> getSomeCalificaciones
                    else -> ""
                }

        val body = getBody(message)

        return Response.Builder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
                .request(request)
                .message(message)
                .body(body)
                .addHeader("Content-Type", CONTENT_TYPE)
                .build()
    }

    private fun getBody(body: String): ResponseBody =
            body.toByteArray().toResponseBody(CONTENT_TYPE.toMediaTypeOrNull())

    private fun isGetEscuela(request: Request) = request.url.toString().contains("get_escuela_.php", true)
    private fun isGetUsuario(request: Request) = request.url.toString().contains("get_usuario_.php", true)
    private fun isGetPosgrado(request: Request) = request.url.toString().contains("get_posgrado_.php", true)
    private fun isGetHorario(request: Request) = request.url.toString().contains("get_horario_.php", true)
    private fun isGetAllPosgrados(request: Request) = request.url.toString().contains("get_all_posgrados_.php", true)
    private fun isGetAllModulos(request: Request) = request.url.toString().contains("get_all_modulos_.php", true)
    private fun isGetAllDocentes(request: Request) = request.url.toString().contains("get_all_docentes_.php",true)
    private fun isGetSomeModulos(request: Request) = request.url.toString().contains("get_some_modulos_.php",true)
    private fun isGetSomeDocentes(request: Request) = request.url.toString().contains("get_some_docentes_.php",true)
    private fun isGetSomeCalificaciones(request: Request) = request.url.toString().contains("get_some_calificaciones_.php",true)

    companion object {
        const val SUCCESS_CODE = 200
        const val CONTENT_TYPE = "application/json"
    }

}

const val getEscuela =
        """{
            "director": "Juan Pablo Prado Medina", 
            "descripcion": "La Corporación Universitaria Autonoma.... Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.", 
            "correo": "direccion@uniautonoma.edu.co", 
            "direccion": "calle 3 #5-73", 
            "coordenada1": 2.442971, 
            "coordenada2": -76.605247
            }"""
const val getUsuario =
        """{
            "codigo": 10636,
            "nombre": "Kevin Felipe",
            "apellido": "Meneses Palta",
            "id_posgrado": "6",
            "semestre": 2
            }"""
const val getPosgrado =
        """{
            "nombre": "Especialización en Sistemas de Información"
            }"""
const val getHorario =
        """[{
            "dia": "Viernes",
            "hora_inicio": "18:00",
            "hora_fin": "22:00",
            "sede": "Sede Principal",
            "salon": "201"
            },
            {
            "dia": "Sabado",
            "hora_inicio": "08:00",
            "hora_fin": "16:00",
            "sede": "Sede Principal",
            "salon": "201"
            }
            ]"""
const val getAllPosgrados =
        """[
            {
            "id": "1",
            "cod_snies": "65456",
            "nombre": "Especialización en Pedagogía",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "valorsemestre": "$4,140,580"
            },
            {
            "id": "2",
            "cod_snies": "65456",
            "nombre": "Especialización en Derecho Penal",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "valorsemestre": "$4,140,580"
            },
            {
            "id": "3",
            "cod_snies": "65456",
            "nombre": "Especialización en Gestion de Riesgos",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "valorsemestre": "$4,140,580"
            },
            {
            "id": "4",
            "cod_snies": "65456",
            "nombre": "Especialización en Finanzas Corporativas",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "valorsemestre": "$4,140,580"
            },
            {
            "id": "5",
            "cod_snies": "65456",
            "nombre": "Especialización en Proyectos de Desarrollo",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "valorsemestre": "$4,140,580"
            }
            ]"""
const val getAllModulos = """
    [       {
            "id": 1,
            "id_docente": "1",
            "nombre": "Metodología de la Investigación",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Enero 1 a Febrero 1"
            },
            {
            "id": 2,
            "id_docente": "2",
            "nombre": "Proyecto de Investigación en el Aula",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Febrero 2 a Marzo 2"
            },
            {
            "id": 3,
            "id_docente": "3",
            "nombre": "Gestión del Emprendimiento Educativo",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Marzo 3 a Abril 3"
            },
            {
            "id": 4,
            "id_docente": "4",
            "nombre": "Liderazgo, Emprendimiento y Educación",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Abril 4 a Mayo 4"
            },
            {
            "id": 5,
            "id_docente": "5",
            "nombre": "Saberes y Haceres Pedagógicos",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Mayo 5 a Junio 5"
            }
            ]
"""
const val getAllDocentes = """
    [
            {
            "nombre": "Augusto",
            "apellido": "Velasco",
            "profesion": "Economista",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Hernando",
            "apellido": "Forero",
            "profesion": "Abogado",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Vladimir",
            "apellido": "Morales",
            "profesion": "Ingeniero",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Armando",
            "apellido": "Astudillo",
            "profesion": "Abogado",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Pedro",
            "apellido": "Jaramillo",
            "profesion": "Sociologo",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            }
            ]
"""
const val getSomeModulos = """
    [       {
            "id": 1,
            "id_docente": "1",
            "nombre": "Metodología de la Investigación",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Enero 1 a Febrero 1"
            },
            {
            "id": 2,
            "id_docente": "2",
            "nombre": "Proyecto de Investigación en el Aula",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Febrero 2 a Marzo 2"
            },
            {
            "id": 3,
            "id_docente": "3",
            "nombre": "Gestión del Emprendimiento Educativo",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Marzo 3 a Abril 3"
            },
            {
            "id": 4,
            "id_docente": "4",
            "nombre": "Liderazgo, Emprendimiento y Educación",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Abril 4 a Mayo 4"
            },
            {
            "id": 5,
            "id_docente": "5",
            "nombre": "Saberes y Haceres Pedagógicos",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "creditos": 2,
            "duracion": "Mayo 5 a Junio 5"
            }
            ]
"""
const val getSomeDocentes = """
    [
            {
            "nombre": "Augusto",
            "apellido": "Velasco",
            "profesion": "Economista",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Hernando",
            "apellido": "Forero",
            "profesion": "Abogado",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Vladimir",
            "apellido": "Morales",
            "profesion": "Ingeniero",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Armando",
            "apellido": "Astudillo",
            "profesion": "Abogado",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            },
            {
            "nombre": "Pedro",
            "apellido": "Jaramillo",
            "profesion": "Sociologo",
            "descripcion": "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.",
            "imagen": "http://images.gofreedownload.net/default-profile-picture-8242.jpg"
            }
            ]
"""
const val getSomeCalificaciones = """
    [   {
        "calificacion": 5,
        "promedio": 4
        },
        {
        "calificacion": 3,
        "promedio": 3.5
        },
        {
        "calificacion": 5,
        "promedio": 5
        },
        {
        "calificacion": 2,
        "promedio": 3
        },
        {
        "calificacion": 4,
        "promedio": 4.5
        }
        ]
"""