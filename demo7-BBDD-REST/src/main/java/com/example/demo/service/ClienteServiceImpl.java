package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.entity.Cliente;

@Service
public class ClienteServiceImpl implements ClienteService {

	private static final Logger log = LoggerFactory.getLogger(ClienteServiceImpl.class);

	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public List<ClienteDTO> findAll() {

		log.info("ClienteServiceImpl - findAll: lista de todos los clientes");

		/*
		 * List<ClienteDTO> listaClientesDTO = new ArrayList<ClienteDTO>();
		 * List<Cliente> listaClientes = clienteRepository.findAll(); for (int i = 0; i
		 * < listaClientes.size(); i++) { Cliente cliente = listaClientes.get(i);
		 * ClienteDTO clienteDto = ClienteDTO.convertToDTO(cliente);
		 * listaClientesDTO.add(clienteDto); }
		 */

		List<ClienteDTO> listaClientesDTO = clienteRepository.findAll().stream().map(p -> ClienteDTO.convertToDTO(p))
				.collect(Collectors.toList());

		return listaClientesDTO;
	}

	@Override
	public ClienteDTO findById(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - findById: buscar cliente po id" + clienteDTO.getId());

		Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());
		if (cliente.isPresent()) {
			clienteDTO = ClienteDTO.convertToDTO(cliente.get());
		} else {
			clienteDTO = null;
		}

		return clienteDTO;

	}

	@Override
	public ClienteDTO save(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - save: Salvamos el cliente: " + clienteDTO.toString());

		Cliente cliente = ClienteDTO.convertToEntity(clienteDTO);
		cliente = clienteRepository.save(cliente);
		clienteDTO = ClienteDTO.convertToDTO(cliente);

		return clienteDTO;

	}

	@Override
	public void delete(ClienteDTO clienteDTO) {

		log.info("ClienteServiceImpl - save: Borramos el cliente: " + clienteDTO.getId());

		Cliente cliente = new Cliente();
		cliente.setId(clienteDTO.getId());
		log.info(cliente.toString());
		clienteRepository.deleteById(clienteDTO.getId());
	}

	@Override
	public List<ClienteDTO> findByApellidos(String apellidos) {

		log.info("ClienteServiceImpl - findByApellidos: lista de todos los clientes con apellido" + apellidos);

		List<ClienteDTO> listaClientesDTO = clienteRepository.findByApellidos(apellidos).stream()
				.map(p -> ClienteDTO.convertToDTO(p)).collect(Collectors.toList());

		return listaClientesDTO;
	}

	/*
	 * private String checkDni(String dni) { String mns = null;
	 * 
	 * List<Cliente> lista = clienteRepository.findAll();
	 * 
	 * boolean sw = false; int i = 0; while (i < lista.size() && !sw) { String
	 * dniList = lista.get(i).getNif();
	 * 
	 * if (dniList.equals(dni)) { sw = true; mns = "El dni ya existe"; }
	 * 
	 * i++; }
	 * 
	 * if(!(dni.matches("^[1-9]{8}[A-Za-z]"))) { mns =
	 * "El dni introducido no es correcto, pruebe de nuevo."; }
	 * 
	 * return mns; }
	 */
}
