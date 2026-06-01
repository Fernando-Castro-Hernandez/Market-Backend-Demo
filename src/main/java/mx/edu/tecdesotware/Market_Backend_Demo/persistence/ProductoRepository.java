package mx.edu.tecdesotware.Market_Backend_Demo.persistence;

import mx.edu.tecdesotware.Market_Backend_Demo.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity.Producto;

import java.util.List;

public class ProductoRepository {

    private ProductoCrudRepository  productoCrudRepository;

    // SELECT * FROM productos
    public List<Producto> getAll(){
        //Se "Castea" de Iterable a la Lista
        return (List<Producto>) productoCrudRepository.findAll();
    }
}

