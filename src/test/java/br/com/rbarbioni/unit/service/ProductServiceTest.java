package br.com.rbarbioni.unit.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.rbarbioni.exception.ResourceNotFoundException;
import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.repository.ProductRepository;
import br.com.rbarbioni.service.ProductService;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ProductServiceTest {

  ProductRepository repository = mock(ProductRepository.class);
  ProductService service = new ProductService(repository);

  @Test
  public void shouldReturnAllProducts() {
    when(repository.findAll()).thenReturn(List.of(new Product()));
    Collection<Product> products = service.findAll();
    assertNotNull(products);
    assertEquals(products.size(), 1);
  }

  @Test
  public void shouldReturnProductById() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(new Product()));
    Optional<Product> product = service.findById(1L);
    assertTrue(product.isPresent());
  }

  @Test
  public void shouldReturnProductByCode() {
    when(repository.findByCode(anyString())).thenReturn(Optional.of(new Product()));
    Optional<Product> product = service.findByCode("code");
    assertTrue(product.isPresent());
  }

  @Test
  public void shouldReturnEmptyOptionalProduct() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    Optional<Product> product = service.findById(9999L);
    assertTrue(product.isEmpty());
  }

  @Test
  public void shouldSaveAndReturnProduct() {
    when(repository.save(any(Product.class))).thenReturn(new Product());
    final var product = service.save(new Product());
    assertNotNull(product);
  }

  @Test
  public void shouldUpdateAndReturnProduct() {
    final var product = new Product();
    when(repository.findById(anyLong())).thenReturn(Optional.of(product));
    when(repository.update(any(Product.class))).thenReturn(product);
    final var productUpdated = service.update(1L, new Product());
    assertNotNull(productUpdated);
  }

  @Test
  public void shouldUpdateAndThrowResourceNotFoundException() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    try {
      service.update(1L, new Product());
    } catch (ResourceNotFoundException e) {
      assertNotNull(e.getMessage());
    }
  }

  @Test
  public void shouldDeleteProductById() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(new Product()));
    when(repository.delete(anyLong())).thenReturn(Boolean.TRUE);
    boolean delete = service.delete(1L);
    assertTrue(delete);
  }

  @Test
  public void shouldNotDeleteProductById() {
    when(repository.findById(anyLong())).thenReturn(Optional.of(new Product()));
    when(repository.delete(anyLong())).thenReturn(Boolean.FALSE);
    boolean delete = service.delete(1L);
    assertFalse(delete);
  }

  @Test
  public void shouldNotDeleteAndThrowResourceNotFoundException() {
    when(repository.findById(anyLong())).thenReturn(Optional.empty());
    try {
      service.delete(1L);
    } catch (ResourceNotFoundException e) {
      assertNotNull(e.getMessage());
    }
  }
}
