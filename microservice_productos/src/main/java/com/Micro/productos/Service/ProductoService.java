package com.Micro.productos.Service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import java.util.List;
import com.Micro.productos.Repository.ProductoRepository;
import com.Micro.productos.Model.Producto;

@Service
@Transactional
public class ProductoService {

    @Autowired
    private ProductoRepository productoRepository;

    


    // Endpoint LISTAR TODOS LOS PRODUCTOS

    public List<Producto> findAll(){
        return productoRepository.findAll();
    }

    // Endpoint Buscar PRODUCTO por id
    public Producto findById(long id){
        return productoRepository.findById(id).orElse(null);
    }

    
    // Endpoint Buscar PRODUCTO por nombre
    
    public List<Producto> findByNombre(String nombre) {
        return productoRepository.findByNombre(nombre);
}


    // Endpoint CREAR PRODUCTO
    public Producto save(Producto producto){
        return productoRepository.save(producto);
    }

    // Endpoint borrar PRODUCTO
    public void delete(Long id){
        productoRepository.deleteById(id);
    }
}