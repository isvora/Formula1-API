package formula1.api.controllers;

import formula1.api.assembler.ConstructorResultsModelAssembler;
import formula1.api.entities.ConstructorResults;
import formula1.api.repositories.ConstructorResultsRepository;
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
@WebMvcTest(ConstructorResultsController.class)
@Import({ ConstructorResultsModelAssembler.class })
public class ConstructorResultsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConstructorResultsRepository constructorResultsRepository;

    @Test
    public void testGetAllConstructorResults() throws Exception {
        ConstructorResults constructorResults = new ConstructorResults(213L, 38L, 11L, 0.0, "\\N");

        given(constructorResultsRepository.findAll()).willReturn(
                Collections.singletonList(constructorResults));

        mockMvc.perform(get("/api/constructor-results").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0].constructorResultsId", is(213)))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0].raceId", is(38)))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0].constructorId", is(11)))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0].points", is(0.0)))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0].status", is("\\N")))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0]._links.self.href", is("http://localhost/api/constructor-results/213")))
                .andExpect(jsonPath("$._embedded.constructorResultsList[0]._links.constructors-results.href", is("http://localhost/api/constructor-results")))
                .andReturn();
    }

    @Test
    public void testGetConstructorResultsForConstructorThrowsException() throws Exception {
        given(constructorResultsRepository.findConstructorsResultsForConstructor(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/constructor-results/constructor/123").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404));
    }

    @Test
    public void testGetConstructorResultsForRaceThrowsException() throws Exception {
        given(constructorResultsRepository.findConstructorResultsForRace(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/constructor-results/race/123").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404));
    }
}
