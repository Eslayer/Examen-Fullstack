package com.microservice_ventas.client;



import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.microservice_ventas.dto.ClienteDTO;
import com.microservice_ventas.dto.Productodto;


@Service
public class ProductoCliente {

    private final RestTemplate restTemplate;

    public ProductoCliente(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public Productodto obtenerProductoPorId(Long productoid) {
        String url = "http://localhost:8081/productos/" + productoid;
        return restTemplate.getForObject(url, Productodto.class);
    }

        public ClienteDTO obtenerClientePorId(String clienteId) {
        String url = "http://localhost:8082/clientes/" + clienteId;
        return restTemplate.getForObject(url, ClienteDTO.class);
    }
}
