package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.ClienteDireccion;
import com.example.demo.repository.entity.Direccion;

import lombok.Data;
import lombok.ToString;

@Data
public class DireccionDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String descripcion;
	private String pais;
	private String cp;
	@ToString.Exclude
	private List<ClienteDTO> listaClientesDTO;

	public static DireccionDTO convertToDTO(Direccion direccion, ClienteDTO clienteDTO) {

		DireccionDTO direccionDTO = new DireccionDTO();
		direccionDTO.setId(direccion.getId());
		direccionDTO.setDescripcion(direccion.getDescripcion());
		direccionDTO.setPais(direccion.getPais());
		direccionDTO.setCp(direccion.getCp());

		// No tiene sentido mapear todos los clientes que tiene cada direccion ya que no
		// va a interesar trabajar por direcciones con sus clientes
		direccionDTO.getListaClientesDTO().add(clienteDTO);

		return direccionDTO;

	}

	public static Direccion convertToEntity(DireccionDTO direccionDTO, Cliente cliente) {

		Direccion direccion = new Direccion();
		direccion.setId(direccionDTO.getId());
		direccion.setDescripcion(direccionDTO.getDescripcion());
		direccion.setPais(direccionDTO.getPais());
		direccion.setCp(direccionDTO.getCp());

		/*
		 * Como direccion ahora no tiene una lista de cliente hay que modificar esta parte
		// No tiene sentido mapear todos los clientes que tiene cada direccion ya que no
		// va a interesar trabajar por direcciones con sus clientes
		direccion.getListaClientes().add(cliente);
		*/
		
		//Entidad ClienteDireccion con su cliente y su direccion
		ClienteDireccion cd = new ClienteDireccion();
		cd.setCliente(cliente);
		cd.setDireccion(direccion);
		direccion.getListaClientesDirecciones().add(cd);
		cliente.getListaClientesDirecciones().add(cd);

		return direccion;
	}

	public DireccionDTO() {
		super();
		this.listaClientesDTO = new ArrayList<ClienteDTO>();
	}

}
