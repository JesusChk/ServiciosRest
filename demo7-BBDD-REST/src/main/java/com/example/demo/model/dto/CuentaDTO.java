package com.example.demo.model.dto;

import java.io.Serializable;
import java.util.Objects;

import com.example.demo.repository.entity.Cuenta;
import com.fasterxml.jackson.annotation.JsonBackReference;

import lombok.Data;
import lombok.ToString;

@Data
public class CuentaDTO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Long id;
	private String banco;
	private String sucursal;
	private String dc;
	private String numeroCuenta;
	private float saldoActual;
	@ToString.Exclude
	@JsonBackReference
	private ClienteDTO clienteDTO;

	
	//convertir entidad a DTO
	public static CuentaDTO convertToDTO (Cuenta cuenta, ClienteDTO clienteDTO) {
		
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setId(cuenta.getId());
		cuentaDTO.setBanco(cuenta.getBanco());
		cuentaDTO.setSucursal(cuenta.getSucursal());
		cuentaDTO.setDc(cuenta.getDc());
		cuentaDTO.setNumeroCuenta(cuenta.getNumeroCuenta());
		cuentaDTO.setSaldoActual(cuenta.getSaldoActual());
		cuentaDTO.setClienteDTO(clienteDTO);
		
		return cuentaDTO;
	}
	
	//Convertir DTO a entidad
	public static Cuenta convertToEntity(CuentaDTO cuentaDTO) {
		
		Cuenta cuenta = new Cuenta();
		cuenta.setId(cuentaDTO.getId());
		cuenta.setBanco(cuentaDTO.getBanco());
		cuenta.setSucursal(cuentaDTO.getSucursal());
		cuenta.setDc(cuentaDTO.getDc());
		cuenta.setNumeroCuenta(cuentaDTO.getNumeroCuenta());
		cuenta.setSaldoActual(cuentaDTO.getSaldoActual());
		
		/*
		 * Como al hacer Custom buscamos a este cliente y se lo metemos, aqui no hacemos nada
		Cliente cliente = ClienteDTO.convertToEntity(cuentaDTO.getClienteDTO());
		cuenta.setCliente(cliente);
		*/
		
		return cuenta;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		CuentaDTO other = (CuentaDTO) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public int hashCode() {
		return Objects.hash(id);
	}
	
	public CuentaDTO() {
		super();
		clienteDTO = new ClienteDTO();
	}
	
}
