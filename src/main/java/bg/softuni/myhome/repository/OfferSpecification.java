package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.OfferTypeEnum;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.jpa.domain.Specification;

public class OfferSpecification implements Specification<OfferEntity> {

    private final SearchFormDTO searchFormDTO;

    public OfferSpecification(SearchFormDTO searchFormDTO) {
        this.searchFormDTO = searchFormDTO;
    }
//    @Query("select off from OfferEntity off " +
//            "join OfferPageOne one " +
//            "on one.id = off.offerPageOne.id " +
//            "join OfferPageTwo two " +
//            "on two.id = off.offerPageTwo.id " +
//            "join fetch off.pictures p " +
//            "where (one.type = ?1 ) and ( one.category.name = ?2) " +
//            "and (two.location.city.name = ?3) and (?4 is null or one.construction = ?4) " +
//            "and (?5 is null or one.heating= ?5) and (?6 is null or one.price <= ?6) " +
//            "and (?7 is null or one.area >= ?7 ) and ( ?8 is null or off.agency.name = ?8) " +
//            "order by one.price")
//    List<OfferEntity> findOffersBySearchForm(String category, String city,
//                                             ConstructionEnum construction, HeatingEnum heating,
//                                             BigDecimal maxPrice, BigDecimal minArea, String agencyName);

    @Override
    public Predicate toPredicate(Root<OfferEntity> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
      Specification<OfferEntity> where = Specification.where(null);


        if (searchFormDTO.getType() != null) {
            where = where.and(typeNameEqualsTo(searchFormDTO.getType()));
        }


        return where.toPredicate(root, query, criteriaBuilder);
    }

    private static Specification<OfferEntity> typeNameEqualsTo(OfferTypeEnum type) {
        return (r,q,b)->  b.and(b.equal(r.join("type").get("name"), type));
    }

    private static Specification<OfferEntity> categoryNameEqualsTo(String category) {
        return (r,q,b)->  b.and(b.equal(r.join("category").get("name"), category));
    }

}
