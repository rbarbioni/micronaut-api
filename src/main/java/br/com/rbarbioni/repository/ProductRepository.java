package br.com.rbarbioni.repository;

import br.com.rbarbioni.model.Product;
import java.util.List;
import java.util.Optional;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
public interface ProductRepository {

  Optional<Product> findById(@NotNull Long id);

  Optional<Product> findByCode(@NotNull String code);

  Product save(Product product);

  boolean delete(@NotNull Long id);

  List<Product> findAll();

  Product update(Product product);
}
