package com.nttdata.microservicios.app.respuestas.models.services;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.microservicios.app.respuestas.clients.ExamenFeignClient;
import com.nttdata.microservicios.app.respuestas.models.entity.Respuesta;
import com.nttdata.microservicios.app.respuestas.models.repository.RespuestaRepository;
import com.nttdata.microservicios.commons.examenes.models.entity.Examen;
import com.nttdata.microservicios.commons.examenes.models.entity.Pregunta;



@Service
public class RespuestaServiceImpl  implements RespuestaService {
	
	@Autowired
	private RespuestaRepository respuestaRepository;
	
	@Autowired
	private ExamenFeignClient examenClient;

	@Override
	public Iterable<Respuesta> saveAll(Iterable<Respuesta> respuestas) {
		return respuestaRepository.saveAll(respuestas);
	}

	@Override
	public Iterable<Respuesta> findRespuestaByAlumnoByExamen(Long alumnoId, Long examenId) {
		Examen examen = examenClient.obtenerExamenPorId(examenId);
		List<Pregunta> preguntas = examen.getPreguntas();
		List<Long> preguntaIds = preguntas.stream().map(p -> p.getId()).collect(Collectors.toList());
		List<Respuesta> respuestas = (List<Respuesta>) respuestaRepository.findRespuestaByAlumnoByPreguntaIds(alumnoId, preguntaIds);
		respuestas = respuestas.stream().map(r -> {
			preguntas.forEach(p -> {
				if(p.getId() == r.getPreguntaId()) {
					r.setPregunta(p);
				}
			});
			return r;
		}).collect(Collectors.toList());
		return respuestas;
	}

	@Override
	public Iterable<Long> findExamenesIdsConRespuestasByAlumnoId(Long alumnoId) {
		List<Respuesta> respuestasAlumno = (List<Respuesta>) respuestaRepository.findByAlumnoId(alumnoId);

		List<Long> examenIds = Collections.emptyList();
		if(respuestasAlumno != null && !respuestasAlumno.isEmpty()) {
			List<Long> preguntaIds = respuestasAlumno.stream().map(r -> r.getPreguntaId()).collect(Collectors.toList());

			examenIds = examenClient.obtenerExamenesIdsPorPreguntasIdRespondidas(preguntaIds);
		}
		return examenIds;
	}

	@Override
	public Iterable<Respuesta> findByAlumnoId(Long alumnoId) {
		return respuestaRepository.findByAlumnoId(alumnoId);
	}

}
