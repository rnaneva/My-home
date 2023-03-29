package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
    Optional<AgencyEntity> findByName(String name);

    @Query("select a.name from AgencyEntity a")
    List<String> getAllAgencyNames();
}
