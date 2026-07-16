package mx.edu.tecdesotware.Market_Backend_Demo.web.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import mx.edu.tecdesotware.Market_Backend_Demo.domain.Product;
import mx.edu.tecdesotware.Market_Backend_Demo.domain.repository.ProductRepository;
import mx.edu.tecdesotware.Market_Backend_Demo.domain.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
@Tag(name = "Product", description = "Manage products in the store")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Operation(summary = "Get All Products", description = "Return a list of available products")
    @ApiResponse(responseCode = "200", description = "Successful retrieval of products")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("")
    public ResponseEntity<List<Product>> getAll(){
        return new ResponseEntity<>(productService.getAll(),  HttpStatus.OK);
    }


    @Operation(summary = "Get Product By ID", description = "Return a product list if its exists")
    @ApiResponse(responseCode = "200", description = "Product Found")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProduct(
            @Parameter(description = "ID of the product parameter", example = "8", required = true)
            @PathVariable("id") int productId){
        return productService.getProduct(productId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(summary = "Get Product By Category", description = "Return all products in a specific category")
    @ApiResponse(responseCode = "200", description = "Category Found")
    @ApiResponse(responseCode = "404", description = "Category not found")
    @ApiResponse(responseCode = "500", description = "Internal Server Error")
    @GetMapping("/category/{id}")
    public ResponseEntity<List<Product>> getProductByCategory(
            @Parameter(description = "ID of the category parameter", example = "1", required = true)
            @PathVariable("id") int categoryId){
        return productService.getByCategory(categoryId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    @Operation(
            summary = "Save New Product",
            description = "Register a new product and return the created product",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(
                            examples = @ExampleObject(
                                    name = "Example Product",
                                    value = """
                                            {
                                                "name" : "Mirinda",
                                                "categoryId": "5",
                                                "price": 20,
                                                "stock": 300,
                                                "active": true                                               
                                            }
                                            """
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "201", description = "Product created successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "409", description = "Product conflict (duplicate code or SKU)")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @PostMapping("")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        return new ResponseEntity<>(productService.save(product), HttpStatus.CREATED);
    }

    @Operation(summary = "Delete Product By ID", description = "Delete product if it exits")
    @ApiResponse(responseCode = "200", description = "Product deleted successfully")
    @ApiResponse(responseCode = "400", description = "Invalid product data")
    @ApiResponse(responseCode = "401", description = "Unauthorized")
    @ApiResponse(responseCode = "403", description = "Forbidden")
    @ApiResponse(responseCode = "404", description = "Product not found")
    @ApiResponse(responseCode = "500", description = "Internal server error")
    @DeleteMapping("/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") int productId){
        if (productService.delete(productId))
            return new ResponseEntity<>(HttpStatus.OK);
        else
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }





}
