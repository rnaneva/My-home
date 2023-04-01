package bg.softuni.myhome.repository;


import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface RequestRepository extends JpaRepository<RequestEntity, Long> {


//    List<RequestEntity> findByOffer_Agency_Id(long agencyId);

    List<RequestEntity> findByOffer_Agency_User_VisibleIdAndStatus(String userVisibleId, RequestStatusEnum status);


}
