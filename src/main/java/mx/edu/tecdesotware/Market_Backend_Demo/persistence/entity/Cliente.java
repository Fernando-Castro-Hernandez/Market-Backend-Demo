package mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "clientes")

public class Cliente {
    //Primary Key
    @Id
    private String id;

    private String nombre;

    private String apellidos;

    private String celular;

    private String direccion;

    @Column(name = "correo_electronico")
    private String correoElectronico;

    // Un cliente Puede tener muchas Compras
    @OneToMany(mappedBy =  "cliente")
    private List<Compra> compras;
}
