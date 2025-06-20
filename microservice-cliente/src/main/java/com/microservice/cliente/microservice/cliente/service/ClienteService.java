package com.microservice.cliente.microservice.cliente.service;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.microservice.cliente.microservice.cliente.model.Cliente;
import com.microservice.cliente.microservice.cliente.repository.ClienteRepository;

import jakarta.transaction.Transactional;

@Service
@Transactional

public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    // Endpoint Obtener TODOS los CLIENTES

    public List<Cliente> obtenerTodosClientes() {
        return clienteRepository.findAll();
    }
    
    // Endpoint CREAR CLIENTE
    public Cliente save(Cliente cliente){
        return clienteRepository.save(cliente);
    }
    
    
    
    // Endpoint Buscar CLIENTE por id
    public Cliente findById(long id){
        return clienteRepository.findById(id).orElse(null);
    }
    
    
    
    
    // Endpoint borrar CLIENTE
    public void eliminarCliente(Long clienteId) {
        if (clienteRepository.existsById(clienteId)) {
            clienteRepository.deleteById(clienteId);
        } else {
            throw new RuntimeException("Cliente no encontrado con id: " + clienteId);
        }
    }
}