package bg.softuni.myhome.service;

import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.AgencyProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static bg.softuni.myhome.commons.StaticVariables.DEFAULT_LOGO_URL;

@Service
public class AgencyService {

    private AgencyRepository agencyRepository;
    private UserService userService;
    private CloudinaryService cloudinaryService;

    @Autowired
    public AgencyService(AgencyRepository agencyRepository, UserService userService, CloudinaryService cloudinaryService) {
        this.agencyRepository = agencyRepository;
        this.userService = userService;

        this.cloudinaryService = cloudinaryService;
    }

    public long getAgencyIdByUserVisibleId(String id){
        return agencyRepository.getAgencyIdByUserVisibleId(id)
                .orElseThrow();
    }

    public AgencyEntity findByName(String name) {
        return agencyRepository.findByName(name).orElse(null);
    }

    public List<String> getAllAgencyNames() {
        return agencyRepository.getAllAgencyNames();
    }

    public AgencyEntity findAgencyByUserId(long id) {
        return agencyRepository.findByUserId(id).orElse(null);
    }

    public AgencyView getAgencyViewByUserId(long id) {
        return agencyRepository.findByUserId(id)
                .map(this::toAgencyView).orElse(null);
    }

    private AgencyView toAgencyView(AgencyEntity agency) {
        return new AgencyView()
                .setId(agency.getId())
                .setName(agency.getName())
                .setAddress(agency.getAddress())
                .setLogoUrl(agency.getLogoUrl())
                .setPhoneNumber(agency.getPhoneNumber());
    }

    public AgencyEntity createAgencyProfile(String userVisibleId,
                                            AgencyProfileDTO dto) throws IOException {

//        todo  max size multipart
        AgencyEntity agency = new AgencyEntity()
                .setName(dto.getName())
                .setAddress(dto.getAddress())
                .setPhoneNumber(dto.getPhoneNumber())
                .setStatus(StatusEnum.ACTIVE)
                .setUser(userService.findByUserVisibleId(userVisibleId))
                .setLogoUrl(setImgUrl(dto));

        agencyRepository.save(agency);

        return agency;
    }

    public AgencyEntity findByUserVisibleId(String userVisibleId){
        return agencyRepository.findByUserVisibleId(userVisibleId)
                .orElse(null);
    }


    public void editAgencyProfile(AgencyEntity agency,
                                  AgencyProfileDTO dto) throws IOException {

//        todo  unique constraint for name does not allow change
        agency.setName(dto.getName())
                .setAddress(dto.getAddress())
                .setPhoneNumber(dto.getPhoneNumber())
                .setStatus(StatusEnum.ACTIVE)
                .setLogoUrl(setImgUrlEdit(dto, agency));

        agencyRepository.saveAndFlush(agency);
    }



    public List<OfferEntity> findOffersByAgencyId(long id){
        return agencyRepository.findOffersByAgencyId(id);

    }

    private String setImgUrlEdit(AgencyProfileDTO dto, AgencyEntity agency) throws IOException {

        if (!dto.getLogo().isEmpty()) {
            return cloudinaryService.uploadPicture(dto.getLogo());
        } else if (agency.getLogoUrl().equals(DEFAULT_LOGO_URL)) {
            return DEFAULT_LOGO_URL;
        }

        return agency.getLogoUrl();

    }


    private String setImgUrl(AgencyProfileDTO dto) throws IOException {
        return dto.getLogo() != null ? cloudinaryService.uploadPicture(dto.getLogo()) :
                DEFAULT_LOGO_URL;
    }

    public boolean userHasRegisteredAgency(long userId){
        Optional<AgencyEntity> optUser = agencyRepository.findByUserId(userId);
        return optUser.isPresent();
    }


}
