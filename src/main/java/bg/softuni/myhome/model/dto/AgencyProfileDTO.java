package bg.softuni.myhome.model.dto;


import bg.softuni.myhome.model.validation.UniqueName;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.web.multipart.MultipartFile;

public class AgencyProfileDTO {

    @NotBlank
    @UniqueName
    @Size(min = 3, max = 20, message = "Required-between 3 and 20 symbols")
    private String name;

    @NotBlank
    @Size(min = 5, max = 20, message = "Required-between 5 and 20 symbols")
    private String address;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[+]?[0-9]+$", message = "Please provide a valid phone number")
    private String phoneNumber;

//    todo error handling - one user - one agency

    private MultipartFile logo;

    public String getName() {
        return name;
    }

    public AgencyProfileDTO setName(String name) {
        this.name = name;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public AgencyProfileDTO setAddress(String address) {
        this.address = address;
        return this;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public AgencyProfileDTO setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
        return this;
    }

    public MultipartFile getLogo() {
        return logo;
    }

    public AgencyProfileDTO setLogo(MultipartFile logo) {
        this.logo = logo;
        return this;
    }
}
