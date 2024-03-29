package com.nttdata.microservicios.app.respuestas.models.services;

import com.nttdata.microservicios.app.respuestas.models.entity.Respuesta;
import com.nttdata.microservicios.commons.services.CommonService;

public interface RespuestaService {
	
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas);
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId);
	public Iterable<Long> findExamenesIdsConRespuestasByAlumnoId(Long alumnoId);
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId);
}
