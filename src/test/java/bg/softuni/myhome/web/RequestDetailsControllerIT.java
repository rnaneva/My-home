package bg.softuni.myhome.web;

import bg.softuni.myhome.model.dto.AgencyRequestDTO;
import bg.softuni.myhome.model.entities.RequestEntity;
import bg.softuni.myhome.model.enums.RequestStatusEnum;
import bg.softuni.myhome.model.view.RequestView;
import bg.softuni.myhome.repository.RequestRepository;
import bg.softuni.myhome.service.RequestService;
import com.cloudinary.Cloudinary;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static bg.softuni.myhome.commons.StaticVariables.ROLE_MODERATOR;
import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class RequestDetailsControllerIT {

    @MockBean
    private RequestService requestService;

    @MockBean
    private RequestRepository requestRepository;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private Cloudinary cloudinary;

    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_getRequestDetails() throws Exception {

        mockFindRequestView();
        mockMvc.perform(get("/agency/requests/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(model().attributeExists("request"))
                .andExpect(view().name("request-details"));
    }



    @Test
    @WithMockUser(username = "username",password = "pass", authorities = {ROLE_MODERATOR})
    void test_postRequestDetails() throws Exception {

        mockFindRequestEntity();
        AgencyRequestDTO agencyRequestDTO = agencyRequestDTO();
        mockMvc.perform(post("/agency/requests/{id}", 1)
                        .flashAttr("agencyRequestDTO", agencyRequestDTO)
                        .with(csrf()))
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/agency/requests/1"));
    }

    private void mockFindRequestEntity() {
        when(requestRepository.findById(1L)).thenReturn(Optional.ofNullable(new RequestEntity().setId(1L)));
    }


    private AgencyRequestDTO agencyRequestDTO(){
        return new AgencyRequestDTO()
                .setNotes("notes")
                .setStatus(RequestStatusEnum.REPLIED);
    }

    private void mockFindRequestView() {
        when(requestService.getRequestViewById(1)).thenReturn(new RequestView());
    }

}
