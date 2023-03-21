package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.AgencyEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
}
