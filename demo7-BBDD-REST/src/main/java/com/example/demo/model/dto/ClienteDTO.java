package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.ClienteDireccion;
import com.example.demo.repository.entity.Cuenta;
import com.example.demo.repository.entity.Recomendacion;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Data;
import lombok.ToString;

@Data
public class ClienteDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Long id;
	private String nif;
	private String nombre;
	private String apellidos;
	private String claveSeguridad;
	private String email;
	@ToString.Exclude
	@JsonManagedReference
	private RecomendacionDTO recomendacionDTO;
	@ToString.Exclude
	@JsonManagedReference
	private List<CuentaDTO> listaCuentasDTO;
	
	//Ahora como direccionesclientes tiene fecha necesitamos esa lista
	//@ToString.Exclude
	//private List<DireccionDTO> listaDireccionesDTO;
	
	@ToString.Exclude
	@JsonIgnore
	private List<ClienteDireccionDTO> listaClientesDireccionesDTO;
	
	@DateTimeFormat(iso=ISO.DATE)
	private Date fechaNacimiento;

	// Convertir entidad a DTO
	public static ClienteDTO convertToDTO(Cliente cliente) {

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(cliente.getId());
		clienteDTO.setNif(cliente.getNif());
		clienteDTO.setNombre(cliente.getNombre());
		clienteDTO.setApellidos(cliente.getApellidos());
		clienteDTO.setClaveSeguridad(cliente.getClaveSeguridad());
		clienteDTO.setEmail(cliente.getEmail());
		clienteDTO.setFechaNacimiento(cliente.getFechaNacimiento());

		RecomendacionDTO rec = RecomendacionDTO.convertToDTO(cliente.getRecomendacion(), clienteDTO);
		clienteDTO.setRecomendacionDTO(rec);

		// cargamos lista de cuentas
		// Como es un HashSet hay que pasarla a ArrayList
		List<Cuenta> listaCuentas = new ArrayList<Cuenta>(cliente.getListaCuentas());// Esta es la transformacion
		
		for (int i = 0; i < cliente.getListaCuentas().size(); i++) {
			CuentaDTO cuentadto = CuentaDTO.convertToDTO(listaCuentas.get(i), clienteDTO);
			clienteDTO.getListaCuentasDTO().add(cuentadto);
		}

		//Ahora cliente tiene una lista de clientesDirecciones que hemos de pasar a arrayList y como solo nos interesa
		//mostrar direcciones clienteDTO no tiene listaCLientesDirecciones y si listaDirecciones.
		// cargamos lista de direcciones
		// Como es un HashSet hay que pasarla a ArrayList
		/*List<Direccion> listaDirecciones = new ArrayList<Direccion>(cliente.getListaDirecciones());// Esta es latransformacion
		for (int i = 0; i < cliente.getListaDirecciones().size(); i++) {
			DireccionDTO direccionDTO = DireccionDTO.convertToDTO(listaDirecciones.get(i), clienteDTO);
			clienteDTO.getListaDireccionesDTO().add(direccionDTO);
		}*/
		/*
		//Aqui solo pasábamos las direcciones a la lista de clientesdirecciones.
		List<ClienteDireccion> listaClientesDirecciones = new ArrayList<ClienteDireccion>(cliente.getListaClientesDirecciones());// Esta es latransformacion
		for (int i = 0; i < cliente.getListaClientesDirecciones().size(); i++) {
			DireccionDTO direccionDTO = DireccionDTO.convertToDTO(listaClientesDirecciones.get(i).getDireccion(), clienteDTO);
			clienteDTO.getListaDireccionesDTO().add(direccionDTO);
		}*/
		
		//Ahora la listaclientesdirecciones lo tiene todo
		List<ClienteDireccion> listaClientesDirecciones = new ArrayList<ClienteDireccion>(cliente.getListaClientesDirecciones());// Esta es latransformacion
		for (int i = 0; i < cliente.getListaClientesDirecciones().size(); i++) {
			ClienteDireccionDTO clienteDireccionDTO = new ClienteDireccionDTO();
			clienteDireccionDTO.setClienteDTO(clienteDTO);
			
			clienteDireccionDTO.setDireccionDTO(DireccionDTO.convertToDTO(listaClientesDirecciones.get(i).getDireccion(), clienteDTO));
			
			clienteDireccionDTO.setFechaalta(listaClientesDirecciones.get(i).getFechaalta());
			
			clienteDTO.getListaClientesDireccionesDTO().add(clienteDireccionDTO);
		}
		
		return clienteDTO;
	}

	// Convertir DTO a entidad
	public static Cliente convertToEntity(ClienteDTO clienteDTO) {

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		cliente.setNif(clienteDTO.getNif());
		cliente.setNombre(clienteDTO.getNombre());
		cliente.setApellidos(clienteDTO.getApellidos());
		cliente.setClaveSeguridad(clienteDTO.getClaveSeguridad());
		cliente.setEmail(clienteDTO.getEmail());
		cliente.setFechaNacimiento(clienteDTO.getFechaNacimiento());

		Recomendacion rec = RecomendacionDTO.convertToEntity(clienteDTO.getRecomendacionDTO(), cliente);
		cliente.setRecomendacion(rec);

		// cargamos lista de cuentas
		for (int i = 0; i < clienteDTO.getListaCuentasDTO().size(); i++) {
			Cuenta cuenta = CuentaDTO.convertToEntity(clienteDTO.getListaCuentasDTO().get(i));
			cliente.getListaCuentas().add(cuenta);
		}
		
		//Como cliente no admite listaDirecciones hay que modificar esta parte
		// cargamos lista de direcciones
		/*for (int i = 0; i < clienteDTO.getListaDireccionesDTO().size(); i++) {
			Direccion direccion = DireccionDTO.convertToEntity(clienteDTO.getListaDireccionesDTO().get(i), cliente);
			cliente.getListaDirecciones().add(direccion);
		}*/
		/*
		//Ahora cliente tiene una lista de direccionesCLientes SINFECHA
		for (int i = 0; i < clienteDTO.getListaDireccionesDTO().size(); i++) {
			Direccion direccion = DireccionDTO.convertToEntity(clienteDTO.getListaDireccionesDTO().get(i), cliente);
			ClienteDireccion cd = new ClienteDireccion();
			cd.setCliente(cliente);
			cd.setDireccion(direccion);
			cliente.getListaClientesDirecciones().add(cd);
		}*/
		
		//Ahora cliente tiene una lista de direccionesCLientes CONFECHA
		for (int i = 0; i < clienteDTO.getListaClientesDireccionesDTO().size(); i++) {
			ClienteDireccion cd = new ClienteDireccion();
			
			cd.setId(clienteDTO.getListaClientesDireccionesDTO().get(i).getId());
			cd.setCliente(cliente);
			
			cd.setDireccion(DireccionDTO.convertToEntity(clienteDTO.getListaClientesDireccionesDTO().get(i).getDireccionDTO(), cliente));

			cd.setFechaalta(clienteDTO.getListaClientesDireccionesDTO().get(i).getFechaalta());
			
			cliente.getListaClientesDirecciones().add(cd);
		}

		return cliente;
	}

	// Constructor vacio con inicializacion de Recomendacion
	public ClienteDTO() {
		super();
		this.recomendacionDTO = new RecomendacionDTO();
		this.listaCuentasDTO = new ArrayList<CuentaDTO>();
		//this.listaDireccionesDTO = new ArrayList<DireccionDTO>();
		this.listaClientesDireccionesDTO = new ArrayList<ClienteDireccionDTO>();
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ClienteDTO other = (ClienteDTO) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

}
