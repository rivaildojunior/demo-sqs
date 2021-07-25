package com.example.demosqs.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demosqs.model.Cliente;
import com.example.demosqs.service.ClienteService;
import com.fasterxml.jackson.core.JsonProcessingException;

@RestController
public class ClienteController {
	
	
	private ClienteService service;
	
	public ClienteController(ClienteService service) {
		this.service = service;
	}


	@PostMapping("/clientes")
	public void cadastrar(@RequestBody Cliente cliente) throws JsonProcessingException {
		service.cadastrar(cliente);
	}

}
