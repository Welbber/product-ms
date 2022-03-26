package uol.compass.desafiojavaspringboot.productms.repository.specification;


import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;
import uol.compass.desafiojavaspringboot.productms.entity.Product;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.util.ArrayList;

public class ProductSpecification {
    public static Specification<Product> createSpecification(final String q, final Double minPrice, final Double maxPrice) {
        return (Root<Product> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) -> {
            final var predicates = new ArrayList<Predicate>();
            if (StringUtils.hasLength(q))
                predicates.add(criteriaBuilder.or(
                        criteriaBuilder.like(root.get("name"), '%' + q + '%'),
                        criteriaBuilder.like(root.get("description"), '%' + q + '%')
                ));
            if (minPrice != null)
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), minPrice));
            if (maxPrice != null)
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), minPrice));

            return criteriaBuilder.and(predicates.toArray(new Predicate[]{}));
        };
    }
}
