package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.SearchEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SearchRepository extends JpaRepository<SearchEntity, Long> {

    Optional<SearchEntity> findByVisibleId(String visibleId);

}
