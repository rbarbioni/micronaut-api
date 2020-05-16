package br.com.rbarbioni.integration;

import static org.junit.jupiter.api.Assertions.*;

import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.model.ResponseError;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.client.RxHttpClient;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.http.client.exceptions.HttpClientResponseException;
import io.micronaut.test.annotation.MicronautTest;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import javax.inject.Inject;
import org.junit.jupiter.api.Test;

@MicronautTest
public class ProductIntegrationTest {

  @SuppressWarnings("unused")
  @Inject
  @Client("/api/v1")
  RxHttpClient client;

  final ObjectMapper mapper = new ObjectMapper();

  @Test
  public void shouldReturnAllProductsTest() {
    final HttpRequest<String> request = HttpRequest.GET("/products");
    final var products = client.toBlocking().retrieve(request, List.class);
    assertTrue(products.size() > 0);
  }

  @Test
  public void shouldCreateAndReturnValidProductTest() {
    final var product = new Product(UUID.randomUUID().toString(), "SNES", 49.99);
    final var request = HttpRequest.POST("/products", product);
    final var response = client.toBlocking().exchange(request, Product.class);
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(response.body(), product);
  }

  @Test
  public void shouldReturnErrorWhenCreateInvalidProductTest() throws JsonProcessingException {
    final var product = new Product(null, null, null);
    final var request = HttpRequest.POST("/products", product);
    try {
      client.toBlocking().exchange(request, String.class);
    } catch (HttpClientResponseException e) {
      assertEquals(e.getStatus(), HttpStatus.BAD_REQUEST);
      ResponseError responseError =
          mapper.readValue(
              Objects.requireNonNull(e.getResponse().body()).toString(), ResponseError.class);
      assertNotNull(responseError);
      assertTrue(responseError.getViolations().size() > 0);
    }
  }

  @Test
  public void shouldReturnProductByCodeTest() {
    final var product = new Product(UUID.randomUUID().toString(), "SNES", 49.99);
    var request = HttpRequest.POST("/products", product);
    var response = client.toBlocking().exchange(request, Product.class);
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(response.body(), product);

    request =
        HttpRequest.GET(
            String.format("/products/%s", Objects.requireNonNull(response.body()).getId()));
    response = client.toBlocking().exchange(request, Product.class);
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(response.body(), product);
  }

  @Test
  public void shouldReturnProductNotFoundTest() {
    final var request = HttpRequest.GET(String.format("/products/%s", "9999"));
    try {
      client.toBlocking().exchange(request, String.class);
    } catch (HttpClientResponseException e) {
      assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
  }

  @Test
  public void shouldReturnAndUpdateValidProductTest() {
    final var product = new Product(UUID.randomUUID().toString(), "SNES", 49.99);
    var request = HttpRequest.POST("/products", product);
    var response = client.toBlocking().exchange(request, Product.class);

    assertEquals(response.getStatus(), HttpStatus.OK);
    final var productNew = response.body();
    assertEquals(productNew, product);

    final var productUpdate =
        new Product(
            Objects.requireNonNull(productNew).getId(),
            productNew.getCode(),
            productNew.getName(),
            29.99);
    request =
        HttpRequest.PUT(
            String.format("/products/%s", Objects.requireNonNull(response.body()).getId()),
            productUpdate);
    response = client.toBlocking().exchange(request, Product.class);
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(Objects.requireNonNull(response.body()).getCode(), productUpdate.getCode());
    assertEquals(Objects.requireNonNull(response.body()).getPrice(), 29.99);
  }

  @Test
  public void shouldReturnResourceNotFoundWhenIdNotExistsBeforeUpdate() {
    final Product product = new Product(9999L, "Code", "Name", 99.99);
    var request =
        HttpRequest.PUT(
            String.format("/products/%s", Objects.requireNonNull(product.getId())), product);
    try {
      client.toBlocking().exchange(request, Product.class);
    } catch (HttpClientResponseException e) {
      assertEquals(e.getStatus(), HttpStatus.NOT_FOUND);
    }
  }

  @Test
  public void shouldDeleteValidProductTest() {
    final var product = new Product(UUID.randomUUID().toString(), "SNES", 49.99);
    var request = HttpRequest.POST("/products", product);
    var response = client.toBlocking().exchange(request, Product.class);
    assertEquals(response.getStatus(), HttpStatus.OK);
    assertEquals(response.body(), product);

    request =
        HttpRequest.DELETE(
            String.format("/products/%s", Objects.requireNonNull(response.body()).getId()));
    response = client.toBlocking().exchange(request);
    assertEquals(response.getStatus(), HttpStatus.NO_CONTENT);
  }
}
