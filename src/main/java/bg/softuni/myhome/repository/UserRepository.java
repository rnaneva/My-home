package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByVisibleId(String userVisibleId);

    Optional<UserEntity> findByOneTimePass(String oneTimePass);

    Optional<UserEntity> findByEmailAndOneTimePass(String email, String oneTimePass);

    List<UserEntity> findAllByOrderByIdDesc();


}
