package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.CityDTO;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public List<CityDTO> getAllCities(){
        return cityRepository.findAll()
                .stream()
                .map(this::toCityDTO)
                .toList();
    }

    public List<String> getAllCityNames(){
        return cityRepository.getAllCityNames();
    }

    private CityDTO toCityDTO(CityEntity city){
        return modelMapper.map(city, CityDTO.class);
    }

    public CityEntity findByName(String name){
        return cityRepository.findByName(name).orElse(null);
    }


}
