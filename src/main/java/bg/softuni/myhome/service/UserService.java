package bg.softuni.myhome.service;

import bg.softuni.myhome.commons.CommonService;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;


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
        user.setVisibleId(commonService.createVisibleId())
                .setUpdateDate(LocalDate.now());
        userRepository.save(user);

    }


    public UserEntity findByUserVisibleId(String userVisibleId){
        return userRepository.findByVisibleId(userVisibleId).orElse(null);
    }

    public UserEntity findById(long id){
        return userRepository.findById(id).orElse(null);
    }

    public UserView getUserViewById (long id){
      return  userRepository.findById(id)
                .map(this::toUserView)
                .orElse(null);
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<UserView> getAllUserViews(Pageable pageable){
       return userRepository.findAll(pageable).stream()
                .map(this::toUserView)
                .toList();

    }

    public void editUser(EditUserDTO editUserDTO){
        Optional<UserEntity> optUser = userRepository.findById(editUserDTO.getId());
        if (optUser.isEmpty()){
            throw new NoSuchElementException();
        }
        UserEntity user = optUser.get()
                .setNames(editUserDTO.getNames())
                .setEmail(editUserDTO.getEmail())
                .setUsername(editUserDTO.getUsername())
                .setUpdateDate(LocalDate.now());
        user.getRoles().get(0).setRole(editUserDTO.getRole());

        userRepository.save(user);

    }

    private UserView toUserView(UserEntity user){
        return new UserView()
                .setEmail(user.getEmail())
                .setId(user.getId())
                .setNames(user.getNames())
                .setUpdateDate(formatDate(user))
                .setRole(getRole(user))
                .setUsername(user.getUsername());
    }

    private static UserRoleEnum getRole(UserEntity user) {
        return user.getRoles().stream().map(UserRoleEntity::getRole).findFirst().get();
    }

    private static String formatDate(UserEntity user) {
        return user.getUpdateDate().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
