package bg.softuni.myhome.service;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.view.OfferView;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledTaskService {

    private OfferService offerService;
    private SearchService searchService;
    private EmailService emailService;
    private LocationService locationService;


    public ScheduledTaskService(OfferService offerService, SearchService searchService,
                                EmailService emailService, LocationService locationService
    ) {
        this.offerService = offerService;
        this.searchService = searchService;
        this.emailService = emailService;
        this.locationService = locationService;

    }




    @Scheduled(cron = "0 0 2 * * *")
//    for testing - cron = "*/30 * * * * *"
    public void findRequestsForOffers() {


        List<SearchFormDTO> requestsForOffers =
                searchService.findSearchesWithRequestForOffers();

        if (requestsForOffers.isEmpty()) {
            return;
        }

        for (SearchFormDTO request : requestsForOffers) {

            List<OfferView> offers = offerService.findOffersBySearchForm(request)
                    .stream().filter(offerView ->
                            offerView.getCreatedOn()
                                    .isAfter(request.getReceivedOn()))
                    .toList();

            if (!offers.isEmpty()) {
                String email = request.getEmail();
                String userNames = request.getUserNames() != null ? request.getUserNames() : "";

                emailService.sendEmailWithOffers(email, userNames, request.getVisibleId());

            }


        }
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void removeSearchesWithoutEmail() {
        searchService.deleteAllSearchesWithoutEmail();
    }

    @Scheduled(cron = "0 0 2 * * *")
    public void removeUnusedLocations() {
        locationService.deleteAllUnusedLocations();
    }

}