package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {
}
