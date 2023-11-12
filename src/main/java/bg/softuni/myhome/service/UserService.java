package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.dto.UserLoginDTO;
import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.model.entities.PasswordChangeCode;
import bg.softuni.myhome.model.entities.UserActivationCode;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.StatusEnum;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.events.PasswordChangedEvent;
import bg.softuni.myhome.model.events.UserRegisteredEvent;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final UserRoleService userRoleService;
    private final ApplicationEventPublisher applicationEventPublisher;
    private final UserCodeGenerationService userCodeGenerationService;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, UserRoleService userRoleService,
                       ApplicationEventPublisher applicationEventPublisher,
                       UserCodeGenerationService userCodeGenerationService) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;
        this.applicationEventPublisher = applicationEventPublisher;
        this.userCodeGenerationService = userCodeGenerationService;
    }


    public void registerUser(UserRegisterDTO userRegisterDTO) {

        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .setStatus(StatusEnum.INACTIVE)
                .addRole(userRoleService.findByRole(UserRoleEnum.USER));
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new UserRegisteredEvent(
                "UserService", user.getEmail(), user.getUsername(), user.getVisibleId()));


    }

    public boolean changePassword(String email, String typedCode, String newPassword) {
        Optional<UserEntity> optUser = userRepository.findByEmail(email);

        PasswordChangeCode savedCode = userCodeGenerationService.findPassCodeByUserEmail(email);

        if (optUser.isEmpty() || savedCode == null || !savedCode.getCode().equals(typedCode)) {
            return false;
        }
        UserEntity user = optUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        applicationEventPublisher.publishEvent(new PasswordChangedEvent("UserService",
                email, typedCode));
        return true;
    }


    public void editUser(EditUserDTO editUserDTO) {
        Optional<UserEntity> optUser = userRepository.findById(editUserDTO.getId());

        if (optUser.isEmpty()) {
            return;
        }
        UserEntity user = optUser.get();
        user.setNames(editUserDTO.getNames())
                .setEmail(editUserDTO.getEmail())
                .setUsername(editUserDTO.getUsername())
                .setCreated(LocalDate.now())
                .setRoles(List.of(getRole(editUserDTO)));

        userRepository.save(user);

    }

    public boolean activateUserProfile(String userId, String code) {
        UserActivationCode codeByUserId = userCodeGenerationService.findActivationCodeByUserId(userId);
        if (code == null) {
            throw new NullPointerException("Code is not present");
        }
        if (codeByUserId.getActivationCode().equals(code)) {
            Optional<UserEntity> optUser = userRepository.findByVisibleId(userId);
            optUser.ifPresent(userEntity -> userEntity.setStatus(StatusEnum.ACTIVE));
            userRepository.save(optUser.get());
            return true;
        }
        return false;
    }

    //    todo deny login
    public boolean userIsNotActive(UserLoginDTO loginDTO) {
        Optional<UserEntity> optUser = userRepository.findByUsername(loginDTO.getUsername());
        return optUser.filter(userEntity -> userEntity.getStatus().equals(StatusEnum.INACTIVE)).isPresent();
    }


    public boolean passwordsMatch(UserRegisterDTO userRegisterDTO) {
        return userRegisterDTO.getPassword().equals(userRegisterDTO.getConfirmPassword());
    }

    public UserEntity findByUserVisibleId(String userVisibleId) {
        return userRepository.findByVisibleId(userVisibleId)
                .orElseThrow(() -> new ObjectNotFoundException("findByUserVisibleId", userVisibleId));
    }


    public UserEntity findById(long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("findById", id));
    }

    public Optional<UserEntity> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    public UserView getUserViewById(long id) {
        return userRepository.findById(id)
                .map(this::toUserView)
                .orElseThrow(() -> new ObjectNotFoundException("findById", id));
    }


    public boolean findByUsernameIfUserExists(String username) {
        return userRepository.findByUsername(username).isPresent();
    }

    public boolean findByEmailIfUserExists(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    public List<UserView> findAllByOrderByLastUpdatedOnDesc() {
        return userRepository.findAllByOrderByIdDesc().stream()
                .map(this::toUserView)
                .toList();

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
        return user.getCreated().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));
    }


}
