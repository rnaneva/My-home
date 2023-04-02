package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.repository.LocationRepository;
import org.springframework.stereotype.Service;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    private CityService cityService;

    public LocationService(LocationRepository locationRepository, CityService cityService) {
        this.locationRepository = locationRepository;
        this.cityService = cityService;
    }

    public LocationEntity saveLocation(String cityName, String address){

        return locationRepository.save(new LocationEntity()
                .setAddress(address)
                .setCity(cityService.findByName(cityName)));

    }
}
