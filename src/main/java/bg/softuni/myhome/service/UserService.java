package bg.softuni.myhome.service;

import bg.softuni.myhome.commons.CommonService;
import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private CommonService commonService;

    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleService userRoleService, CommonService commonService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.commonService = commonService;
    }

    public boolean passwordsMatch(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {

        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .addRole(userRoleService.findByRole(UserRoleEnum.USER));
        user.setVisibleId(commonService.createVisibleId());
        userRepository.save(user);

    }


    public UserEntity findByUserVisibleId(String userVisibleId){
        return userRepository.findByVisibleId(userVisibleId).orElse(null);
    }

    public UserEntity findById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }
}
