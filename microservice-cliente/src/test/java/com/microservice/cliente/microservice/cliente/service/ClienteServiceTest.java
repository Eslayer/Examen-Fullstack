package com.microservice.cliente.microservice.cliente.service;

import com.microservice.cliente.microservice.cliente.model.Cliente;
import com.microservice.cliente.microservice.cliente.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setApellido("Pérez");
    }

    @Test
    void testObtenerTodosClientes() {
        List<Cliente> clientes = Arrays.asList(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> resultado = clienteService.obtenerTodosClientes();

        assertEquals(1, resultado.size());
        verify(clienteRepository).findAll();
    }

    @Test
    void testGuardarCliente() {
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        Cliente resultado = clienteService.save(cliente);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository).save(cliente);
    }

    @Test
    void testBuscarClientePorId() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente resultado = clienteService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombre());
        verify(clienteRepository).findById(1L);
    }



    @Test
    void testEliminarClienteExistente() {
        when(clienteRepository.existsById(1L)).thenReturn(true);
        doNothing().when(clienteRepository).deleteById(1L);

        assertDoesNotThrow(() -> clienteService.eliminarCliente(1L));
        verify(clienteRepository).deleteById(1L);
    }


}
