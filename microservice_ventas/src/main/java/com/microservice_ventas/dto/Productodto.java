package com.microservice_ventas.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Productodto {
    private Long id;
    private String nombre;
    private Double precio;
}
