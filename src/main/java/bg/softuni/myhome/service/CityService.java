package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.repository.CityRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private final CityRepository cityRepository;



    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }



    public List<String> getAllCityNames(){
        return cityRepository.getAllCityNames();
    }


//can be null
    public CityEntity findByName(String name){
        return cityRepository.findByName(name).orElse(null);
    }


    public void saveCity(CityDTO cityDTO) {
        cityRepository.save(
                new CityEntity()
                        .setName(cityDTO.getName()));
    }
}
