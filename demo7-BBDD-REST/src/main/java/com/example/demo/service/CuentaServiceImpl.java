package com.example.demo.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.CuentaDTO;
import com.example.demo.repository.dao.ClienteRepository;
import com.example.demo.repository.dao.CuentaRepository;
import com.example.demo.repository.entity.Cuenta;

@Service
public class CuentaServiceImpl implements CuentaService{
	
	private static final Logger log = LoggerFactory.getLogger(CuentaServiceImpl.class);
	
	@Autowired
	public CuentaRepository cuentaRepository;
	@Autowired
	public ClienteRepository clienteRepository;

	@Override
	public void save(CuentaDTO cuentaDTO) {
		
		log.info("CuentaServiceImpl - Guardamos: cuenta de cliente " + cuentaDTO.getClienteDTO().getId()
				+" con cuenta " + cuentaDTO.getId());
		
		Cuenta cuenta = CuentaDTO.convertToEntity(cuentaDTO);
		//como hemos quitado el cliente del convertoentity le ponemos ahora el id
		cuenta.getCliente().setId(cuentaDTO.getClienteDTO().getId());
		
		cuentaRepository.saveCustom(cuenta);
	}

	@Override
	public CuentaDTO findByCuentaCliente(CuentaDTO cuentaDTO) {
		
		log.info("CuentaServiceImpl - buscaCuenta: buscar cuenta de un cliente");
		
		Optional<Cuenta> cuenta = cuentaRepository.findById(cuentaDTO.getId());
		ClienteDTO clienteDTO= new ClienteDTO();
		clienteDTO = ClienteDTO.convertToDTO(cuenta.get().getCliente());
		cuentaDTO = CuentaDTO.convertToDTO(cuenta.get(), clienteDTO);
		
		return cuentaDTO;
		
	}

	//Borramos una cuenta directamente
	@Override
	public void delete(CuentaDTO cuentaDTO) {
		
		log.info("CuentaServiceImpl - delete: Borramos la cuenta "+ cuentaDTO.getId());
			
		log.info(cuentaDTO.toString());
		cuentaRepository.deleteById(cuentaDTO.getId());
	}
	
	/*//Borramos una cuenta a traves de la lista de un cliente
	@Override
	public void delete(CuentaDTO cuentaDTO, ClienteDTO clienteDTO) {
		
		log.info("CuentaServiceImpl - delete: Borramos la cuenta "+ cuentaDTO.getId()+ " del cliente " + cuentaDTO.getClienteDTO().getId());
		
		//Localizamos los objetos que vienen en Optional
		Optional<Cliente> cliente = clienteRepository.findById(clienteDTO.getId());
		Optional<Cuenta> cuenta = cuentaRepository.findById(cuentaDTO.getId());
		
		//Eliminamos la cuenta de la lista de cuentas del cliente
		cliente.get().getListaCuentas().remove(cuenta.get());
		
		//Eliminamos la cuenta
		cuentaRepository.deleteById(cuenta.get().getId());
		
		//Salvamos el cliente con la nueva situacion
		clienteRepository.save(cliente.get());
		
		cuentaRepository.deleteById(cuentaDTO.getId());
	}*/

	//Buscamos todas las cuentas de un cliente
	@Override
	public List<CuentaDTO> findAllByCliente(ClienteDTO clienteDTO) {
		
		List<Cuenta> listaCuenta = cuentaRepository.findAllByCliente(clienteDTO.getId());
		
		List<CuentaDTO> listaCuentaDTO = listaCuenta.
				stream().
				map(c->CuentaDTO.convertToDTO(c,clienteDTO)).
				collect(Collectors.toList());
		
		return listaCuentaDTO;
	}

}
