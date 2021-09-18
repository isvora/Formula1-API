package formula1.api.controllers;

import formula1.api.assembler.DriverModelAssembler;
import formula1.api.entities.Driver;
import formula1.api.repositories.DriverRepository;
import formula1.api.types.DobOrderEnum;
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

    private final Driver driver = new Driver(24407L, "hamilton", 44, "HAM", "Lewis", "Hamilton",
            new Date(85, Calendar.JANUARY, 7), "British", "http://en.wikipedia.org/wiki/Lewis_Hamilton");

    @Test
    public void testGetAllDrivers() throws Exception {
        given(driverRepository.findAll()).willReturn(
                Collections.singletonList(driver));

        assertListRequestIsValid("/api/drivers");
    }

    @Test
    public void testGetDriverById() throws Exception {
        given(driverRepository.findById(24407L)).willReturn(java.util.Optional.of(driver));

        assertRequestIsValid("/api/drivers/24407");
    }

    @Test
    public void testGetDriverByIdThrowsException() throws Exception {
        given(driverRepository.findById(24407L)).willReturn(java.util.Optional.of(driver));

        assertRequestThrowsExeption("/api/drivers/1234", "Driver not found for id 1234");
    }

    @Test
    public void testGetDriverByRef() throws Exception {
        given(driverRepository.findDriverByRef("hamilton")).willReturn(driver);

        assertRequestIsValid("/api/drivers/?ref=hamilton");
    }

    @Test
    public void testGetDriversSortByDob() throws Exception {
        given(driverRepository.findAllDriversByDob(DobOrderEnum.YTO)).willReturn(
                Collections.singletonList(driver));

        assertListRequestIsValid("/api/drivers/bydob/?sort=YTO");
    }

    @Test
    public void testGetDriversSortByDobThrowsException() throws Exception {
        given(driverRepository.findAllDriversByDob(DobOrderEnum.YTO)).willReturn(
                Collections.singletonList(driver));

        assertRequestThrowsExeption("/api/drivers/bydob/?sort=YOT", "Unknown value: YOT\nSupported values: [YTO, OTY]");
    }

    @Test
    public void testGetDriversByNationality() throws Exception {
        given(driverRepository.findAllDriversByNationality("British")).willReturn(
                Collections.singletonList(driver));

        assertListRequestIsValid("/api/drivers/nationality/British");
    }

    @Test
    public void testGetDriversByNationalityThrowsException() throws Exception {
        given(driverRepository.findAllDriversByNationality("British")).willReturn(
                Collections.singletonList(driver));

        assertRequestThrowsExeption("/api/drivers/nationality/Romanian", "Driver not found for nationality Romanian");
    }

    private void assertRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("driverId", is(24407)))
                .andExpect(jsonPath("driverRef", is("hamilton")))
                .andExpect(jsonPath("number", is(44)))
                .andExpect(jsonPath("code", is("HAM")))
                .andExpect(jsonPath("forename", is("Lewis")))
                .andExpect(jsonPath("surname", is("Hamilton")))
                .andExpect(jsonPath("dob", is("1985-01-06")))
                .andExpect(jsonPath("nationality", is("British")))
                .andExpect(jsonPath("url", is("http://en.wikipedia.org/wiki/Lewis_Hamilton")))
                .andExpect(jsonPath("_links.self[0].href", is("http://localhost/api/drivers/24407")))
                .andExpect(jsonPath("_links.self[1].href", is("http://localhost/api/drivers/?ref=hamilton")))
                .andExpect(jsonPath("_links.drivers.href", is("http://localhost/api/drivers")))
                .andReturn();
    }

    private void assertListRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
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

    private void assertRequestThrowsExeption(String path, String msg) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string(msg))
                .andReturn();
    }
}
