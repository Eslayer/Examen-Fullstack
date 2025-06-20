package com.Micro.productos.Controller;

import org.springframework.web.bind.annotation.RestController;

import com.Micro.productos.Model.Producto;
import com.Micro.productos.Service.ProductoService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private final ProductoService productoService;

    public ProductoController(ProductoService productoService) {
        this.productoService = productoService;
    }

    // Endpoint LISTAR TODOS LOS PRODUCTOS
    @GetMapping
    public ResponseEntity<List<Producto>> getProducto(){
        return ResponseEntity.ok(productoService.findAll());
    }

    // Endpoint CREAR PRODUCTO
    @PostMapping
    public ResponseEntity<Producto> crearProducto(@RequestBody Producto producto) {
        productoService.save(producto);
        return new ResponseEntity<>(producto, HttpStatus.CREATED);
    }

    // Endpoint Buscar PRODUCTO
    @GetMapping("/{id}")
    public ResponseEntity<Producto> getProductoById(@PathVariable Long id) {
        return ResponseEntity.ok(productoService.findById(id));
    }
    // Endpoint Buscar PRODUCTO por nombre
    @GetMapping("/categoria/{nombre}")
    public ResponseEntity<List<Producto>> getProductoPorNombre(@PathVariable String nombre) {
        return ResponseEntity.ok(productoService.findByNombre(nombre));
    }








}