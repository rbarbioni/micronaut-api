package br.com.rbarbioni.unit.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;

import br.com.rbarbioni.controller.ExceptionHandlerHelper;
import br.com.rbarbioni.exception.ResourceNotFoundException;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpStatus;
import org.junit.jupiter.api.Test;

public class ExceptionHandlerHelperTest {

  HttpRequest request = mock(HttpRequest.class);

  @Test
  public void shouldReturnHttpResponseWithResourceNotFoundException() {
    final var helper = new ExceptionHandlerHelper();
    final var exception = new ResourceNotFoundException("message");
    final var error = helper.error(request, exception);
    assertNotNull(error);
    assertEquals(error.getStatus(), HttpStatus.NOT_FOUND);
    assertEquals(error.getBody().get().getMessage(), "message");
  }

  @Test
  public void shouldReturnHttpResponseGenericException() {
    final var helper = new ExceptionHandlerHelper();
    final var exception = new RuntimeException("message");
    final var error = helper.error(request, exception);
    assertNotNull(error);
    assertEquals(error.getStatus(), HttpStatus.INTERNAL_SERVER_ERROR);
    assertEquals(error.getBody().get().getMessage(), "message");
  }
}
