package com.example.productapp.service;



import com.example.productapp.dto.ProductDTO;
import com.example.productapp.mapper.ProductMapper;
import com.example.productapp.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public ProductDTO saveProduct(ProductDTO productDTO) {
        var product = ProductMapper.INSTANCE.toEntity(productDTO);
        var savedProduct = productRepository.save(product);
        return ProductMapper.INSTANCE.toDTO(savedProduct);
    }

    public List<ProductDTO> getAllProducts() {
        return productRepository.findAll().stream()
                .map(ProductMapper.INSTANCE::toDTO)
                .collect(Collectors.toList());
    }

    public ProductDTO getProductById(Long id) {
        return productRepository.findById(id)
                .map(ProductMapper.INSTANCE::toDTO)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        var existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        existingProduct.setName(productDTO.getName());
        existingProduct.setPrice(productDTO.getPrice());
        existingProduct.setType(productDTO.getType());

        var updatedProduct = productRepository.save(existingProduct);
        return ProductMapper.INSTANCE.toDTO(updatedProduct);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
