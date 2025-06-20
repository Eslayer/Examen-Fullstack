package com.microservice_ventas.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class VentaConDTO {
    private Long id;
    private LocalDateTime fecha;
    private String clienteId;
    private String nombreCliente;
    private List<DetalleVentaDTO> detalles;

}
