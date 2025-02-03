package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;

public interface ClienteService {

	public List<ClienteDTO> findAll();
	public ClienteDTO findById(ClienteDTO clienteDTO);
	public ClienteDTO save(ClienteDTO clienteDTO);
	public void delete(ClienteDTO clienteDTO);
	public List<ClienteDTO> findByApellidos(String apellidos);

}
