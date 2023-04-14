package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper, PasswordEncoder passwordEncoder, UserRoleService userRoleService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;

    }

    public boolean passwordsMatch(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
    }

    public void registerUser(UserRegisterDTO userRegisterDTO) {

        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .addRole(userRoleService.findByRole(UserRoleEnum.USER));
        userRepository.save(user);
    }


    public UserEntity findByUserVisibleId(String userVisibleId) {
        return userRepository.findByVisibleId(userVisibleId)
                .orElseThrow(() -> new ObjectNotFoundException("findByUserVisibleId", userVisibleId));
    }


    public UserEntity findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("findById", id));
    }


    public UserView getUserViewById(long id) {
        return userRepository.findById(id)
                .map(this::toUserView)
                .orElseThrow(() -> new ObjectNotFoundException("findById", id));
    }

    //    can be null
    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username).orElse(null);
    }

    //    can be null
    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);
    }

    public List<UserView> findAllByOrderByUpdateDateDesc() {
        return userRepository.findAllByOrderByLastUpdatedOnDesc().stream()
                .map(this::toUserView)
                .toList();

    }

    public void editUser(EditUserDTO editUserDTO) {
        UserEntity user = userRepository.findById(editUserDTO.getId()).get();

        user
                .setNames(editUserDTO.getNames())
                .setEmail(editUserDTO.getEmail())
                .setUsername(editUserDTO.getUsername())
                .setLastUpdatedOn(LocalDate.now());
        user.setRoles(List.of(getRole(editUserDTO)));

        userRepository.save(user);

    }

    private UserRoleEntity getRole(EditUserDTO editUserDTO) {
        return userRoleService.findByRole(editUserDTO.getRole());
    }

    private UserView toUserView(UserEntity user) {
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
        return user.getLastUpdatedOn().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }
}
