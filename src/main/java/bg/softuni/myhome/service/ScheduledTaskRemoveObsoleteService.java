package bg.softuni.myhome.service;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;


@Component
public class ScheduledTaskRemoveObsoleteService {

    private final SearchService searchService;
    private final LocationService locationService;
    private final UserCodeGenerationService userCodeGenerationService;


    public ScheduledTaskRemoveObsoleteService(SearchService searchService,
                                              LocationService locationService,
                                              UserCodeGenerationService userCodeGenerationService) {

        this.searchService = searchService;

        this.locationService = locationService;

        this.userCodeGenerationService = userCodeGenerationService;
    }


    @Scheduled(cron = "0 0 1 * * *")
    public void removeSearchesWithoutEmail() {
        searchService.deleteAllSearchesWithoutEmail();
    }

    @Scheduled(cron = "0 0 1 * * *")
    public void removeUnusedLocations() {
        locationService.deleteAllUnusedLocations();
    }

    @Scheduled(fixedRate = 180_000)
    public void removeObsoletePasswordChangeCodes() {
        userCodeGenerationService.deletePasswordChangeCodesByTime(Instant.now());
    }

    @Scheduled(fixedRate = 180_000)
    public void removeObsoleteActivationLinkCodes() {
        userCodeGenerationService.deleteActivationLinkCodesByTime(Instant.now());
    }

}
