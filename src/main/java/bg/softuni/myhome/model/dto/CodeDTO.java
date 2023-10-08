package bg.softuni.myhome.model.dto;


import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Length;

public class CodeDTO {

    @NotNull
    private String code;

    public String getCode() {
        return code;
    }

    public CodeDTO setCode(String code) {
        this.code = code;
        return this;
    }
}
