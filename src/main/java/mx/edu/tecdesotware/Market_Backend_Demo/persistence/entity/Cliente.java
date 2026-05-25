package mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity;

import jakarta.persistence.*;

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




}
