package bg.softuni.myhome.repository;

import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.model.entities.LocationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityRepository extends JpaRepository<CityEntity, Long> {

    Optional<CityEntity> findByName(String name);

    @Query("select c.name from CityEntity  c")
    List<String> getAllCityNames();

}
