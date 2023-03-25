package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.ConstructionEnum;
import bg.softuni.myhome.model.enums.HeatingEnum;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface OfferRepository extends JpaRepository<OfferEntity, Long> {

    List<OfferEntity> findByOfferPage1Type(OfferTypeEnum type);

    @Query("select o from OfferEntity o where o.offerPage1.type = :type and o.offerPage1.category.name =:category " +
            "and o.offerPage2.location.city.name = :city order by o.offerPage1.price")
    List<OfferEntity> findOffersQuickSearch(OfferTypeEnum type, String category, String city);

//    @Query("select o from OfferEntity o where o.offerPage1.type = :type and o.offerPage1.category.name =:category " +
//            "and o.offerPage2.location.city.name = :city and o.offerPage1.construction =:construction" +
//            " and o.offerPage1.heating=: heating and o.offerPage1.price <=: maxPrice and o.offerPage1.area" +
//            " >=: minArea and o.agency.name =: agencyName order by o.offerPage1.price")
//    List<OfferEntity> findOffersAdvancedSearch(OfferTypeEnum type, String category, String city,
//                                               ConstructionEnum construction, HeatingEnum heating,
//                                               BigDecimal maxPrice, BigDecimal minArea, String agencyName);



}
