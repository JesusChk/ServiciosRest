package com.example.demo.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.ClienteDireccionDTO;
import com.example.demo.model.dto.DireccionDTO;
import com.example.demo.repository.dao.ClienteDireccionRepository;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.DireccionRepository;
import com.example.demo.repository.entity.Cliente;
import com.example.demo.repository.entity.ClienteDireccion;
import com.example.demo.repository.entity.Direccion;

@Service
public class DireccionServiceImpl implements DireccionService{
	
	private static final Logger log = LoggerFactory.getLogger(DireccionServiceImpl.class);
	
	@Autowired
	private DireccionRepository direccionRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ClienteDireccionRepository clienteDireccionRepository;

	/*
	//Esta era con List<DireccionDTO>
	@Override
	public List<DireccionDTO> findAllByCliente(ClienteDTO clienteDTO) {
		
		log.info("DireccionServiceImpl - findAllByCliente: Lista de todas las direcciones del cliente: " + clienteDTO.getId());
		
		//Obtenemos la lista de direcciones del cliente
		List<Direccion> listaDirecciones = (List<Direccion>) direccionRepository.findAllByCliente(clienteDTO.getId());
		//Creamos listaDireccionesDTO para devolver(Para mapeo necesitamos clienteDTO que viene como parametro)
		List<DireccionDTO> listaDireccionesDTO = new ArrayList<DireccionDTO>();
		for(int i = 0; i<listaDirecciones.size();i++) {
			listaDireccionesDTO.add(DireccionDTO.convertToDTO(listaDirecciones.get(i), clienteDTO));
		}
		
		return listaDireccionesDTO;
	}
	*/
	
	//Esta es con LIST<clienteDireccion>
	@Override
	public List<ClienteDireccionDTO> findAllByCliente(ClienteDTO clienteDTO) {
		
		log.info("DireccionServiceImpl - findAllByCliente: Lista de todas las direcciones del cliente: " + clienteDTO.getId());
		
		//Obtenemos la lista de direcciones del cliente
		List<ClienteDireccion> listaClientesDirecciones = clienteDireccionRepository.findAllByCliente(clienteDTO.getId());
		//Creamos listaDireccionesDTO para devolver(Para mapeo necesitamos clienteDTO que viene como parametro)
		List<ClienteDireccionDTO> listaClientesDireccionesDTO = new ArrayList<ClienteDireccionDTO>();
		for(int i = 0; i<listaClientesDirecciones.size();i++) {
			listaClientesDireccionesDTO.add(ClienteDireccionDTO.convertToDTO(listaClientesDirecciones.get(i)));
		}
		
		return listaClientesDireccionesDTO;
	}

	/*
	//Trabajamos sin listaClientesDirecciones
	@Override
	public void save(DireccionDTO direccionDTO) {

		log.info("DireccionesServiceImpl - save: Salva la direccion del cliente: " + direccionDTO.getListaClientesDTO().get(0).getId());
		
		//Cliente ya no tiene ListaDirecciones
		//Como solo tenemos un cliente en la direccion
	//	Optional<Cliente> cliente = clienteRepository.findById(direccionDTO.getListaClientesDTO().get(0).getId());
	//	if(cliente.isPresent()) {
			//Creamos las relaciones en las entidades
			//La direccion ya tiene el cliente y ya lo hemos pasado al entitad arriba
			//Al clientele metemos la direccion
	//		Direccion direccion = DireccionDTO.convertToEntity(direccionDTO, cliente.get());
	//		cliente.get().getListaDirecciones().add(direccion);
			//Almacenamos la direccion, y por la relacion que tiene, almacenará la relacion N a N
	//		direccionRepository.save(direccion);
	//	}
		
		
		//Trabajamos con listaDireccionesClientes
		//Solo existe un clienteDTO en la DireccionDTO. Creamos CLienteDireccion y le metemos el cliente y la direccion
		Optional<Cliente> cliente = clienteRepository.findById(direccionDTO.getListaClientesDTO().get(0).getId());
		if(cliente.isPresent()) {
			//Creamos las relaciones en las entidades
			Direccion direccion = DireccionDTO.convertToEntity(direccionDTO, cliente.get());
			ClienteDireccion cd = new ClienteDireccion();
			cd.setDireccion(direccion);
			cd.setCliente(cliente.get());
			//Almacenamos la direccion, y por la relacion que tiene, almacenará la relacion N a N
			direccionRepository.save(direccion);
		}
	}*/

	//Trabajamos CON listaClientesDirecciones
		@Override
		public void save(DireccionDTO direccionDTO) {

			log.info("DireccionesServiceImpl - save: Salva la direccion del cliente: " + direccionDTO.getListaClientesDTO().get(0).getId());
			
			//Obtenemos la entidad cliente
			Optional<Cliente> cliente = clienteRepository.findById(direccionDTO.getListaClientesDTO().get(0).getId());
			
			//Persistimos la entidad direccion
			Direccion direccion = DireccionDTO.convertToEntity(direccionDTO, cliente.get());
			direccionRepository.save(direccion);
			
			//Hay que almacenar la relacion intermedia, que es ClienteDireccion, ya sea nueva o una actualizacion
			ClienteDireccion cd = null;
			Optional<ClienteDireccion> cdo = clienteDireccionRepository.findAllByClienteDireccion(cliente.get().getId(),direccion.getId());
			if(cdo.isPresent()) {
				cd=cdo.get();
				cd.setFechaalta(new Date());
			}else {
				cd = new ClienteDireccion();
				cd.setCliente(cliente.get());
				cd.setDireccion(direccion);
				cd.setFechaalta(new Date());
			}
			clienteDireccionRepository.save(cd);
		}
	
}
