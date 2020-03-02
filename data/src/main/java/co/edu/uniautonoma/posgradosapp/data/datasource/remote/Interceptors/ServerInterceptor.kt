package co.edu.uniautonoma.posgradosapp.data.datasource.remote.Interceptors

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody.Companion.toResponseBody

class ServerInterceptor: Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val message =
                if (isGetEscuela(request)) getEscuela
                else if (isGetUsuario(request)) getUsuario
                else if (isGetPosgrado(request)) getPosgrado
                else if (isGetHorario(request)) getHorario
                else if (isGetAllPosgrados(request)) getAllPosgrados
                else if (isGetAllModulos(request)) getAllModulos
                else if (isGetAllDocentes(request)) getAllDocentes
                else if (isGetSomeModulos(request)) getSomeModulos
                else if (isGetSomeDocentes(request)) getSomeDocentes
                else getSomeCalificaciones

        val body = getBody(message)

        return chain.proceed(request)
                .newBuilder()
                .code(SUCCESS_CODE)
                .protocol(Protocol.HTTP_2)
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

const val getEscuela = """
{
    "director": "Juan Pablo Prado Medina",
    "descripcion": "La Corporación Universitaria Autonoma....",
    "correo": "direccion@uniautonoma.edu.co",
    "direccion": "calle 3 #5-73",
    "coordenada1": 2.442971,
    "coordenada2": -76.605247
}
"""
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
        """{
            "dia": "Viernes",
            "hora_inicio": "18:00",
            "hora_fin": "22:00",
            "sede": "Sede Principal",
            "salon": "201"
        }"""
const val getAllPosgrados =
        """[
        {
            "id": "1",
            "cod_snies": "65456",
            "nombre": "Especialización en Pedagogía",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "........."
            "valorsemestre": "$4,140,580"
        },
        {
            "id": "2",
            "cod_snies": "65456",
            "nombre": "Especialización en Derecho Penal",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "........."
            "valorsemestre": "$4,140,580"
        },
        {
            "id": "3",
            "cod_snies": "65456",
            "nombre": "Especialización en Gestion de Riesgos",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "........."
            "valorsemestre": "$4,140,580"
        },
        {
            "id": "4",
            "cod_snies": "65456",
            "nombre": "Especialización en Ambiente",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "........."
            "valorsemestre": "$4,140,580"
        },
        {
            "id": "5",
            "cod_snies": "65456",
            "nombre": "Especialización en Sistemas Informáticos",
            "duracion": "2 semestres",
            "totalcreditos": 22,
            "descripcion": "........."
            "valorsemestre": "$4,140,580"
        },
        ]"""
const val getAllModulos = """"""
const val getAllDocentes = """"""
const val getSomeModulos = """"""
const val getSomeDocentes = """"""
const val getSomeCalificaciones = """"""