package br.com.rbarbioni.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import br.com.rbarbioni.model.ResponseError;
import br.com.rbarbioni.model.ViolationField;
import java.util.Collections;
import org.junit.jupiter.api.Test;

class ResponseErrorTest {

  @Test
  public void responseErrorTest1() {
    final var responseError =
        new ResponseError(
            "message", "path", Collections.singletonList(new ViolationField("field", "error")));
    assertEquals(responseError.getMessage(), "message");
    assertEquals(responseError.getPath(), "path");
    assertNotNull(responseError.getViolations());
    assertNotNull(responseError.toString());
  }

  @Test
  public void responseErrorTest2() {
    final var responseError = new ResponseError("message", "path");
    assertEquals(responseError.getMessage(), "message");
    assertEquals(responseError.getPath(), "path");
    assertEquals(responseError.getViolations().size(), 0);
    assertNotNull(responseError.toString());
  }
}
