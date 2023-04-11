package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.AgencyCreateProfileDTO;
import bg.softuni.myhome.model.dto.AgencyEditProfileDTO;
import bg.softuni.myhome.model.entities.AgencyEntity;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.view.AgencyView;
import bg.softuni.myhome.repository.AgencyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static bg.softuni.myhome.commons.StaticVariables.DEFAULT_LOGO_URL;

@Service
public class AgencyService {

    private final AgencyRepository agencyRepository;
    private final UserService userService;
    private final CloudinaryService cloudinaryService;

    @Autowired
    public AgencyService(AgencyRepository agencyRepository,
                         UserService userService, CloudinaryService cloudinaryService) {
        this.agencyRepository = agencyRepository;
        this.userService = userService;

        this.cloudinaryService = cloudinaryService;
    }


//    can return null
    public AgencyEntity findByName(String name) {
        return agencyRepository.findByName(name).orElse(null);
    }

    public List<String> getAllAgencyNames() {
        return agencyRepository.getAllAgencyNames();
    }

    public AgencyEntity findAgencyByUserId(long id) {
        return agencyRepository.findByUserId(id)
                .orElseThrow(()-> new ObjectNotFoundException("findAgencyByUserId", id));
    }

    public AgencyView getAgencyViewByUserId(long id) {
        return agencyRepository.findByUserId(id)
                .map(this::toAgencyView)
                .orElseThrow(()-> new ObjectNotFoundException("findAgencyByUserId", id));
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
                                            AgencyCreateProfileDTO dto) throws IOException {

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

    public AgencyEntity findAgencyByUserVisibleId(String userVisibleId){
        return agencyRepository.findByUserVisibleId(userVisibleId)
                .orElseThrow(()-> new ObjectNotFoundException("findAgencyByUserVisibleId", userVisibleId));
    }


    public void editAgencyProfile(AgencyEntity agency,
                                  AgencyEditProfileDTO dto) throws IOException {

        agency
                .setAddress(dto.getAddress())
                .setPhoneNumber(dto.getPhoneNumber())
                .setStatus(StatusEnum.ACTIVE)
                .setLogoUrl(setImgUrlEdit(dto, agency));

        agencyRepository.saveAndFlush(agency);
    }



    private String setImgUrlEdit(AgencyEditProfileDTO dto, AgencyEntity agency) throws IOException {

        if (!dto.getLogo().isEmpty()) {
            return cloudinaryService.uploadPicture(dto.getLogo());
        } else if (agency.getLogoUrl().equals(DEFAULT_LOGO_URL)) {
            return DEFAULT_LOGO_URL;
        }

        return agency.getLogoUrl();

    }


    private String setImgUrl(AgencyCreateProfileDTO dto) throws IOException {
        return dto.getLogo() != null ? cloudinaryService.uploadPicture(dto.getLogo()) :
                DEFAULT_LOGO_URL;
    }

    public boolean userHasRegisteredAgency(long userId){
        Optional<AgencyEntity> optUser = agencyRepository.findByUserId(userId);
        return optUser.isPresent();
    }


}
