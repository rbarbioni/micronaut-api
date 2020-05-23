package br.com.rbarbioni.model;

import com.fasterxml.jackson.annotation.*;
import io.micronaut.core.annotation.Introspected;
import java.time.LocalDateTime;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

@Introspected
@Entity
@Table(name = "products")
public class Product {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @JsonIgnore
  private Long id;

  @NotBlank
  @NotNull
  @Column(name = "code", nullable = false, unique = true)
  private String code;

  @Column(name = "name", nullable = false)
  @NotBlank
  private String name;

  @Column(name = "price", nullable = false)
  @PositiveOrZero
  private Double price;

  @Column(name = "created_at", nullable = false)
  @JsonIgnore
  private final LocalDateTime createdAt;

  public Product() {
    this.createdAt = LocalDateTime.now();
  }

  public Product(String code, String name, Double price) {
    this();
    this.code = code;
    this.name = name;
    this.price = price;
  }

  @JsonCreator
  public Product(
      @JsonProperty("id") Long id,
      @JsonProperty("code") String code,
      @JsonProperty("name") String name,
      @JsonProperty("price") Double price) {
    this(code, name, price);
    this.id = id;
  }

  public String getCode() {
    return code;
  }

  public String getName() {
    return name;
  }

  public Double getPrice() {
    return price;
  }

  @JsonGetter
  public Long getId() {
    return id;
  }

  @JsonGetter
  @JsonFormat(shape = JsonFormat.Shape.STRING)
  public LocalDateTime getCreatedAt() {
    return createdAt;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Product product = (Product) o;
    return Objects.equals(code, product.code);
  }

  @Override
  public int hashCode() {
    return Objects.hash(code);
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", code='"
        + code
        + '\''
        + ", name='"
        + name
        + '\''
        + ", price="
        + price
        + '\''
        + ", createdAt="
        + createdAt
        + '}';
  }
}
