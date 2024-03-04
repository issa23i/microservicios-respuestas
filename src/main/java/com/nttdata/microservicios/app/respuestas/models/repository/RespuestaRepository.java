package com.nttdata.microservicios.app.respuestas.models.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.nttdata.microservicios.app.respuestas.models.entity.Respuesta;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
	@Query("select r from Respuesta r join fetch r.pregunta p join fetch p.examen e where r.alumno_id=?1 and e.id=?2")
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
		
	@Query("select e.id from Respuesta r join r.pregunta p join p.examen e where r.alumno_id=?1 group by e.id ")
	public Iterable<Long> findExamenesIdsConRespuestasByAlumnoId(Long alumnoId);
}
