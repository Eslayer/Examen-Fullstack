package com.Micro.productos.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;


@Entity
@Table(name = "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Producto {
    @Id
    @Column(unique = true, nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true)
    private String nombre;

    @Column(nullable = true)
    private String descripcion;

    @Column(nullable = true)
    private Double precio;

    @Column(nullable = true)
    private String categoria;

    @Column(nullable = true)
    private String marca;
}