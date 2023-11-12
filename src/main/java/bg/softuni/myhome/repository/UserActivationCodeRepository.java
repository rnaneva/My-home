package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.UserActivationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserActivationCodeRepository extends JpaRepository<UserActivationCode, Long> {

List<UserActivationCode> findByUserVisibleId(String visibleId);

    List<UserActivationCode> findByCreatedBefore(Instant time);

}
