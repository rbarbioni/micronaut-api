package br.com.rbarbioni.service;

import br.com.rbarbioni.exception.ResourceNotFoundException;
import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.repository.ProductRepository;
import io.micronaut.spring.tx.annotation.Transactional;
import java.util.Collection;
import java.util.Optional;
import javax.inject.Inject;

@SuppressWarnings("unused")
public class ProductService {

  private final ProductRepository productRepository;

  @Inject
  public ProductService(ProductRepository productRepository) {
    this.productRepository = productRepository;
  }

  @Transactional(readOnly = true)
  public Collection<Product> findAll() {
    return this.productRepository.findAll();
  }

  @Transactional(readOnly = true)
  public Optional<Product> findById(Long id) {
    return this.productRepository.findById(id);
  }

  @Transactional(readOnly = true)
  public Optional<Product> findByCode(String code) {
    return this.productRepository.findByCode(code);
  }

  @Transactional
  public Product save(Product product) {
    return this.productRepository.save(product);
  }

  @Transactional
  public Product update(Long id, Product product) {
    return this.findById(id)
        .map(p -> productRepository.update(product))
        .orElseThrow(
            () -> new ResourceNotFoundException(String.format("Product id %s not found", id)));
  }

  @Transactional
  public boolean delete(Long id) {
    return this.productRepository.delete(id);
  }
}
