package br.com.rbarbioni.controller;

import br.com.rbarbioni.exception.ResourceNotFoundException;
import br.com.rbarbioni.model.ResponseError;
import br.com.rbarbioni.model.ViolationField;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import javax.validation.ConstraintViolationException;
import javax.validation.ElementKind;
import javax.validation.Path;

public class ExceptionHandlerHelper {

  @SuppressWarnings("rawtypes")
  public static HttpResponse<ResponseError> error(HttpRequest request, Throwable e) {
    if (e instanceof ResourceNotFoundException) {
      return HttpResponse.notFound(new ResponseError(e.getMessage(), request.getPath()));
    }

    if (e instanceof ConstraintViolationException) {
      final ConstraintViolationException violationException = (ConstraintViolationException) e;
      return HttpResponse.badRequest(
          new ResponseError(
              e.getMessage(),
              request.getPath(),
              violationException
                  .getConstraintViolations()
                  .stream()
                  .map(
                      v ->
                          new ViolationField(
                              StreamSupport.stream(v.getPropertyPath().spliterator(), false)
                                  .filter(node -> ElementKind.PROPERTY.equals(node.getKind()))
                                  .map(Path.Node::getName)
                                  .collect(Collectors.joining()),
                              v.getMessage()))
                  .collect(Collectors.toList())));
    }

    return HttpResponse.serverError(new ResponseError(e.getMessage(), request.getPath()));
  }
}
