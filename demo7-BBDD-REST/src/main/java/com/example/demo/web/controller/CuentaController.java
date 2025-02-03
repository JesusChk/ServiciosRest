package com.example.demo.web.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.example.demo.model.dto.ClienteDTO;
import com.example.demo.model.dto.CuentaDTO;
import com.example.demo.service.ClienteService;
import com.example.demo.service.CuentaService;

@Controller
public class CuentaController {

	private static final Logger log = LoggerFactory.getLogger(CuentaController.class);

	@Autowired
	private CuentaService cuentaService;
	@Autowired
	private ClienteService clienteService;

	// Visualizar las cuentas de un cliente
	@GetMapping("/clientes/{idCliente}/cuentas")
	public ModelAndView findAllByCliente(@PathVariable("idCliente") Long idCliente) {

		log.info("CuentaController - findByIdCliente: Mostramos las cuentas del cliente: " + idCliente);

		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);

		// Invocacion a la capa de servicios para que busque al cliente
		clienteDTO = clienteService.findById(clienteDTO);
		List<CuentaDTO> listaCuentaDTO = cuentaService.findAllByCliente(clienteDTO);

		ModelAndView mav = new ModelAndView("cuentas");
		mav.addObject("clienteDTO", clienteDTO);
		mav.addObject("listaCuentaDTO", listaCuentaDTO);

		return mav;
	}

	//Borrar cuenta
	@GetMapping("/clientes/{idCliente}/cuentas/{idCuenta}/delete")
	public ModelAndView delete(@PathVariable("idCliente") Long idCliente, @PathVariable("idCuenta") Long idCuenta) {

		log.info("CuentaController - delete: Borramos la cuenta "+ idCuenta+ " del cliente " + idCliente);

		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setId(idCuenta);

		log.info(cuentaDTO.toString());
		// Invocacion a la capa de servicios para que borre la cuenta
		cuentaService.delete(cuentaDTO);

		ModelAndView mav = new ModelAndView("redirect:/clientes/{idCliente}/cuentas");

		return mav;
		}
	/*//Borramos cuenta desvinculando la cuenta del cliente y borrando esta cuenta y luego save del cliente
	@GetMapping("/clientes/{idCliente}/cuentas/{idCuenta}/delete")
	public ModelAndView delete(@PathVariable("idCliente") Long idCliente, @PathVariable("idCuenta") Long idCuenta) {
		
		log.info("CuentaController - delete: Borramos la cuenta "+ idCuenta+ " del cliente " + idCliente);
		
		//Creamos el cliente
		ClienteDTO clienteDTO = new ClienteDTO();
		clienteDTO.setId(idCliente);
		
		//Creamos la cuenta
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setId(idCuenta);
		
		//Invocamos al metodo de borrar
		cuentaService.delete(cuentaDTO,clienteDTO);
		
		ModelAndView mav = new ModelAndView("redirect:/clientes/{idCliente}/cuentas");

		return mav;
	}*/
	
	
	// Salvar cuentas
	@PostMapping("/clientes/{idCliente}/cuentas/save")
	public ModelAndView save(@PathVariable("idCliente") Long idCliente,
			@ModelAttribute("cuentaDTO") CuentaDTO cuentaDTO) {

		log.info("CuentaController - save: Guardamos cuenta " + cuentaDTO.toString());

		cuentaDTO.getClienteDTO().setId(idCliente);
		log.info("CuentaControllerSAVE - " + cuentaDTO.getClienteDTO().toString());
		
		// Invocacion a la capa de servicios para que salve la cuenta
		cuentaService.save(cuentaDTO);

		ModelAndView mav = new ModelAndView("redirect:/clientes/{idCliente}/cuentas");

		return mav;
	}

	// Modifica cuentas
	@GetMapping("/clientes/{idCliente}/cuentas/{idCuenta}/update")
	public ModelAndView update(@PathVariable("idCliente") Long idCliente, @PathVariable("idCuenta") Long idCuenta) {
		
		log.info("CuentaController - update: Modificamos cuenta " + idCuenta + " del cliente " + idCliente);
		
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.setId(idCuenta);

		cuentaDTO = cuentaService.findByCuentaCliente(cuentaDTO);
		log.info("CuentaControllerUPDATE - " + cuentaDTO.getClienteDTO().toString());
		
		ModelAndView mav = new ModelAndView("cuentaform");
		mav.addObject("cuentaDTO", cuentaDTO);
		mav.addObject("add", false);
		
		return mav;
	}

	// Crear cuentas
	@GetMapping("/clientes/{idCliente}/cuentas/add")
	public ModelAndView add(@PathVariable("idCliente") Long idCliente) {
		
		log.info("CuentaController - add: Añadimos cuenta nueva al cliente " + idCliente);
		
		CuentaDTO cuentaDTO = new CuentaDTO();
		cuentaDTO.getClienteDTO().setId(idCliente);
		
		ModelAndView mav = new ModelAndView("cuentaform");
		mav.addObject("cuentaDTO", cuentaDTO);
		mav.addObject("add", true);
		
		return mav;
	}
	
	
}
