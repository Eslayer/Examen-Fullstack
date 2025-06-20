package com.Micro.productos.Service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.junit.jupiter.api.extension.ExtendWith;

import com.Micro.productos.Model.Producto;
import com.Micro.productos.Repository.ProductoRepository;

@ExtendWith(MockitoExtension.class)
public class ProductoServiceTest {


    @InjectMocks
    private ProductoService productoService;

    @Mock
    private ProductoRepository productoRepository;



    @Test
    void testDelete() {
        Long productoId = 1L;

        
        productoService.delete(productoId);

        verify(productoRepository).deleteById(productoId);
    }


    @Test
    void testFindAll() {
        Producto producto = new Producto(1L, "Producto1", "Descripcion1", 100.0, "Categoria1", "Marca1");
                
            
            when(productoRepository.findAll()).thenReturn(List.of(producto));

                
            List<Producto> productos = productoService.findAll();

            assertNotNull(productos);
            assertEquals(1, productos.size());

    }

    @Test
    void testFindById() {
        Producto producto = new Producto(1L, "Producto1", "Descripcion1", 100.0, "Categoria1", "Marca1");

        
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        assertEquals("Producto1", resultado.getNombre());
    }


    @Test
    void testFindByNombre() {
        Producto producto = new Producto(1L, "Producto1", "Descripcion1", 100.0, "Categoria1", "Marca1");

        
        when(productoRepository.findByNombre("Producto1")).thenReturn(List.of(producto));

        List<Producto> resultado = productoService.findByNombre("Producto1");

        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        assertEquals("Producto1", resultado.get(0).getNombre());
    }


    @Test
    void testSave() {
        Producto producto = new Producto(1L, "Producto1", "Descripcion1", 100.0, "Categoria1", "Marca1");

        
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.save(producto);

        assertNotNull(resultado);
        assertEquals("Producto1", resultado.getNombre());
        assertEquals(100.0, resultado.getPrecio());
}

}
