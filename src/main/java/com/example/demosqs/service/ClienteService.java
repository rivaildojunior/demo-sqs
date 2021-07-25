package com.example.demosqs.service;

import javax.jms.JMSException;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

import com.example.demosqs.model.Cliente;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ClienteService {

	private JmsTemplate jmsTemplate;

	public ClienteService(JmsTemplate jmsTemplate) {
		this.jmsTemplate = jmsTemplate;
	}

	public void cadastrar(Cliente cliente) throws JsonProcessingException {
		ObjectMapper objectMapper = new ObjectMapper();
		String strCliente = objectMapper.writeValueAsString(cliente);
		jmsTemplate.convertAndSend("sqs-demo", strCliente);
	}

	@JmsListener(destination = "sqs-demo")
	public void receiver(String message) throws JMSException {
		System.out.println("Mensagem recebida = " + message);
	}
}
