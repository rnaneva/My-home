package bg.softuni.myhome.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AgencyEditProfileDTO {

    private String name;

    @NotBlank
    @Size(min = 5, max = 40, message = "Required-between 5 and 40 symbols")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]+$", message = "Please provide a valid phone number")
    private String phoneNumber;

    private MultipartFile logo;

    public String getName() {
        return name;
    }

    public AgencyEditProfileDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AgencyEditProfileDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AgencyEditProfileDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public AgencyEditProfileDTO setLogo(MultipartFile logo) {
        this.logo = logo;
        return this;
    }
}
