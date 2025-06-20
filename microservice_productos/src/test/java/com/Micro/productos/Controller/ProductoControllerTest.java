package com.Micro.productos.Controller;

import java.util.Arrays;

import org.springframework.http.MediaType;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.Micro.productos.Model.Producto;
import com.Micro.productos.Service.ProductoService;
import com.fasterxml.jackson.databind.ObjectMapper;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@WebMvcTest(ProductoController.class)
public class ProductoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ProductoService productoService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearProducto() throws Exception {
        Producto producto = new Producto(1L, "Nuevo Producto", "Categoría Z", 15.0, "Descripción", "Marca");

        when(productoService.save(Mockito.any(Producto.class))).thenReturn(producto);

        mockMvc.perform(post("/productos")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(producto)))
            .andExpect(status().isCreated())
            .andExpect(jsonPath("$.nombre").value("Nuevo Producto"));
    }
    @Test
    void testListarProducto() throws Exception {
        Producto p1 = new Producto(1L, "Producto A", "Categoría X", 10.0, "Descripción A", "Marca A");
        Producto p2 = new Producto(2L, "Producto B", "Categoría Y", 20.0, "Descripción B", "Marca B");

        given(productoService.findAll()).willReturn(Arrays.asList(p1, p2));

        mockMvc.perform(get("/productos"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.length()").value(2))
            .andExpect(jsonPath("$[0].nombre").value("Producto A"))
            .andExpect(jsonPath("$[1].nombre").value("Producto B"));
    }

    @Test
    void testBuscarProductoById() throws Exception {
        Producto p1 = new Producto(1L, "Producto A", "Categoría X", 10.0, "Descripción A", "Marca A");

        given(productoService.findById(1L)).willReturn(p1);

        mockMvc.perform(get("/productos/1"))
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.id").value(1))
            .andExpect(jsonPath("$.nombre").value("Producto A"))
            .andExpect(jsonPath("$.categoria").value("Categoría X"))
            .andExpect(jsonPath("$.precio").value(10.0))
            .andExpect(jsonPath("$.descripcion").value("Descripción A"))
            .andExpect(jsonPath("$.marca").value("Marca A"));

    }

    @Test
    void testBuscarProductoByNombre() throws Exception {
        Producto producto = new Producto(1L, "Producto Especial", "Categoria Especial", 8.0, "Descripción Especial", "Marca Especial");
        
        given(productoService.findByNombre("Producto Especial")).willReturn(Arrays.asList(producto));

        mockMvc.perform(get("/productos/categoria/Producto Especial"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$[0].nombre").value("Producto Especial"));
        }
    }
