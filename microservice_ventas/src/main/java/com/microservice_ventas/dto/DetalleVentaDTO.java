package com.microservice_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleVentaDTO {


    private Long id;
    private Long productoId;
    private Integer cantidad;
    private String nombreProducto;
    private Double precioProducto;



}
