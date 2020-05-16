package br.com.rbarbioni.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Collections;
import java.util.List;

public class ResponseError {

  private final String message;

  private final String path;

  private final List<ViolationField> violations;

  @JsonCreator
  public ResponseError(
      @JsonProperty("message") String message,
      @JsonProperty("path") String path,
      @JsonProperty("violations") List<ViolationField> violations) {
    this.message = message;
    this.path = path;
    this.violations = violations;
  }

  public ResponseError(String message, String path) {
    this(message, path, Collections.emptyList());
  }

  public String getMessage() {
    return message;
  }

  public String getPath() {
    return path;
  }

  public List<ViolationField> getViolations() {
    return violations;
  }

  @Override
  public String toString() {
    return "ResponseError{"
        + "message='"
        + message
        + '\''
        + ", path='"
        + path
        + '\''
        + ", violations="
        + violations
        + '}';
  }
}
