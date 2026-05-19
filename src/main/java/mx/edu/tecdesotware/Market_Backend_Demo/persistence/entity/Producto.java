package mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column (name = "id_producto")
    private int idProdcuto;


    private String nombre;

    @Column (name = "id_categorias")
    private Integer id_categoria;


    @Column (name = "codigo_barras")
    private Integer codigoBarras;


    @Column (name = "precio_ventas")
    private Integer precioVentas;


    @Column (name = "cantidad_stock")
    private Integer cantidadStock;

    private boolean estado;



}
