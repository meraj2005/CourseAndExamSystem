package ir.maktabsharif.test_app.repository;


import ir.maktabsharif.test_app.model.User;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface UserRepository extends BaseRepository<Long, User>, JpaSpecificationExecutor<User> {
    Optional<User> findByEmail(String email);
}