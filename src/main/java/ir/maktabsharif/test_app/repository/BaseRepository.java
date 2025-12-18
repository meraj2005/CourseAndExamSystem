package ir.maktabsharif.test_app.repository;

import ir.maktabsharif.test_app.model.BaseModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface BaseRepository<ID, T extends BaseModel<ID>>extends JpaRepository<T, ID> {
}
