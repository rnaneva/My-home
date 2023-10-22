package bg.softuni.myhome.commons;

import bg.softuni.myhome.model.enums.*;

import java.util.Arrays;
import java.util.List;

public final class EnumValues {

    public static List<OfferTypeEnum> offerTypeEnums(){
        return Arrays.stream(OfferTypeEnum.values()).toList();
    }

    public static List<ConstructionEnum> constructionEnums() {
        return Arrays.stream(ConstructionEnum.values()).toList();
    }

    public static List<HeatingEnum> heatingEnums() {
        return Arrays.stream(HeatingEnum.values()).toList();
    }

    public static List<AvailableEnum> availableEnums(){
        return Arrays.stream(AvailableEnum.values()).toList();
    }

    public static List<RequestStatusEnum> requestStatusEnums(){
        return Arrays.stream(RequestStatusEnum.values()).toList();
    }

    public static List<UserRoleEnum> userRoleEnums(){
        return Arrays.stream(UserRoleEnum.values()).toList();
    }

    public static List<StatusEnum> statusEnums(){
        return Arrays.stream(StatusEnum.values()).toList();
    }
}
