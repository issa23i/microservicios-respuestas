package com.nttdata.microservicios.app.respuestas.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservicios.app.respuestas.models.entity.Respuesta;
import com.nttdata.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.nttdata.microservicios.commons.services.CommonServiceImpl;

import org.springframework.transaction.annotation.Transactional;


@Service
public class RespuestaServiceImpl extends CommonServiceImpl<Respuesta,RespuestaRepository> implements RespuestaService {
	
	@Autowired
	private RespuestaRepository respuestaRepository;

	@Override
	@Transactional
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepository.saveAll(respuestas);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		return respuestaRepository.findRespuestaByAlumnoByExamen(alumnoId, examenId);
	}

	@Override
	@Transactional(readOnly = true)
	public Iterable<Long> findExamenesIdsConRespuestasByAlumnoId(Long alumnoId) {
		return respuestaRepository.findExamenesIdsConRespuestasByAlumnoId(alumnoId);
	}

}
