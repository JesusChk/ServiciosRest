package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import com.example.demo.repository.entity.ClienteDireccion;

import lombok.Data;

@Data
public class ClienteDireccionDTO implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Long id;
	
	private ClienteDTO clienteDTO;
	
	private DireccionDTO direccionDTO;
	
	@DateTimeFormat(pattern="dd-mm-yyyy HH:mm:ss")
	private Date fechaalta;
	
	//Solo pasaremos de entidad a DTO. De DTO a entidad nunca lo necesitaremos
	public static ClienteDireccionDTO convertToDTO(ClienteDireccion clienteDireccion) {
		
		ClienteDireccionDTO cdDTO = new ClienteDireccionDTO();
		ClienteDTO clienteDTO = ClienteDTO.convertToDTO(clienteDireccion.getCliente());
		cdDTO.setClienteDTO(clienteDTO);
		
		cdDTO.setDireccionDTO(DireccionDTO.convertToDTO(clienteDireccion.getDireccion(), clienteDTO));
		
		cdDTO.setFechaalta(clienteDireccion.getFechaalta());
		
		return cdDTO;
	}
}
