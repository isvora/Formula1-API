package formula1.api.controllers;

import formula1.api.assembler.ConstructorModelAssembler;
import formula1.api.entities.Constructor;
import formula1.api.repositories.ConstructorRepository;
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
@WebMvcTest(ConstructorController.class)
@Import({ ConstructorModelAssembler.class })
public class ConstructorControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ConstructorRepository constructorRepository;

    @Test
    public void testGetAllConstructors() throws Exception {
        Constructor amonConstructor = new Constructor(78L, "amon", "Amon", "New Zealand", "http://en.wikipedia.org/wiki/Amon_(Formula_One_team)");

        given(constructorRepository.findAll()).willReturn(
                Collections.singletonList(amonConstructor));

        mockMvc.perform(get("/api/constructors").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.constructorList[0].constructorId", is(78)))
                .andExpect(jsonPath("$._embedded.constructorList[0].constructorRef", is("amon")))
                .andExpect(jsonPath("$._embedded.constructorList[0].name", is("Amon")))
                .andExpect(jsonPath("$._embedded.constructorList[0].nationality", is("New Zealand")))
                .andExpect(jsonPath("$._embedded.constructorList[0].url", is("http://en.wikipedia.org/wiki/Amon_(Formula_One_team)")))
                .andExpect(jsonPath("$._embedded.constructorList[0]._links.self[0].href", is("http://localhost/api/constructors/78")))
                .andExpect(jsonPath("$._embedded.constructorList[0]._links.self[1].href", is("http://localhost/api/constructors/?ref=amon")))
                .andExpect(jsonPath("$._embedded.constructorList[0]._links.constructors.href", is("http://localhost/api/constructors")))
                .andReturn();
    }

    @Test
    public void testGetConstructorsByCountryThrowsException() throws Exception {
        given(constructorRepository.findCircuitsByNationality(any()))
                .willReturn(Collections.emptyList());

        mockMvc.perform(get("/api/constructors/nationality/Romanian").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404));
    }
}
