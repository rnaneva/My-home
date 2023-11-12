package bg.softuni.myhome.service.schedulers;

import bg.softuni.myhome.model.dto.SearchFormDTO;
import bg.softuni.myhome.model.view.OfferView;
import bg.softuni.myhome.service.EmailService;
import bg.softuni.myhome.service.OfferService;
import bg.softuni.myhome.service.SearchService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ScheduledEmailTaskService {

    private final EmailService emailService;
    private final OfferService offerService;
    private final SearchService searchService;

    public ScheduledEmailTaskService(EmailService emailService, OfferService offerService, SearchService searchService) {
        this.emailService = emailService;
        this.offerService = offerService;
        this.searchService = searchService;
    }


    @Scheduled(cron = "0 0 1 * * *")
//    for testing - cron = "*/30 * * * * *"
    public void sendEmailForRequestsWithNewOffers() {


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

}
