package com.maids.cc.store.management.backend.service;

import com.maids.cc.store.management.backend.dto.request.ProductRequestDto;
import com.maids.cc.store.management.backend.dto.response.ProductResponseDto;
import com.maids.cc.store.management.backend.entity.Category;
import com.maids.cc.store.management.backend.entity.Product;
import com.maids.cc.store.management.backend.extension.ProductExtension;
import com.maids.cc.store.management.backend.repository.CategoryRepository;
import com.maids.cc.store.management.backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class ProductService {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    CategoryRepository categoryRepository;
    

    public ResponseEntity<ProductResponseDto> getProduct(UUID id){

        try {
            Product product = productRepository.get(id);

            if(product == null){
                return new ResponseEntity<>(null, HttpStatus.FAILED_DEPENDENCY);
            }

            ProductResponseDto dto = ProductExtension.toProductDto(product);

            return new ResponseEntity<>(dto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<List<ProductResponseDto>> getAllProducts(){
        try {
            List<Product> products = productRepository.getAll();

            List<ProductResponseDto> productDtos = products.stream()
                    .map(ProductExtension::toProductDto)
                    .collect(Collectors.toList());

            return new ResponseEntity<>(productDtos,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<ProductResponseDto> addProduct(ProductRequestDto dto){
        try {
            Product product = ProductExtension.toProductEntity(dto);
            Product addedProduct = productRepository.save(product);

            if(dto.categoryId != null){
                Category category = categoryRepository.get(dto.categoryId);

                addedProduct.setCategory(category);

                category.addProduct(addedProduct);

                categoryRepository.save(category);

                addedProduct = productRepository.save(addedProduct);
            }

            ProductResponseDto responseDto = ProductExtension.toProductDto(addedProduct);

            return new ResponseEntity<>(responseDto, HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public ResponseEntity<ProductResponseDto> updateProduct(UUID id, ProductRequestDto dto){

        try {
            Product product = productRepository.get(id);

            if(product == null){
                return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
            }

            product.setName(dto.name);
            product.setDescription(dto.description);
            product.setPrice(dto.price);
            product.setQuantity(dto.quantity);

            Product addedProduct = productRepository.save(product);

            ProductResponseDto responseDto = ProductExtension.toProductDto(addedProduct);

            return new ResponseEntity<>(responseDto,HttpStatus.ACCEPTED);
        }catch (Exception e){
            return new ResponseEntity<>(null,HttpStatus.FAILED_DEPENDENCY);
        }
    }

    public boolean deleteProduct(UUID id){
        Product product = productRepository.get(id);

        if(product == null){
            return false;
        }

        productRepository.delete(id);

        return true;
    }
}
