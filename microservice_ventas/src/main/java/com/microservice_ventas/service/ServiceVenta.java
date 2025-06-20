package com.microservice_ventas.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.microservice_ventas.Repository.RepositoryVenta;
import com.microservice_ventas.client.ProductoCliente;
import com.microservice_ventas.dto.ClienteDTO;
import com.microservice_ventas.dto.CuponDTO;
import com.microservice_ventas.dto.DetalleVentaDTO;
import com.microservice_ventas.dto.Productodto;
import com.microservice_ventas.dto.VentaConDTO;
import com.microservice_ventas.model.DetalleVenta;
import com.microservice_ventas.model.Venta;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceVenta {

    private final RepositoryVenta ventaRepository;
    private final ProductoCliente productoClient;
    private final ProductoCliente clienteRestClient;

    @Transactional
    public Venta crearVenta(VentaConDTO ventaDTO) {
        Venta venta = Venta.builder()
                .clienteId(ventaDTO.getClienteId())
                .fecha(LocalDateTime.now())
                .build();

        List<DetalleVenta> detalles = ventaDTO.getDetalles().stream()
                .map(detalleDTO -> DetalleVenta.builder()
                        .productoId(detalleDTO.getProductoId())
                        .cantidad(detalleDTO.getCantidad())
                        .venta(venta)
                        .build())
                .toList();

        venta.setDetalles(detalles);
        return ventaRepository.save(venta);
    }

    public Optional<Venta> obtenerVentaPorId(Long id) {
        return ventaRepository.findById(id);
    }

    public List<Venta> obtenerVentasPorClienteId(String clienteId) {
        return ventaRepository.findByClienteId(clienteId);
    }

    public boolean validarCupon(CuponDTO cuponDTO) {
        return "DESC10".equalsIgnoreCase(cuponDTO.getCodigo());
    }

    public VentaConDTO obtenerVentaConProductos(Long ventaId) {
        Venta venta = ventaRepository.findById(ventaId)
                .orElseThrow(() -> new RuntimeException("Venta no encontrada"));

        
        ClienteDTO cliente = clienteRestClient.obtenerClientePorId(venta.getClienteId());

        List<DetalleVentaDTO> detallesConProducto = venta.getDetalles().stream().map(detalle -> {
            Productodto producto = productoClient.obtenerProductoPorId(detalle.getProductoId());
            return DetalleVentaDTO.builder()
                    .id(detalle.getId())
                    .productoId(detalle.getProductoId())
                    .cantidad(detalle.getCantidad())
                    .nombreProducto(producto.getNombre())
                    .precioProducto(producto.getPrecio())
                    .build();
        }).toList();

        return VentaConDTO.builder()
                .id(venta.getId())
                .fecha(venta.getFecha())
                .clienteId(venta.getClienteId())
                .nombreCliente(cliente.getNombre())
                .detalles(detallesConProducto)
                .build();
    }
}
