
package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Long> {

    Optional<SearchEntity> findByVisibleId(String visibleId);
    List<SearchEntity> findByEmailNotNull();
    List<SearchEntity> findByEmailNull();

}
