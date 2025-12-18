package ir.maktabsharif.test_app.specification;

import ir.maktabsharif.test_app.dto.user.UserSearchFilter;
import ir.maktabsharif.test_app.model.User;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import jakarta.persistence.criteria.Predicate;


public class UserSpecification {

    public static Specification<User> filter(UserSearchFilter filter) {

        return (root, query, cb) -> {

            List<Predicate> predicates = new ArrayList<>();

            if (filter.getFirstName() != null && !filter.getFirstName().isBlank()) {
                predicates.add(
                         cb.like(
                                cb.lower(root.get("firstName")),
                                "%" + filter.getFirstName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getLastName() != null && !filter.getLastName().isBlank()) {
                predicates.add(
                         cb.like(
                                cb.lower(root.get("lastName")),
                                "%" + filter.getLastName().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getEmail() != null && !filter.getEmail().isBlank()) {
                predicates.add(
                         cb.like(
                                cb.lower(root.get("email")),
                                "%" + filter.getEmail().toLowerCase() + "%"
                        )
                );
            }

            if (filter.getRole() != null) {
                predicates.add(
                         cb.equal(root.get("role"), filter.getRole())
                );
            }

            if (filter.getStatus() != null) {
                predicates.add(
                         cb.equal(root.get("status"), filter.getStatus())
                );
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}

