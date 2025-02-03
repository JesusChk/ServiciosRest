package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Objects;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.Recomendacion;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
public class RecomendacionDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String observaciones;
	@ToString.Exclude
	@JsonBackReference
	private ClienteDTO clienteDTO;
	
	public static RecomendacionDTO convertToDTO(Recomendacion recomendacion, ClienteDTO clienteDTO) {
		
		RecomendacionDTO recomendacionDTO = new RecomendacionDTO();
		recomendacionDTO.setId(recomendacion.getId());
		recomendacionDTO.setObservaciones(recomendacion.getObservaciones());
		recomendacionDTO.setClienteDTO(clienteDTO);
		
		return recomendacionDTO;
	}

	public static Recomendacion convertToEntity(RecomendacionDTO recomendacionDTO,
			Cliente cliente) {
		
		Recomendacion recomendacion = new Recomendacion();
		recomendacion.setId(recomendacionDTO.getId());
		recomendacion.setObservaciones(recomendacionDTO.getObservaciones());
		recomendacion.setCliente(cliente);

		return recomendacion;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RecomendacionDTO other = (RecomendacionDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	
}
