package com.microservice.cliente.microservice.cliente.controller;

import java.util.List;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservice.cliente.microservice.cliente.model.Cliente;
import com.microservice.cliente.microservice.cliente.service.ClienteService;

@RestController
@RequestMapping("/api/v1/clientes")
public class ClienteController {

    private final ClienteService clienteService;

    
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    
    
    @GetMapping
    public List<Cliente> listarClientes() {
        return clienteService.obtenerTodosClientes();
    }
    
    // Endpoint CREAR CLIENTE
    @PostMapping
    public ResponseEntity<?> crearCliente(@RequestBody Cliente cliente) {
        clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(cliente);
    }



    // Endpoint Buscar CLIENTE
    @GetMapping("/{id}")
    public ResponseEntity<?> getClienteById(@PathVariable Long id) {
        return ResponseEntity.ok(clienteService.findById(id));
    }

        //editar clientes

    @PutMapping
    public ResponseEntity<Cliente> putCliente(@RequestBody Cliente cliente){
        Cliente s = clienteService.findById(cliente.getId());
        if (s != null){
            s.setNombre(cliente.getNombre());
            s.setApellido(cliente.getApellido());
            s.setEmail(cliente.getEmail());

            Cliente actualizado = clienteService.save(s);
            return ResponseEntity.ok(actualizado);
        } else {
            return ResponseEntity.notFound().build();
        }
    }



    @DeleteMapping("/{id}")
    public ResponseEntity<String> eliminarCliente(@PathVariable Long id) {
        try {
            clienteService.eliminarCliente(id);
            return ResponseEntity.ok("Cliente eliminado correctamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }


}

    
