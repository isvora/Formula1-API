package formula1.api.controllers;

import formula1.api.assembler.CircuitModelAssembler;
import formula1.api.assembler.DriverModelAssembler;
import formula1.api.entities.Circuit;
import formula1.api.entities.Driver;
import formula1.api.repositories.CircuitRepository;
import formula1.api.repositories.DriverRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(DriverController.class)
@Import({ DriverModelAssembler.class })
public class DriverControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverRepository driverRepository;

    @Test
    public void testGetAllDrivers() throws Exception {
        Driver driver = new Driver(24407L, "hamilton", 44, "HAM", "Lewis", "Hamilton",
                    new Date(85, Calendar.JANUARY, 7), "British", "http://en.wikipedia.org/wiki/Lewis_Hamilton");

        given(driverRepository.findAll()).willReturn(
                Collections.singletonList(driver));

        mockMvc.perform(get("/api/drivers").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.driverList[0].driverId", is(24407)))
                .andExpect(jsonPath("$._embedded.driverList[0].driverRef", is("hamilton")))
                .andExpect(jsonPath("$._embedded.driverList[0].number", is(44)))
                .andExpect(jsonPath("$._embedded.driverList[0].code", is("HAM")))
                .andExpect(jsonPath("$._embedded.driverList[0].forename", is("Lewis")))
                .andExpect(jsonPath("$._embedded.driverList[0].surname", is("Hamilton")))
                .andExpect(jsonPath("$._embedded.driverList[0].dob", is("1985-01-06")))
                .andExpect(jsonPath("$._embedded.driverList[0].nationality", is("British")))
                .andExpect(jsonPath("$._embedded.driverList[0].url", is("http://en.wikipedia.org/wiki/Lewis_Hamilton")))
                .andExpect(jsonPath("$._embedded.driverList[0]._links.self[0].href", is("http://localhost/api/drivers/24407")))
                .andExpect(jsonPath("$._embedded.driverList[0]._links.self[1].href", is("http://localhost/api/drivers/?ref=hamilton")))
                .andExpect(jsonPath("$._embedded.driverList[0]._links.drivers.href", is("http://localhost/api/drivers")))
                .andReturn();
    }
}
