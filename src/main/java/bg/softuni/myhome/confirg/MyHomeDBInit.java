package bg.softuni.myhome.confirg;

import bg.softuni.myhome.model.entities.*;
import bg.softuni.myhome.model.enums.*;
import bg.softuni.myhome.repository.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;

@Component
public class MyHomeDBInit implements CommandLineRunner {

    private final UserRoleRepository userRoleRepository;
    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;
    private final AgencyRepository agencyRepository;
    private final SearchRepository searchRepository;
    private final RequestRepository requestRepository;
    private final UserRepository userRepository;


    public MyHomeDBInit(UserRoleRepository userRoleRepository,
                        CategoryRepository categoryRepository,
                        LocationRepository locationRepository,
                        AgencyRepository agencyRepository,
                        SearchRepository searchRepository,
                        RequestRepository requestRepository,
                        UserRepository userRepository) {
        this.userRoleRepository = userRoleRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.agencyRepository = agencyRepository;
        this.searchRepository = searchRepository;
        this.requestRepository = requestRepository;
        this.userRepository = userRepository;
    }


    @Override
    public void run(String... args) throws Exception {


        if (userRoleRepository.count() == 0 && categoryRepository.count() == 0 && locationRepository.count() == 0
                && agencyRepository.count() == 0 && searchRepository.count() == 0
                && searchRepository.count() == 0 && searchRepository.count() == 0) {

            initRoles();
            initCategories(List.of("one-bedroom", "two-bedroom", "studio", "maisonette"));
            initLocations(List.of("Sofia", "Varna", "Bourgas", "Plovdiv"));
            initAgencies();
            initSearch();
            initUsers();
        }




    }


    private void initRoles() {

        Arrays.stream(UserRoleEnum.values())
                .forEach(role -> {
                    userRoleRepository.save(new UserRoleEntity().setRole(role));
                });

    }

    private void initCategories(List<String> categories) {
        categories.forEach(c -> categoryRepository.save(new CategoryEntity().setName(c)));
    }

    private void initLocations(List<String> cities) {
        cities.forEach((c -> locationRepository.save(new LocationEntity().setCity(c))));
    }

    private void initAgencies() {
        initAgency("Address", "Sofia, Sv. Nedelia 6",
                "0898433193",
                "https://res.cloudinary.com/dipiksmcm/image/upload/v1679216206/address_agn_v1u58w.png",
                StatusEnum.ACTIVE);
        initAgency("Revolution", "Varna, General Kolev 54",
                "0898433193",
                "https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/revolutionesiz_n4sk3h.jpg",
                StatusEnum.INACTIVE);
        initAgency("Building box", "Sofia, Graf Ignatiev 4",
                "0898433193",
                "https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/buildingbox_xqqfks.jpg",
                StatusEnum.ACTIVE);
        initAgency("Luximmo", "Plovdiv, Tsar Asen 22",
                "0898433193",
                "https://res.cloudinary.com/dipiksmcm/image/upload/v1679425547/luximmo_l7acsg.jpg",
                StatusEnum.ACTIVE);
    }

    private void initAgency(String name, String address, String phoneNumber,
                            String logoUrl, StatusEnum status) {
        agencyRepository.save(new AgencyEntity(
                address, name, phoneNumber, logoUrl, status));
    }


    private void initSearch() {
        LocationEntity cityVarna = locationRepository.findByCity("Varna").get();
        LocationEntity cityPlovdiv = locationRepository.findByCity("Plovdiv").get();
        CategoryEntity twoBedroom = categoryRepository.findByName("two-bedroom").get();
        CategoryEntity oneBedroom = categoryRepository.findByName("one-bedroom").get();

        searchRepository.save(new SearchEntity().setCity(cityVarna)
                .setConstruction(ConstructionEnum.BRICK)
                .setMaxPrice(BigDecimal.valueOf(200000)));

        searchRepository.save(new SearchEntity().setCity(cityPlovdiv)
                .setHeating(HeatingEnum.TPP)
                .setMinArea(BigDecimal.valueOf(100))
                .setCategory(twoBedroom));

        searchRepository.save(new SearchEntity().setType(OfferTypeEnum.BUY)
                .setCity(cityVarna).setCategory(oneBedroom));

    }


    private void initUsers() {

        UserRoleEntity admin = userRoleRepository.findByRole(UserRoleEnum.ADMIN).get();
        UserRoleEntity user = userRoleRepository.findByRole(UserRoleEnum.USER).get();
        UserRoleEntity moderator = userRoleRepository.findByRole(UserRoleEnum.MODERATOR).get();
        SearchEntity search1 = searchRepository.findById(1L).get();
        SearchEntity search2 = searchRepository.findById(2L).get();
        SearchEntity search3 = searchRepository.findById(3L).get();
        AgencyEntity agency = agencyRepository.findById(1L).get();

        userRepository.save(new UserEntity().setNames("Maxim Petrov")
                .setPassword("1234")
                .setEmail("moderator@mail.bg")
                .setUsername("moderator")
                .setRoles(List.of(user, admin, moderator)));

        userRepository.save(new UserEntity().setNames("Kalin Mishev")
                .setPassword("1234")
                .setEmail("admin@mail.bg")
                .setUsername("admin")
                        .setAgency(agency)
                .setRoles(List.of(user, admin)));

        userRepository.save(new UserEntity().setNames("Ivan Ivanov")
                .setPassword("test")
                .setEmail("Ivan@mail.bg")
                .setUsername("ivan")
                .setSearchCriteria(search1)
                .setRoles(List.of(user)));

        userRepository.save(new UserEntity().setNames("Maria Pencheva")
                .setPassword("test")
                .setEmail("maria@mail.bg")
                .setUsername("maria")
                .setSearchCriteria(search2)
                .setRoles(List.of(user)));

        userRepository.save(new UserEntity().setNames("Mladen Belchev")
                .setPassword("test")
                .setEmail("mladen@mail.bg")
                .setUsername("mladen")
                .setSearchCriteria(search2)
                .setRoles(List.of(user)));

    }


}





