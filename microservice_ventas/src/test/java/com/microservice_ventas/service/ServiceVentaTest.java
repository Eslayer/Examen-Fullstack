package com.microservice_ventas.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import com.microservice_ventas.client.ProductoCliente;
import com.microservice_ventas.dto.*;
import com.microservice_ventas.model.DetalleVenta;
import com.microservice_ventas.model.Venta;
import com.microservice_ventas.Repository.RepositoryVenta;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ServiceVentaTest {

    @Mock
    private RepositoryVenta ventaRepository;

    @Mock
    private ProductoCliente productoClient;

    @Mock
    private ProductoCliente clienteRestClient;

    @InjectMocks
    private ServiceVenta serviceVenta;

@Test
void testObtenerVentaConProductos() {
    Long ventaId = 1L;

    DetalleVenta detalle = DetalleVenta.builder()
            .id(1L)
            .productoId(10L)
            .cantidad(2)
            .build();

    Venta ventaMock = Venta.builder()
            .id(ventaId)
            .clienteId("123")
            .fecha(LocalDateTime.now())
            .detalles(List.of(detalle))
            .build();

    when(ventaRepository.findById(ventaId)).thenReturn(Optional.of(ventaMock));
    when(clienteRestClient.obtenerClientePorId("123"))
        .thenReturn(ClienteDTO.builder().clienteId("123").nombre("Cliente Test").build());

    // Aquí está el cambio clave:
    when(productoClient.obtenerProductoPorId(anyLong()))
        .thenReturn(Productodto.builder().id(10L).nombre("Producto Test").precio(100.0).build());

    VentaConDTO resultado = serviceVenta.obtenerVentaConProductos(ventaId);

    assertNotNull(resultado);
    assertEquals(ventaId, resultado.getId());
    assertEquals("Cliente Test", resultado.getNombreCliente());
    assertEquals(1, resultado.getDetalles().size());
    assertEquals("Producto Test", resultado.getDetalles().get(0).getNombreProducto());
}


    @Test
    void testCrearVenta() {
        DetalleVentaDTO detalleDTO = DetalleVentaDTO.builder()
                .productoId(10L)
                .cantidad(3)
                .build();

        VentaConDTO ventaDTO = VentaConDTO.builder()
                .clienteId("123")
                .detalles(List.of(detalleDTO))
                .build();

        Venta ventaGuardada = Venta.builder()
                .id(1L)
                .clienteId("123")
                .build();

        when(ventaRepository.save(any(Venta.class))).thenReturn(ventaGuardada);

        Venta resultado = serviceVenta.crearVenta(ventaDTO);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("123", resultado.getClienteId());
    }

    @Test
    void testObtenerVentaPorId() {
        Long id = 1L;
        Venta ventaMock = Venta.builder()
                .id(id)
                .clienteId("123")
                .build();

        when(ventaRepository.findById(id)).thenReturn(Optional.of(ventaMock));

        Optional<Venta> resultado = serviceVenta.obtenerVentaPorId(id);

        assertTrue(resultado.isPresent(), "La venta debería estar presente");
        assertEquals(id, resultado.get().getId());
        assertEquals("123", resultado.get().getClienteId());
    }
}



