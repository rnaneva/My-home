package bg.softuni.myhome.service;

import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AgencyService {

    private AgencyRepository agencyRepository;

    @Autowired
    public AgencyService(AgencyRepository agencyRepository) {
        this.agencyRepository = agencyRepository;
    }

    public AgencyEntity findByName(String name){
       return agencyRepository.findByName(name).orElse(null);
    }

    public List<String> getAllAgencyNames(){
        return agencyRepository.getAllAgencyNames();
    }
}
