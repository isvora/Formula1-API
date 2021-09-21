package formula1.api.controllers;

import formula1.api.assembler.DriverStandingsModelAssembler;
import formula1.api.entities.DriverStandings;
import formula1.api.repositories.DriverStandingsRepository;
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

import java.util.Collections;

import static org.hamcrest.CoreMatchers.is;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(DriverStandingsController.class)
@Import({ DriverStandingsModelAssembler.class })
public class DriverStandingsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DriverStandingsRepository driverStandingsRepository;

    private final DriverStandings driverStandings = new DriverStandings(
            33007L,
            1030L,
            1L,
            413.0,
            1,
            "1",
            11);

    @Test
    public void testGetAllDriverStandings() throws Exception {
        given(driverStandingsRepository.findAll())
                .willReturn(Collections.singletonList(driverStandings));

        assertListRequestIsValid("/api/driver-standings");
    }

    @Test
    public void testGetDriverStandingsById() throws Exception {
        given(driverStandingsRepository.findById(33007L))
                .willReturn(java.util.Optional.of(driverStandings));

        assertRequestIsValid("/api/driver-standings/33007");
    }

    @Test
    public void testGetDriverStandingsByIdThrowsException() throws Exception {
        given(driverStandingsRepository.findById(33007L))
                .willReturn(java.util.Optional.of(driverStandings));

        assertRequestThrowsExeption("/api/driver-standings/12345", "Driver standings not found for driverStandingsId 12345");
    }


    @Test
    public void testGetDriverStandingsByRace() throws Exception {
        given(driverStandingsRepository.findDriverStandingsByRace(1030L))
                .willReturn(Collections.singletonList(driverStandings));

        assertListRequestIsValid("/api/driver-standings/race/1030");
    }

    @Test
    public void testGetDriverStandingsByRaceThrowsException() throws Exception {
        given(driverStandingsRepository.findDriverStandingsByRace(1030L))
                .willReturn(Collections.singletonList(driverStandings));

        assertRequestThrowsExeption("/api/driver-standings/race/12345", "Driver standings not found for raceId 12345");
    }

    @Test
    public void testGetDriverStandingsByDriver() throws Exception {
        given(driverStandingsRepository.findDriverStandingsByDriver(1L))
                .willReturn(Collections.singletonList(driverStandings));

        assertListRequestIsValid("/api/driver-standings/driver/1");
    }

    @Test
    public void testGetDriverStandingsByDriverThrowsException() throws Exception {
        given(driverStandingsRepository.findDriverStandingsByDriver(1L))
                .willReturn(Collections.singletonList(driverStandings));

        assertRequestThrowsExeption("/api/driver-standings/driver/12345", "Driver standings not found for driverId 12345");
    }

    private void assertRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("driverStandingsId", is(33007)))
                .andExpect(jsonPath("raceId", is(1030)))
                .andExpect(jsonPath("driverId", is(1)))
                .andExpect(jsonPath("points", is(413.0)))
                .andExpect(jsonPath("position", is(1)))
                .andExpect(jsonPath("positionText", is("1")))
                .andExpect(jsonPath("wins", is(11)))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/driver-standings/33007")))
                .andExpect(jsonPath("_links.driver-standings.href", is("http://localhost/api/driver-standings")))
                .andReturn();
    }

    private void assertListRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].driverStandingsId", is(33007)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].raceId", is(1030)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].driverId", is(1)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].points", is(413.0)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].position", is(1)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].positionText", is("1")))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0].wins", is(11)))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0]._links.self.href", is("http://localhost/api/driver-standings/33007")))
                .andExpect(jsonPath("$._embedded.driverStandingsList[0]._links.driver-standings.href", is("http://localhost/api/driver-standings")))
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
