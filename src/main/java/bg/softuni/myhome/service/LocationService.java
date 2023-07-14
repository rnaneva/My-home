package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.LocationEntity;
import bg.softuni.myhome.repository.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {

    private final LocationRepository locationRepository;
    private final CityService cityService;

    public LocationService(LocationRepository locationRepository, CityService cityService) {
        this.locationRepository = locationRepository;
        this.cityService = cityService;
    }

    public LocationEntity saveLocation(String cityName, String address){

        return locationRepository.save(new LocationEntity()
                .setAddress(address)
                .setCity(cityService.findByName(cityName)));

    }


    public void deleteAllUnusedLocations(){
        List<LocationEntity> toDelete = getUnusedLocations();
        locationRepository.deleteAll(toDelete);
    }

    private List<LocationEntity> getUnusedLocations(){
        return locationRepository.findByOfferPageTwoNull();
    }
}