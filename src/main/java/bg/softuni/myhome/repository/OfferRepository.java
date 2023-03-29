package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;


@Repository
public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    @Query("select o from OfferEntity o join fetch o.pictures p where o.offerPage1.type = :type")
    List<OfferEntity> findByOfferPage1Type(OfferTypeEnum type);

    //    @Query("select o from OfferEntity o where o.offerPage1.type = :type and o.offerPage1.category.name =:category " +
//            "and o.offerPage2.location.city.name = :city order by o.offerPage1.price")
//    List<OfferEntity> findOffersQuickSearch(OfferTypeEnum type, String category, String city);

    @Query("select off from OfferEntity off " +
            "join OfferPage1 one " +
            "on one.id = off.offerPage1.id " +
            "join OfferPage2 two " +
            "on two.id = off.offerPage2.id " +
            "join fetch off.pictures p " +
            "where (one.type = ?1 ) and ( one.category.name = ?2) " +
            "and (two.location.city.name = ?3) and (?4 is null or one.construction = ?4) " +
            "and (?5 is null or one.heating= ?5) and (?6 is null or one.price <= ?6) " +
            "and (?7 is null or one.area >= ?7 ) and ( ?8 is null or off.agency.name = ?8 ) " +
            "order by one.price")
    List<OfferEntity> findOffersBySearchForm(OfferTypeEnum type, String category, String city,
                                               ConstructionEnum construction, HeatingEnum heating,
                                               BigDecimal maxPrice, BigDecimal minArea, String agencyName);


    @Query(value = "select * from `my-home`.offers as o order by o.created_on desc limit 4 ",nativeQuery = true)
    List<OfferEntity> findLast4AddedOffers();

    @Query("select o from OfferEntity o join fetch o.pictures p where o.visibleId = :visibleId")
    Optional<OfferEntity> findByVisibleId(String visibleId);

}
