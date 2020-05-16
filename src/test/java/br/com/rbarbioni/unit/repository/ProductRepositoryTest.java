package br.com.rbarbioni.unit.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.repository.ProductRepositoryImpl;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.junit.jupiter.api.Test;

public class ProductRepositoryTest {

  EntityManager entityManager = mock(EntityManager.class);
  TypedQuery<Product> typedQuery = mock(TypedQuery.class);
  ProductRepositoryImpl productRepository = new ProductRepositoryImpl(entityManager);

  @Test
  public void findByIdTest() {
    when(entityManager.find(any(Class.class), anyLong())).thenReturn(new Product());
    Optional<Product> product = productRepository.findById(1L);
    assertNotNull(product);
  }

  @Test
  public void findByCodeTest() {
    when(entityManager.createQuery(anyString(), any(Class.class))).thenReturn(typedQuery);
    when(typedQuery.setParameter(anyString(), any(Object.class))).thenReturn(typedQuery);
    when(typedQuery.getSingleResult()).thenReturn(new Product());
    Optional<Product> product = productRepository.findByCode("code");
    assertNotNull(product);
  }

  @Test
  public void saveTest() {
    Product product = productRepository.save(new Product());
    assertNotNull(product);
  }

  @Test
  public void updateTest() {
    when(entityManager.merge(any(Product.class))).thenReturn(new Product());
    Product product = productRepository.update(new Product());
    assertNotNull(product);
  }

  @Test
  public void deleteTest() {
    when(entityManager.find(any(Class.class), anyLong())).thenReturn(new Product());
    productRepository.delete(1L);
  }

  @Test
  public void findAllTest() {
    when(entityManager.createQuery(anyString(), any(Class.class))).thenReturn(typedQuery);
    when(typedQuery.getResultList()).thenReturn(Arrays.asList(new Product()));
    List<Product> products = productRepository.findAll();
    assertNotNull(products);
    assertEquals(products.size(), 1);
  }
}
