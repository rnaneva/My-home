package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.PasswordChangeCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@Repository
public interface PasswordChangeCodeRepository extends JpaRepository<PasswordChangeCode, Long> {

   List<PasswordChangeCode> findByUserEmail(String email);
   List<PasswordChangeCode> findByCreatedBefore(Instant time);

}
