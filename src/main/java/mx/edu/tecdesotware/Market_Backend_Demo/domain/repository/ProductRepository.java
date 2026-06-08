package mx.edu.tecdesotware.Market_Backend_Demo.domain.repository;

import mx.edu.tecdesotware.Market_Backend_Demo.domain.Product;
import mx.edu.tecdesotware.Market_Backend_Demo.persistence.entity.Producto;

import java.util.List;
import java.util.Optional;

public interface ProductRepository {

    List<Product> getAll();
    Optional<List<Product>> getByCategory(int categoryId);
    Optional<List<Product>> getScarceProducts(int quantity);
    Optional<Product> getProduct(int productId);
    Product save(Product product);
    void delete(int productId);


}
