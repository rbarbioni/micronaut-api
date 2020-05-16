package br.com.rbarbioni.unit.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import br.com.rbarbioni.model.ViolationField;
import org.junit.jupiter.api.Test;

class ViolationFieldTest {

  @Test
  public void violationFieldTest() {
    ViolationField violationField = new ViolationField("field", "error");
    assertEquals(violationField.getField(), "field");
    assertEquals(violationField.getError(), "error");
  }
}
