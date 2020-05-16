package br.com.rbarbioni.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ViolationField {
  private final String field;
  private final String error;

  @JsonCreator
  public ViolationField(@JsonProperty("field") String field, @JsonProperty("error") String error) {
    this.field = field;
    this.error = error;
  }

  public String getField() {
    return field;
  }

  public String getError() {
    return error;
  }

  @Override
  public String toString() {
    return "ViolationField{" + "field='" + field + '\'' + ", error='" + error + '\'' + '}';
  }
}
