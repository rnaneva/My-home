package bg.softuni.myhome.service;

import bg.softuni.myhome.model.view.CityView;
import bg.softuni.myhome.model.entities.CityEntity;
import bg.softuni.myhome.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CityService {

    private CityRepository cityRepository;
    private ModelMapper modelMapper;

    @Autowired
    public CityService(CityRepository cityRepository, ModelMapper modelMapper) {
        this.cityRepository = cityRepository;
        this.modelMapper = modelMapper;
    }

    public List<CityView> getAllCities(){
        return cityRepository.findAll()
                .stream()
                .map(this::toCityDTO)
                .toList();
    }

    public List<String> getAllCityNames(){
        return cityRepository.getAllCityNames();
    }

    private CityView toCityDTO(CityEntity city){
        return modelMapper.map(city, CityView.class);
    }

    public CityEntity findByName(String name){
        return cityRepository.findByName(name).orElse(null);
    }


}
