package com.microservice_ventas.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.microservice_ventas.dto.VentaConDTO;
import com.microservice_ventas.model.Venta;
import com.microservice_ventas.service.ServiceVenta;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/ventas")
@RequiredArgsConstructor
public class VentaController {

    private final ServiceVenta ventaService;

    // POST /ventas : crea una nueva venta
    @PostMapping
    public ResponseEntity<Venta> crearVenta(@RequestBody VentaConDTO ventaDTO) {
        Venta ventaCreada = ventaService.crearVenta(ventaDTO);
        return new ResponseEntity<>(ventaCreada, HttpStatus.CREATED);
    }

    // GET /ventas/{id} : obtiene una venta por id
    @GetMapping("/{id}")
    public ResponseEntity<VentaConDTO> obtenerVenta(@PathVariable Long id) {
        VentaConDTO ventaConDTO = ventaService.obtenerVentaConProductos(id);
        return ResponseEntity.ok(ventaConDTO);
    }

    // GET /ventas/cliente/{clienteId} : obtiene las ventas de un cliente
    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Venta>> obtenerVentasPorCliente(@PathVariable String clienteId) {
        List<Venta> ventas = ventaService.obtenerVentasPorClienteId(clienteId);
        return ResponseEntity.ok(ventas);
    }
}
