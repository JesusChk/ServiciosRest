package com.example.demo.service;

import java.util.List;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.CuentaDTO;

public interface CuentaService {

	public void save(CuentaDTO cuentaDTO);

	public CuentaDTO findByCuentaCliente(CuentaDTO cuentaDTO);

	public void delete(CuentaDTO cuentaDTO);//public void delete(CuentaDTO cuentaDTO, ClienteDTO clienteDTO);

	public List<CuentaDTO> findAllByCliente(ClienteDTO clienteDTO);

}
