package com.example.demo.web.webservices;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.service.ClienteService;

@RestController
@RequestMapping("ws")
public class ClienteRestController {

	private static final Logger log = LoggerFactory.getLogger(ClienteRestController.class);

	@Autowired
	private ClienteService clienteService;

	// Listar los clientes
	@GetMapping("/clientes")
	public ResponseEntity<List<ClienteDTO>> findAll(
			@RequestParam(value = "apellidos", defaultValue = "") String apellidos) {

		List<ClienteDTO> listaClientesDTO = null;
		if (apellidos.isBlank()) {
			log.info("ClienteRestController - findAll: Mostramos todos los clientes");
			listaClientesDTO = clienteService.findAll();
		} else {
			log.info("ClienteRestController - findAll: Mostramos todos los clientes con apellido" + apellidos);
			listaClientesDTO = clienteService.findByApellidos(apellidos);
		}

		return new ResponseEntity<List<ClienteDTO>>(listaClientesDTO, HttpStatus.OK);
	}

	// Visualizar la informacion de un cliente
	@GetMapping("/clientes/{idCliente}")
	public ResponseEntity<ClienteDTO> findById(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteRestController - findById: Mostramos la informacion del cliente: " + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		// Invocacion a la capa de servicios para que busque al cliente
		clienteDTO = clienteService.findById(clienteDTO);

		ResponseEntity<ClienteDTO> resp;

		if (clienteDTO == null) {
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			resp = new ResponseEntity<>(clienteDTO, HttpStatus.OK);
		}

		return resp;
	}

	// Alta de cliente
	@PostMapping("/clientes")
	public ResponseEntity<ClienteDTO> add(@RequestBody ClienteDTO clienteDTO) {

		log.info("ClienteRestController - add: Anyadimos un nuevo cliente");

		clienteDTO = clienteService.save(clienteDTO);

		ResponseEntity<ClienteDTO> resp;
		if (clienteDTO == null) {
			resp = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} else {
			resp = new ResponseEntity<>(clienteDTO, HttpStatus.OK);
		}

		return resp;
	}

	// Actualizar un cliente
	@PatchMapping("/clientes")
	public ResponseEntity<ClienteDTO> update(@RequestBody ClienteDTO clienteDTO) {

		log.info("ClienteRestController - update: Modificamos el cliente: " + clienteDTO.getId());

		// Vamos a buscar el cliente para saber seguro que existe
		ClienteDTO clienteExDTO = new ClienteDTO();
		clienteExDTO.setId(clienteDTO.getId());
		clienteExDTO = clienteService.findById(clienteExDTO);

		ResponseEntity<ClienteDTO> resp;
		if (clienteExDTO == null) {
			resp = new ResponseEntity<>(HttpStatus.NOT_FOUND);
		} else {
			clienteDTO = clienteService.save(clienteDTO);
			resp = new ResponseEntity<>(clienteDTO, HttpStatus.OK);
		}

		return resp;
	}

	// Borrar un cliente
	@DeleteMapping("/clientes/{idCliente}")
	public ResponseEntity<String> delete(@PathVariable("idCliente") Long idCliente) {

		log.info("ClienteRestController - delete: Borramos el cliente: " + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		// Invocacion a la capa de servicios para que borre al cliente
		clienteService.delete(clienteDTO);

		return new ResponseEntity<>("Cliente borrado satisfactoriamente", HttpStatus.OK);
	}
}
