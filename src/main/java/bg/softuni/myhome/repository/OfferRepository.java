package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import bg.softuni.myhome.model.enums.StatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    @Query("select o from OfferEntity o join fetch o.pictures p where o.offerPageOne.type = :type " +
            "and o.status= :status")
    List<OfferEntity> findByOfferPageOneType(OfferTypeEnum type, StatusEnum status);

    //    @Query("select o from OfferEntity o where o.offerPageOne.type = :type and o.offerPageOne.category.name =:category " +
//            "and o.offerPageTwo.location.city.name = :city order by o.offerPageOne.price")
//    List<OfferEntity> findOffersQuickSearch(OfferTypeEnum type, String category, String city);

//    todo Criteria class
    @Query("select off from OfferEntity off " +
            "join OfferPageOne one " +
            "on one.id = off.offerPageOne.id " +
            "join OfferPageTwo two " +
            "on two.id = off.offerPageTwo.id " +
            "join fetch off.pictures p " +
            "where (one.type = ?1 ) and ( one.category.name = ?2) " +
            "and (two.location.city.name = ?3) and (?4 is null or one.construction = ?4) " +
            "and (?5 is null or one.heating= ?5) and (?6 is null or one.price <= ?6) " +
            "and (?7 is null or one.area >= ?7 ) and ( ?8 is null or off.agency.name = ?8) " +
            "order by one.price")
    List<OfferEntity> findOffersBySearchForm(OfferTypeEnum type, String category, String city,
                                               ConstructionEnum construction, HeatingEnum heating,
                                               BigDecimal maxPrice, BigDecimal minArea, String agencyName);



    @Query(value = "select * from `my-home`.offers as o where o.status = 'ACTIVE' order by o.id " +
            "desc limit 4 ",nativeQuery = true)
    List<OfferEntity> findLast4AddedOffers();

    @Query("select o from OfferEntity o join fetch o.pictures p where o.visibleId = :visibleId")
    Optional<OfferEntity> findOfferByVisibleId(String visibleId);

    @Query("select o from OfferEntity o where o.visibleId = :visibleId")
    Optional<OfferEntity> getOfferByVisibleIdWithoutPics(String visibleId);


    List<OfferEntity> findByAgency_User_VisibleIdAndStatus(String visibleId, StatusEnum status);

}
