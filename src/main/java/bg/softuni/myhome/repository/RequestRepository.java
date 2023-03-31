package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.RequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {


    List<RequestEntity> findByOffer_Agency_Id(long agencyId);
}
