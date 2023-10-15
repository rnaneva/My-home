package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.AppUserDetails;
import bg.softuni.myhome.model.dto.EditUserDTO;
import bg.softuni.myhome.model.dto.EmailDTO;
import bg.softuni.myhome.model.dto.UserRegisterDTO;
import bg.softuni.myhome.model.entities.OfferEntity;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.entities.UserRoleEntity;
import bg.softuni.myhome.model.enums.UserRoleEnum;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.model.view.UserView;
import bg.softuni.myhome.repository.OfferRepository;
import bg.softuni.myhome.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.lang.RandomStringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
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
    private final EmailService emailService;
    private final OfferRepository offerRepository;


    @Autowired
    public UserService(UserRepository userRepository, ModelMapper modelMapper,
                       PasswordEncoder passwordEncoder, UserRoleService userRoleService, EmailService emailService,
                       OfferRepository offerRepository) {
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.passwordEncoder = passwordEncoder;
        this.userRoleService = userRoleService;

        this.emailService = emailService;
        this.offerRepository = offerRepository;
    }




    public void registerUser(UserRegisterDTO userRegisterDTO) {

        UserEntity user = modelMapper.map(userRegisterDTO, UserEntity.class);
        user.setPassword(passwordEncoder.encode(userRegisterDTO.getPassword()))
                .addRole(userRoleService.findByRole(UserRoleEnum.USER));
        userRepository.save(user);
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
                .setLastUpdatedOn(LocalDate.now())
                .setRoles(List.of(getRole(editUserDTO)));

        userRepository.save(user);

    }


    public Optional<UserEntity> findByEmailAndOneTimePass(String email, String oneTimePass){
        return userRepository.findByEmailAndOneTimePass(email, oneTimePass);
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

    public Optional<UserEntity> findByEmail(String email){
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



    public void sendEmailToChangeUserPass(EmailDTO emailDTO) {

        Optional<UserEntity> optUser = findByEmail(emailDTO.getEmail());

        if(optUser.isEmpty()){
            return;
        }
        UserEntity user = optUser.get();
        String code = RandomStringUtils.randomNumeric(6);
        setOneTimePassToUser(user, code);
        emailService.sendEmailForPasswordReset(user.getUsername(), user.getEmail(), user.getOneTimePass());
    }

    public boolean changePassword(String email, String code, String newPassword) {
        Optional<UserEntity> optUser = findByEmailAndOneTimePass(email, code);

        if(optUser.isEmpty()){
            return false;
        }
        UserEntity user = optUser.get();
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setOneTimePass(null);
        userRepository.save(user);
        return true;
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


    private void setOneTimePassToUser(UserEntity user, String code) {
        user.setOneTimePass(code);
        userRepository.save(user);
    }


}
