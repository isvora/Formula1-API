package formula1.api.controllers;

import formula1.api.assembler.DriverStandingsModelAssembler;
import formula1.api.assembler.LapTimeModelAssembler;
import formula1.api.entities.DriverStandings;
import formula1.api.entities.LapTime;
import formula1.api.repositories.DriverStandingsRepository;
import formula1.api.repositories.LapTimeRepository;
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
@WebMvcTest(LapTimeController.class)
@Import({ LapTimeModelAssembler.class })
public class LapTimeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LapTimeRepository lapTimeRepository;

    private final LapTime lapTime = new LapTime(
            1L,
            1L,
            1,
            1,
            "1:23.456",
            92605
    );

    @Test
    public void testGetAllLapTimes() throws Exception {
        given(lapTimeRepository.findAll())
                .willReturn(Collections.singletonList(lapTime));

        assertListRequestIsValid("/api/lap-times");
    }

    @Test
    public void testGetLapTimesById() throws Exception {
        given(lapTimeRepository.findById(1L))
                .willReturn(java.util.Optional.of(lapTime));

        assertRequestIsValid("/api/lap-times/1");
    }

    @Test
    public void testGetLapTimesByIdThrowsException() throws Exception {
        given(lapTimeRepository.findById(1L))
                .willReturn(java.util.Optional.of(lapTime));

        assertRequestThrowsExeption("/api/lap-times/123", "Lap time not found for id 123");
    }


    @Test
    public void testGetLapTimesByRace() throws Exception {
        given(lapTimeRepository.findAllLapTimesByRace(1L))
                .willReturn(Collections.singletonList(lapTime));

        assertListRequestIsValid("/api/lap-times/race/1");
    }

    @Test
    public void testGetLapTimesByRaceThrowsException() throws Exception {
        given(lapTimeRepository.findAllLapTimesByRace(1L))
                .willReturn(Collections.singletonList(lapTime));

        assertRequestThrowsExeption("/api/lap-times/race/123", "Lap times not found for raceId 123");
    }

    @Test
    public void testGetLapTimesByDriver() throws Exception {
        given(lapTimeRepository.findAllLapTimesByDriver(1L))
                .willReturn(Collections.singletonList(lapTime));

        assertListRequestIsValid("/api/lap-times/driver/1");
    }

    @Test
    public void testGetLapTimesByDriverThrowsException() throws Exception {
        given(lapTimeRepository.findAllLapTimesByDriver(1L))
                .willReturn(Collections.singletonList(lapTime));

        assertRequestThrowsExeption("/api/lap-times/driver/123", "Lap times not found for driverId 123");
    }

    @Test
    public void testGetLapTimesByDriverAndRace() throws Exception {
        given(lapTimeRepository.findAllLapTimesByADriverInARace(1L, 1L))
                .willReturn(Collections.singletonList(lapTime));

        assertListRequestIsValid("/api/lap-times/driver-race/?driverId=1&raceId=1");
    }

    @Test
    public void testGetLapTimesByDriverAndRaceThrowsException() throws Exception {
        given(lapTimeRepository.findAllLapTimesByADriverInARace(1L, 1L))
                .willReturn(Collections.singletonList(lapTime));

        assertRequestThrowsExeption("/api/lap-times/driver-race/?driverId=123&raceId=123", "Lap times not found for driverId 123 and raceId 123");
    }

    @Test
    public void testGetSpecificLapTimeByDriverAndRace() throws Exception {
        given(lapTimeRepository.findSpecificLapTimeByADriverInARace(1L, 1L, 1))
                .willReturn(Collections.singletonList(lapTime));

        assertListRequestIsValid("/api/lap-times/driver-race-lap/?driverId=1&raceId=1&lap=1");
    }

    @Test
    public void testGetSpecificLapTimeByDriverAndRaceThrowsException() throws Exception {
        given(lapTimeRepository.findSpecificLapTimeByADriverInARace(1L, 1L, 1))
                .willReturn(Collections.singletonList(lapTime));

        assertRequestThrowsExeption("/api/lap-times/driver-race-lap/?driverId=123&raceId=123&lap=123", "Lap time not found for driverId 123 and raceId 123 and lap 123");
    }

    @Test
    public void testGetFastestLapTimeByDriverAndRace() throws Exception {
        given(lapTimeRepository.findFastestLapTimeByADriverInARace(1L, 1L))
                .willReturn(java.util.Optional.of(lapTime));

        assertRequestIsValid("/api/lap-times/driver-race/fastest-lap/?driverId=1&raceId=1&lap=1");
    }

    @Test
    public void testGetFastestLapTimeByDriverAndRaceThrowsException() throws Exception {
        given(lapTimeRepository.findFastestLapTimeByADriverInARace(1L, 1L))
                .willReturn(java.util.Optional.of(lapTime));

        assertRequestThrowsExeption("/api/lap-times/driver-race/fastest-lap/?driverId=123&raceId=123", "Fastest lap time not found for driverId 123 and raceId 123");
    }

    private void assertRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("raceId", is(1)))
                .andExpect(jsonPath("driverId", is(1)))
                .andExpect(jsonPath("lap", is(1)))
                .andExpect(jsonPath("position", is(1)))
                .andExpect(jsonPath("time", is("1:23.456")))
                .andExpect(jsonPath("miliseconds", is(92605)))
                .andExpect(jsonPath("_links.self.href", is("http://localhost/api/lap-times/{id}")))
                .andExpect(jsonPath("_links.lap-times.href", is("http://localhost/api/lap-times")))
                .andReturn();
    }

    private void assertListRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].raceId", is(1)))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].driverId", is(1)))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].lap", is(1)))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].position", is(1)))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].time", is("1:23.456")))
                .andExpect(jsonPath("$._embedded.lapTimeList[0].miliseconds", is(92605)))
                .andExpect(jsonPath("$._embedded.lapTimeList[0]._links.self.href", is("http://localhost/api/lap-times/{id}")))
                .andExpect(jsonPath("$._embedded.lapTimeList[0]._links.lap-times.href", is("http://localhost/api/lap-times")))
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
