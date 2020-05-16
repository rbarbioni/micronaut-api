package br.com.rbarbioni.repository;

import br.com.rbarbioni.model.Product;
import io.micronaut.configuration.hibernate.jpa.scope.CurrentSession;
import io.micronaut.spring.tx.annotation.Transactional;
import java.util.List;
import java.util.Optional;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.constraints.NotNull;

@SuppressWarnings("unused")
@Singleton
public class ProductRepositoryImpl implements ProductRepository {

  @PersistenceContext private final EntityManager entityManager;

  public ProductRepositoryImpl(@CurrentSession EntityManager entityManager) {
    this.entityManager = entityManager;
  }

  @Override
  @Transactional(readOnly = true)
  public Optional<Product> findById(@NotNull Long id) {
    return Optional.ofNullable(entityManager.find(Product.class, id));
  }

  @Override
  public Optional<Product> findByCode(@NotNull String code) {
    String query = "SELECT p FROM Product p where code=:code";
    return Optional.ofNullable(
        this.entityManager
            .createQuery(query, Product.class)
            .setParameter("code", code)
            .getSingleResult());
  }

  @Override
  public Product save(Product product) {
    entityManager.persist(product);
    return product;
  }

  @Override
  public boolean delete(@NotNull Long id) {
    return findById(id)
        .map(
            entity -> {
              entityManager.remove(entity);
              return Boolean.TRUE;
            })
        .orElse(Boolean.FALSE);
  }

  public List<Product> findAll() {
    String query = "SELECT p FROM Product p";
    return this.entityManager.createQuery(query, Product.class).getResultList();
  }

  @Override
  public Product update(Product product) {
    return this.entityManager.merge(product);
  }
}
