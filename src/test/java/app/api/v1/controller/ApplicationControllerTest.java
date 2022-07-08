package app.api.v1.controller;

import app.api.v1.request.CreateApplicationForm;
import app.api.v1.request.OfferRequest;
import app.api.v1.response.ApplicationResponse;
import app.api.v1.response.CreateApplicationResponse;
import app.api.v1.service.ApplicationService;
import app.api.v1.domain.Offer;
import app.api.v1.domain.UserApplication;
import app.api.v1.domain.Vendor;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(ApplicationController.class)
class ApplicationControllerTest {

    @MockBean
    ApplicationService applicationService;

    @Autowired
    MockMvc mockMvc;

    @Test
    public void testCreateApplication() throws Exception {
        List<UserApplication> userApplicationList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            userApplicationList.add(createUserApplication(i));
        }
        ApplicationResponse applicationResponse = new ApplicationResponse(userApplicationList);
        Mockito.when(applicationService.createApplication(Mockito.any())).thenReturn(applicationResponse);


        mockMvc.perform(post(ApplicationController.CREATE_APPLICATION)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateApplicationForm())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.applications", Matchers.hasSize(2)));

    }

    @Test
    public void testGetOffers() throws Exception {
        List<UserApplication> userApplicationList = new ArrayList<>();
        for (int i = 1; i <= 2; i++) {
            userApplicationList.add(createUserApplication(i));
        }
        Mockito.when(applicationService.getOffers(Mockito.any())).thenReturn(userApplicationList);

        mockMvc.perform(post(ApplicationController.GET_OFFERS)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new OfferRequest())))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", Matchers.hasSize(2)));
    }

    private UserApplication createUserApplication(int mul) {
        final String id = UUID.randomUUID().toString();
        final CreateApplicationResponse.Status status = CreateApplicationResponse.Status.DRAFT;
        final Offer offer = createOffer(mul);
        final Vendor vendor = mul == 1 ? Vendor.FAST_BANK : Vendor.SOLID_BANK;
        return new UserApplication(id, status, vendor, offer);
    }

    private Offer createOffer(int mul) {
        return new Offer(10 * mul, 100 * mul, 10 * mul, mul, "2020-02-01");
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}