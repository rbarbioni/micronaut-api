package br.com.rbarbioni.unit.exception;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.rbarbioni.exception.ResourceNotFoundException;
import org.junit.jupiter.api.Test;

public class ResourceNotFoundExceptionTest {

  @Test
  public void resourceNotFoundExceptionTest() {
    ResourceNotFoundException message = new ResourceNotFoundException("message");
    assertEquals(message.getMessage(), "message");
  }
}
