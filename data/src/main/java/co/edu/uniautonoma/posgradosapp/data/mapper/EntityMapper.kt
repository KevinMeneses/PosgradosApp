package co.edu.uniautonoma.posgradosapp.data.mapper

import co.edu.uniautonoma.posgradosapp.data.models.*
import co.edu.uniautonoma.posgradosapp.domain.entity.*

object EntityMapper {

    fun escuelaApiToEscuela(escuelaApi: EscuelaApi?): Escuela?{
        return escuelaApi?.let {
            Escuela(
                    direccion = it.direccion,
                    descripcion = it.descripcion,
                    coordenada1 = it.coordenada1,
                    coordenada2 = it.coordenada2,
                    correo = it.correo,
                    director = it.director
            )
        }
    }

    fun usuarioApiToUsuario(usuarioApi: UsuarioApi?): Usuario? {
        return usuarioApi?.let {
            Usuario(
                    codigo = it.codigo,
                    nombre = it.nombre,
                    apellido = it.apellido,
                    semestre = it.semestre,
                    id_posgrado = it.id_posgrado,
                    correo = it.correo
            )
        }
    }

    fun docentesApiListToDocentes(docentesApiList: List<DocentesApi>?): List<Docentes>? {
        return docentesApiList?.map {
            Docentes(
                    nombre = it.nombre,
                    apellido = it.apellido,
                    profesion = it.profesion,
                    descripcion = it.descripcion,
                    imagen = it.imagen
            )
        }
    }

    fun modulosApiListToModulos(modulosApiList: List<ModulosApi>?): List<Modulos>? {
        return modulosApiList?.map {
            Modulos(
                    id = it.id,
                    id_docente = it.id_docente,
                    nombre = it.nombre,
                    descripcion = it.descripcion,
                    creditos = it.creditos,
                    duracion = it.duracion
            )
        }
    }

    fun posgradosApiListToPosgrados(posgradosApiList: List<PosgradosApi>?): List<Posgrados>? {
        return posgradosApiList?.map {
            Posgrados(
                    id = it.id,
                    cod_snies = it.cod_snies,
                    nombre = it.nombre,
                    duracion = it.duracion,
                    totalcreditos = it.totalcreditos,
                    descripcion = it.descripcion,
                    valorsemestre = it.valorsemestre
            )
        }
    }

    fun posgradosApiToPosgrados(posgradosApi: PosgradosApi?): Posgrados? {
        return posgradosApi?.let {
            Posgrados(
                    id = it.id,
                    cod_snies = it.cod_snies,
                    nombre = it.nombre,
                    duracion = it.duracion,
                    totalcreditos = it.totalcreditos,
                    descripcion = it.descripcion,
                    valorsemestre = it.valorsemestre
            )
        }
    }

    fun horariosApiListToHorarios(horariosApiList: List<HorariosApi>?): List<Horarios>? {
        return horariosApiList?.map {
            Horarios(
                    dia = it.dia,
                    hora_inicio = it.hora_inicio,
                    hora_fin = it.hora_fin,
                    sede = it.sede,
                    salon = it.salon
            )
        }
    }

    fun calificacionesApiListToCalificaciones(calificacionesApiList: List<CalificacionesApi>?): List<Calificaciones>? {
        return calificacionesApiList?.map {
            Calificaciones(
                    id_usuario = it.id_usuario,
                    calificacion = it.calificacion,
                    promedio = it.promedio
            )
        }
    }
}