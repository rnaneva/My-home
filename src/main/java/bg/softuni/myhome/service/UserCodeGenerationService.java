package bg.softuni.myhome.service;

import bg.softuni.myhome.exception.ObjectNotFoundException;
import bg.softuni.myhome.model.dto.EmailDTO;
import bg.softuni.myhome.model.entities.PasswordChangeCode;
import bg.softuni.myhome.model.entities.UserActivationCode;
import bg.softuni.myhome.model.entities.UserEntity;
import bg.softuni.myhome.model.events.PasswordChangedEvent;
import bg.softuni.myhome.model.events.UserRegisteredEvent;
import bg.softuni.myhome.repository.PasswordChangeCodeRepository;
import bg.softuni.myhome.repository.UserActivationCodeRepository;
import bg.softuni.myhome.repository.UserRepository;
import org.apache.commons.lang.RandomStringUtils;
import org.springframework.context.event.EventListener;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserCodeGenerationService {

    private final EmailService emailService;
    private static final String ACTIVATION_CODE_SYMBOLS = "abcdefghijklmnopqrstuvwxyzABCDEFGJKLMNPRSTUVWXYZ0123456789";
    private static final int ACTIVATION_CODE_LENGTH = 15;
    private final UserRepository userRepository;
    private final UserActivationCodeRepository userActivationCodeRepository;
    private final PasswordChangeCodeRepository passwordChangeCodeRepository;
    private final PasswordEncoder passwordEncoder;

    public UserCodeGenerationService(EmailService emailService, UserRepository userRepository, UserActivationCodeRepository userActivationCodeRepository, PasswordChangeCodeRepository passwordChangeCodeRepository, PasswordEncoder passwordEncoder) {
        this.emailService = emailService;
        this.userRepository = userRepository;
        this.userActivationCodeRepository = userActivationCodeRepository;
        this.passwordChangeCodeRepository = passwordChangeCodeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @EventListener(UserRegisteredEvent.class)
    public void userRegistered(UserRegisteredEvent event) {
        String activationCode = createActivationCode(event.getUserEmail());
        emailService.sendRegistrationEmail(event.getVisibleId(), event.getUserEmail(), event.getUsername(), activationCode);
    }

    private String createActivationCode(String userEmail) {
        String activationCode = generateActivationCode();
        UserActivationCode userActivationCodeEntity = new UserActivationCode()
                .setActivationCode(activationCode)
                .setUser(userRepository.findByEmail(userEmail)
                        .orElseThrow(() -> new ObjectNotFoundException("createActivationCode", "User not found")))
                .setCreated(Instant.now());
        userActivationCodeRepository.save(userActivationCodeEntity);
        return activationCode;

    }

    private String generateActivationCode() {
        StringBuilder activationCode = new StringBuilder();
        Random random = new SecureRandom();

        for (int i = 0; i < ACTIVATION_CODE_LENGTH; i++) {
            int randInd = random.nextInt(ACTIVATION_CODE_SYMBOLS.length());
            activationCode.append(ACTIVATION_CODE_SYMBOLS.charAt(randInd));
        }

        return activationCode.toString().trim();
    }

    public UserActivationCode findActivationCodeByUserId(String userId) {
        List<UserActivationCode> codes = userActivationCodeRepository.findByUserVisibleId(userId);
        if (codes.isEmpty()) {
            return null;
        }
        return codes.get(codes.size() - 1);
    }

    private void setOneTimePassToUser(UserEntity user, String code) {
        PasswordChangeCode passwordChangeCode = new PasswordChangeCode()
                .setCode(code)
                .setUser(user)
                .setCreated(Instant.now());
        passwordChangeCodeRepository.save(passwordChangeCode);
    }


    public void sendEmailToChangeUserPass(EmailDTO emailDTO) {

        Optional<UserEntity> optUser = userRepository.findByEmail(emailDTO.getEmail());

        if (optUser.isEmpty()) {
            return;
        }
        UserEntity user = optUser.get();
        String code = RandomStringUtils.randomNumeric(6);
        setOneTimePassToUser(user, code);
        emailService.sendEmailForPasswordReset(user.getUsername(), user.getEmail(), code);
    }

    @EventListener(PasswordChangedEvent.class)
    public void passwordChanged(PasswordChangedEvent event) {
        List<PasswordChangeCode> codes = passwordChangeCodeRepository.findByUserEmail(event.getUserEmail());
        if (codes.isEmpty()) {
            return;
        }
        PasswordChangeCode code = codes.get(codes.size() - 1);
        passwordChangeCodeRepository.delete(code);
    }


    public PasswordChangeCode findPassCodeByUserEmail(String userEmail) {
        List<PasswordChangeCode> codes = passwordChangeCodeRepository.findByUserEmail(userEmail);
        if (codes.isEmpty()) {
            return null;
        }
        return codes.get(codes.size() - 1);
    }

    public void deletePasswordChangeCodesByTime(Instant time) {
        List<PasswordChangeCode> codes = passwordChangeCodeRepository.findByCreatedBefore(time);
        passwordChangeCodeRepository.deleteAll(codes);
    }

    public void deleteActivationLinkCodesByTime(Instant time) {
        List<UserActivationCode> codes = userActivationCodeRepository.findByCreatedBefore(time);
        userActivationCodeRepository.deleteAll(codes);
    }


}
