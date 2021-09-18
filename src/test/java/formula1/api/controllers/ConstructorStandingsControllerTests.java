package formula1.api.controllers;

import formula1.api.assembler.ConstructorStandingsModelAssembler;
import formula1.api.entities.ConstructorStandings;
import formula1.api.repositories.ConstructorStandingsRepository;
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
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(ConstructorStandingsController.class)
@Import({ ConstructorStandingsModelAssembler.class })
public class ConstructorStandingsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConstructorStandingsRepository constructorStandingsRepository;

    @Test
    public void testGetAllConstructorResults() throws Exception {
        ConstructorStandings constructorStandings = new ConstructorStandings(11821L, 18L, 1L, 14.0, 1, "1", 1);

        given(constructorStandingsRepository.findAll()).willReturn(
                Collections.singletonList(constructorStandings));

        mockMvc.perform(get("/api/constructor-standings").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].id", is(11821)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].raceId", is(18)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].constructorId", is(1)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].points", is(14.0)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].position", is(1)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].positionText", is("1")))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0].wins", is(1)))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0]._links.self.href", is("http://localhost/api/constructor-standings/1")))
                .andExpect(jsonPath("$._embedded.constructorStandingsList[0]._links.constructors-standings.href", is("http://localhost/api/constructor-standings")))
                .andReturn();
    }

    @Test
    public void testGetConstructorResultsForConstructorThrowsException() throws Exception {
        given(constructorStandingsRepository.findConstructorStandingsForConstructor(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/constructor-standings/constructor/123").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void testGetConstructorResultsForRaceThrowsException() throws Exception {
        given(constructorStandingsRepository.findConstructorStandingsForRace(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/constructor-standings/race/123").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404));
    }
}
