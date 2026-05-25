package mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "clientes")

public class ComprasProductos {

    @EmbeddedId
    private CompraProductoPK id;

    private Integer cantidad;
    private Double total;
    private Boolean estado;


    




}
