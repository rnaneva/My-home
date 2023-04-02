package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferPageOne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferPageOneRepository extends JpaRepository<OfferPageOne, Long> {
}
