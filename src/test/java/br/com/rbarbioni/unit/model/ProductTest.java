package br.com.rbarbioni.unit.model;

import static org.junit.jupiter.api.Assertions.*;

import br.com.rbarbioni.model.Product;
import org.junit.jupiter.api.Test;

public class ProductTest {

  @Test
  public void productTest() {
    Product product = new Product(1L, "code", "name", 9.9);
    assertEquals(product.getCode(), "code");
    assertEquals(product.getName(), "name");
    assertEquals(product.getPrice(), 9.9);
    assertEquals(product.getId(), 1L);
    assertNotNull(product.getCreatedAt());
    assertNotNull(product.toString());
    assertNotEquals(product.hashCode(), 0);
    assertEquals(product, product);
    assertNotEquals(product, null);
    assertNotEquals(product, "");
  }
}
