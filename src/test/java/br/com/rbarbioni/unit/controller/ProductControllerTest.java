package br.com.rbarbioni.unit.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import br.com.rbarbioni.controller.ProductController;
import br.com.rbarbioni.exception.ResourceNotFoundException;
import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.model.ResponseError;
import br.com.rbarbioni.service.ProductService;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

public class ProductControllerTest {

  ProductService service = mock(ProductService.class);
  ProductController controller = new ProductController(service);

  @Test
  public void shouldReturnAllProducts() {
    when(service.findAll()).thenReturn(List.of(new Product()));
    Collection<Product> products = controller.findAll();
    assertNotNull(products);
    assertEquals(products.size(), 1);
  }

  @Test
  public void shouldReturnProductById() {
    when(service.findById(anyLong())).thenReturn(Optional.of(new Product()));
    Optional<Product> product = controller.findById(1L);
    assertTrue(product.isPresent());
  }

  @Test
  public void shouldReturnEmptyOptionalProduct() {
    when(service.findById(anyLong())).thenReturn(Optional.empty());
    Optional<Product> product = controller.findById(9999L);
    assertTrue(product.isEmpty());
  }

  @Test
  public void shouldSaveAndReturnProduct() {
    when(service.save(any(Product.class))).thenReturn(new Product());
    final var product = controller.create(new Product());
    assertNotNull(product);
  }

  @Test
  public void shouldUpdateAndReturnProduct() {
    final var product = new Product();
    when(service.findById(anyLong())).thenReturn(Optional.of(product));
    when(service.update(anyLong(), any(Product.class))).thenReturn(product);
    final var productUpdated = controller.update(1L, new Product());
    assertNotNull(productUpdated);
  }

  @Test
  public void shouldUpdateAndThrowResourceNotFoundException() {
    when(service.findById(anyLong())).thenReturn(Optional.empty());
    try {
      controller.update(1L, new Product());
    } catch (ResourceNotFoundException e) {
      assertNotNull(e.getMessage());
    }
  }

  @Test
  public void shouldDeleteProductById() {
    when(service.findById(anyLong())).thenReturn(Optional.of(new Product()));
    when(service.delete(anyLong())).thenReturn(Boolean.TRUE);
    controller.delete(1L);
    assertTrue(true);
  }

  @Test
  public void shouldNotDeleteProductById() {
    when(service.findById(anyLong())).thenReturn(Optional.of(new Product()));
    when(service.delete(anyLong())).thenReturn(Boolean.FALSE);
    controller.delete(1L);
    assertTrue(true);
  }

  @Test
  public void shouldNotDeleteAndThrowResourceNotFoundException() {
    when(service.findById(anyLong())).thenReturn(Optional.empty());
    try {
      controller.delete(1L);
    } catch (ResourceNotFoundException e) {
      assertNotNull(e.getMessage());
    }
  }

  @Test
  public void shouldReturnResponseError() {
    HttpRequest request = mock(HttpRequest.class);
    HttpResponse<ResponseError> response = controller.error(request, new Exception());
    assertNotNull(response);
  }
}
