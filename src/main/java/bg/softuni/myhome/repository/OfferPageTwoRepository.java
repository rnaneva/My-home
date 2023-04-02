package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferPageTwo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OfferPageTwoRepository extends JpaRepository<OfferPageTwo, Long> {
}
