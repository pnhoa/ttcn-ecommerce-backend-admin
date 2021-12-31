package com.ttcn.ecommerce.backend.app.api;

import com.ttcn.ecommerce.backend.app.dto.MessageResponse;
import com.ttcn.ecommerce.backend.app.dto.ProductDTO;
import com.ttcn.ecommerce.backend.app.entity.Product;
import com.ttcn.ecommerce.backend.app.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin
public class ProductAPI {

    @Autowired
    private IProductService productService;

    @GetMapping("")
    public ResponseEntity<List<Product>> findAll(){

        List<Product> theProducts = productService.findAll();
        return new ResponseEntity<>(theProducts, HttpStatus.OK);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> findById(@PathVariable("id") Long theId){

        Product theProduct = productService.findById(theId);
        return new ResponseEntity<>(theProduct, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<MessageResponse> createProduct(@Valid @RequestBody ProductDTO theProductDto, BindingResult theBindingResult){

        if(theBindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for create product", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = productService.createProduct(theProductDto);
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    public ResponseEntity<MessageResponse> updateProduct(@PathVariable("id") Long theId,
                                                         @Valid @RequestBody ProductDTO theProductDto, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            return new ResponseEntity<MessageResponse>(new MessageResponse("Invalid value for update product", HttpStatus.BAD_REQUEST, LocalDateTime.now()), HttpStatus.BAD_REQUEST);
        }

        MessageResponse messageResponse = productService.updateProduct(theId, theProductDto);
        return new ResponseEntity<MessageResponse>(messageResponse, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable("id") Long theId){

        productService.deleteProduct(theId);
        return new ResponseEntity<>(new MessageResponse("Delete successfully!", HttpStatus.OK, LocalDateTime.now()), HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<List<Product>> findProductsByCategoryId(@PathVariable("categoryId") Long categoryId ){

        List<Product> theProducts = productService.findByCategoryId(categoryId);
        return new ResponseEntity<>(theProducts, HttpStatus.OK);

    }

    @GetMapping("/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam(name="key", required = false) String key){

        List<Product> theProducts = productService.search(key);

        return new ResponseEntity<>(theProducts, HttpStatus.OK);

    }
}
