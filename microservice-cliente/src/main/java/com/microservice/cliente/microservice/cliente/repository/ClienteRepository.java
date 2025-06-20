package com.microservice.cliente.microservice.cliente.repository;




import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.microservice.cliente.microservice.cliente.model.Cliente;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, Long> {


    

    
}
