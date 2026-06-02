package mx.edu.tecdesotware.Market_Backend_Demo.persistence;

import mx.edu.tecdesotware.Market_Backend_Demo.persistence.crud.ProductoCrudRepository;
import mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity.Producto;

import java.util.List;
import java.util.Optional;

public class ProductoRepository {

    private ProductoCrudRepository  productoCrudRepository;

    // SELECT * FROM productos
    public List<Producto> getAll(){
        //Se "Castea" de Iterable a la Lista
        return (List<Producto>) productoCrudRepository.findAll();
    }

    public List<Producto> getByCategoria(int idCategoria){
        return productoCrudRepository.findByIdCategoriaOrderByNombreAsc(idCategoria);
    }

    public Optional<List<Producto>> getEscasos(int cantidad){
        return productoCrudRepository.findByCantidadStockLessThanAndEstado(cantidad, true);
    }

    // Obtener un producto dado el id
    public Optional<Producto> getProductoById(int idProducto){
        return productoCrudRepository.findById(idProducto);
    }

    // Guardar un producto
    public Producto save(Producto producto){
        return productoCrudRepository.save(producto);
    }

    // Eliminar por id
    public void delete(int idProducto){
        productoCrudRepository.deleteById(idProducto);
    }

}

