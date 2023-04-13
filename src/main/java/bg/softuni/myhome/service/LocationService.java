package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private LocationRepository locationRepository;
    private CityService cityService;

    @Autowired
    public LocationService(LocationRepository locationRepository, CityService cityService) {
        this.locationRepository = locationRepository;
        this.cityService = cityService;
    }

    public LocationEntity saveLocation(String cityName, String address){

        return locationRepository.save(new LocationEntity()
                .setAddress(address)
                .setCity(cityService.findByName(cityName)));

    }

    public List<LocationEntity> getUnusedLocations(){
        return locationRepository.findByOfferPageTwoNull();
    }

    public void deleteAllUnusedLocations(){
        List<LocationEntity> toDelete = getUnusedLocations();
        locationRepository.deleteAll(toDelete);
    }
}