package mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity;

import jakarta.persistence.*;

import java.time.LocalDateTime;


@Entity
@Table (name = "Compras")
public class Compra {

    // Llave primaria
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_compra")
    private int idCompra;


    @Column (name = "id_cliente")
    private String idCliente;


    private LocalDateTime fecha;

    @Column (name = "medio_pago")
    private String medioPago;

    private String comentario;
    private String estado;


}
