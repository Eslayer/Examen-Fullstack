package com.microservice_ventas.controller;

import com.microservice_ventas.dto.VentaConDTO;
import com.microservice_ventas.model.Venta;
import com.microservice_ventas.service.ServiceVenta;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class VentaControllerTest {

    @Mock
    private ServiceVenta ventaService;

    @InjectMocks
    private VentaController ventaController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearVenta() {
        VentaConDTO ventaDTO = mock(VentaConDTO.class);
        Venta ventaCreada = new Venta();
        when(ventaService.crearVenta(ventaDTO)).thenReturn(ventaCreada);

        ResponseEntity<Venta> response = ventaController.crearVenta(ventaDTO);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(ventaCreada, response.getBody());
        verify(ventaService, times(1)).crearVenta(ventaDTO);
    }

    @Test
    void testObtenerVenta() {
        Long ventaId = 1L;
        VentaConDTO ventaConDTO = mock(VentaConDTO.class);
        when(ventaService.obtenerVentaConProductos(ventaId)).thenReturn(ventaConDTO);

        ResponseEntity<VentaConDTO> response = ventaController.obtenerVenta(ventaId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ventaConDTO, response.getBody());
        verify(ventaService, times(1)).obtenerVentaConProductos(ventaId);
    }

    @Test
    void testObtenerVentasPorCliente() {
        String clienteId = "123";
        List<Venta> ventas = List.of(new Venta(), new Venta());
        when(ventaService.obtenerVentasPorClienteId(clienteId)).thenReturn(ventas);

        ResponseEntity<List<Venta>> response = ventaController.obtenerVentasPorCliente(clienteId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(ventas, response.getBody());
        verify(ventaService, times(1)).obtenerVentasPorClienteId(clienteId);
    }
}
