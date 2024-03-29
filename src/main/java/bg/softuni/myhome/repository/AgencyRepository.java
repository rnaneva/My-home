package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.view.AgencyView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Repository
public interface AgencyRepository extends JpaRepository<AgencyEntity, Long> {
    Optional<AgencyEntity> findByName(String name);

    @Query("select a.name from AgencyEntity a")
    List<String> getAllAgencyNames();

    Optional<AgencyEntity> findByUserId(long id);

    Optional<AgencyEntity> findByUserVisibleId(String visibleId);

    @Query("select a.offers from AgencyEntity a join fetch OfferEntity o where a.id =:id")
    List<OfferEntity> findOffersByAgencyId(long id);

    @Query("select a.name from AgencyEntity a where a.id =:id")
    String findAgencyNameById(Long id);

    List<AgencyEntity> findByNameContainingIgnoreCase(String name);

}
