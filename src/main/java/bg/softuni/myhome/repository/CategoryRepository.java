package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<CategoryEntity, Long> {

    Optional<CategoryEntity> findByName(String name);

    @Query("select cat.name from CategoryEntity cat")
    List<String> getAllCategoryNames();
}
