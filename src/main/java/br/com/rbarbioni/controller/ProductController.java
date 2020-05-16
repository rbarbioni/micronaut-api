package br.com.rbarbioni.controller;

import br.com.rbarbioni.model.Product;
import br.com.rbarbioni.model.ResponseError;
import br.com.rbarbioni.service.ProductService;
import io.micrometer.core.annotation.Timed;
import io.micronaut.configuration.metrics.annotation.RequiresMetrics;
import io.micronaut.configuration.metrics.micrometer.annotation.MircometerTimed;
import io.micronaut.http.HttpRequest;
import io.micronaut.http.HttpResponse;
import io.micronaut.http.HttpStatus;
import io.micronaut.http.MediaType;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Error;
import io.micronaut.validation.Validated;
import java.util.Collection;
import java.util.Optional;
import javax.inject.Inject;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@RequiresMetrics
@Controller("/api/v1/products")
@Produces(MediaType.APPLICATION_JSON)
public class ProductController {

  private final ProductService productService;

  @Inject
  public ProductController(ProductService productService) {
    this.productService = productService;
  }

  @MircometerTimed
  @Timed(value = "method.products.findAll", description = "Save alarm timer (Single)")
  @Get
  public Collection<Product> findAll() {
    return this.productService.findAll();
  }

  @MircometerTimed
  @Get("/{id}")
  @Validated
  public Optional<Product> findById(@NotNull @PathVariable Long id) {
    return this.productService.findById(id);
  }

  @MircometerTimed
  @Post
  @Validated
  public Product create(@Valid @Body Product product) {
    return this.productService.save(product);
  }

  @MircometerTimed
  @Put("/{id}")
  @Validated
  public Product update(@NotNull @PathVariable Long id, @Valid @Body Product product) {
    return this.productService.update(id, product);
  }

  @MircometerTimed
  @Delete("/{id}")
  @Validated
  @Status(HttpStatus.NO_CONTENT)
  public void delete(@NotBlank @PathVariable Long id) {
    this.productService.delete(id);
  }

  @SuppressWarnings("rawtypes")
  @Error
  public HttpResponse<ResponseError> error(HttpRequest request, Throwable e) {
    return ExceptionHandlerHelper.error(request, e);
  }
}
