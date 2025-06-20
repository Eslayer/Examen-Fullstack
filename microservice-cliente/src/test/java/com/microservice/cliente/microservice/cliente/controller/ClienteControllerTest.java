package com.microservice.cliente.microservice.cliente.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.microservice.cliente.microservice.cliente.model.Cliente;
import com.microservice.cliente.microservice.cliente.service.ClienteService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.sql.Date;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ClienteController.class)
public class ClienteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ClienteService clienteService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testListarClientes() throws Exception {
        Cliente c1 = new Cliente(1L, "12345678-9", "Juan", "Pérez", "juan@mail.com", Date.valueOf("1990-01-01"));
        Cliente c2 = new Cliente(2L, "98765432-1", "Ana", "Gómez", "ana@mail.com", Date.valueOf("1985-05-20"));

        Mockito.when(clienteService.obtenerTodosClientes()).thenReturn(Arrays.asList(c1, c2));

        mockMvc.perform(get("/api/v1/clientes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].run").value("12345678-9"))
                .andExpect(jsonPath("$[1].run").value("98765432-1"));
    }

    @Test
    void testCrearCliente() throws Exception {
        Cliente cliente = new Cliente(null, "11111111-1", "Pedro", "López", "pedro@mail.com", Date.valueOf("1995-07-15"));

        Mockito.when(clienteService.save(any(Cliente.class))).thenReturn(cliente);

        mockMvc.perform(post("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(cliente)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.run").value("11111111-1"))
                .andExpect(jsonPath("$.nombre").value("Pedro"));
    }

    @Test
    void testGetClienteById() throws Exception {
        Cliente cliente = new Cliente(1L, "22222222-2", "Laura", "Martínez", "laura@mail.com", Date.valueOf("1992-03-10"));

        Mockito.when(clienteService.findById(1L)).thenReturn(cliente);

        mockMvc.perform(get("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Laura"))
                .andExpect(jsonPath("$.run").value("22222222-2"));
    }

    @Test
    void testEliminarCliente() throws Exception {
        Mockito.doNothing().when(clienteService).eliminarCliente(1L);

        mockMvc.perform(delete("/api/v1/clientes/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Cliente eliminado correctamente"));
    }

    @Test
    void testActualizarCliente() throws Exception {
        Cliente clienteExistente = new Cliente(1L, "33333333-3", "Carlos", "Ramírez", "carlos@mail.com", Date.valueOf("1988-11-05"));
        Cliente clienteActualizado = new Cliente(1L, "33333333-3", "Carlos Alberto", "Ramírez", "carlos@mail.com", Date.valueOf("1988-11-05"));

        Mockito.when(clienteService.findById(1L)).thenReturn(clienteExistente);
        Mockito.when(clienteService.save(any(Cliente.class))).thenReturn(clienteActualizado);

        mockMvc.perform(put("/api/v1/clientes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(clienteActualizado)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre").value("Carlos Alberto"));
    }
}
