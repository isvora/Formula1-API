package formula1.api.controllers;

import formula1.api.assembler.CircuitModelAssembler;
import formula1.api.entities.Circuit;
import formula1.api.repositories.CircuitRepository;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.BDDMockito.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@RunWith(SpringRunner.class)
@WebMvcTest(CircuitController.class)
@Import({ CircuitModelAssembler.class })
public class CircuitControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CircuitRepository circuitRepository;

    Circuit melbourneCircuit = new Circuit(1L, "albert_park", "Albert Park Grand Prix Circuit",
            "Melbourne", "Australia", -37.8497, 144.968, 10,
            "http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit");
    Circuit monzaCircuit = new Circuit(14L, "monza", "Autodromo Nazionale di Monza",
            "Monza", "Italy", 45.6156, 9.28111, 162,
            "http://en.wikipedia.org/wiki/Autodromo_Nazionale_Monza");

    @Test
    public void testGetAllCircuits() throws Exception {
        given(circuitRepository.findAll()).willReturn(
                Arrays.asList(melbourneCircuit, monzaCircuit));

        mockMvc.perform(get("/api/circuits").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.circuitList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.circuitList[0].circuitRef", is("albert_park")))
                .andExpect(jsonPath("$._embedded.circuitList[0].name", is("Albert Park Grand Prix Circuit")))
                .andExpect(jsonPath("$._embedded.circuitList[0].location", is("Melbourne")))
                .andExpect(jsonPath("$._embedded.circuitList[0].country", is("Australia")))
                .andExpect(jsonPath("$._embedded.circuitList[0].lat", is(-37.8497)))
                .andExpect(jsonPath("$._embedded.circuitList[0].lng", is(144.968)))
                .andExpect(jsonPath("$._embedded.circuitList[0].alt", is(10)))
                .andExpect(jsonPath("$._embedded.circuitList[0].url", is("http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.self[0].href", is("http://localhost/api/circuits/1")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.self[1].href", is("http://localhost/api/circuits/?ref=albert_park")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.circuits.href", is("http://localhost/api/circuits")))
                .andExpect(jsonPath("$._embedded.circuitList[1].id", is(14)))
                .andExpect(jsonPath("$._embedded.circuitList[1].circuitRef", is("monza")))
                .andExpect(jsonPath("$._embedded.circuitList[1].name", is("Autodromo Nazionale di Monza")))
                .andExpect(jsonPath("$._embedded.circuitList[1].location", is("Monza")))
                .andExpect(jsonPath("$._embedded.circuitList[1].country", is("Italy")))
                .andExpect(jsonPath("$._embedded.circuitList[1].lat", is(45.6156)))
                .andExpect(jsonPath("$._embedded.circuitList[1].lng", is(9.28111)))
                .andExpect(jsonPath("$._embedded.circuitList[1].alt", is(162)))
                .andExpect(jsonPath("$._embedded.circuitList[1].url", is("http://en.wikipedia.org/wiki/Autodromo_Nazionale_Monza")))
                .andExpect(jsonPath("$._embedded.circuitList[1]._links.self[0].href", is("http://localhost/api/circuits/14")))
                .andExpect(jsonPath("$._embedded.circuitList[1]._links.self[1].href", is("http://localhost/api/circuits/?ref=monza")))
                .andExpect(jsonPath("$._embedded.circuitList[1]._links.circuits.href", is("http://localhost/api/circuits")))
                .andReturn();
    }

    @Test
    public void testGetCircuitById() throws Exception {
        given(circuitRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(melbourneCircuit));

        assertRequestIsValid("/api/circuits/1");
    }

    @Test
    public void testGetCircuitByIdThrowsException() throws Exception {
        given(circuitRepository.findById(1L)).willReturn(java.util.Optional.ofNullable(melbourneCircuit));

        assertRequestThrowsExeption("/api/circuits/123", "Circuit not found for id 123");
    }

    @Test
    public void testGetCircuitByRef() throws Exception {
        given(circuitRepository.findCircuitByRef("albert_park")).willReturn(melbourneCircuit);

        assertRequestIsValid("/api/circuits/?ref=albert_park");
    }

    @Test
    public void testGetCircuitsByCountry() throws Exception {
        given(circuitRepository.findCircuitsByCountry("Australia")).willReturn(Collections.singletonList(melbourneCircuit));

        mockMvc.perform(get("/api/circuits/country/Australia").accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("$._embedded.circuitList[0].id", is(1)))
                .andExpect(jsonPath("$._embedded.circuitList[0].circuitRef", is("albert_park")))
                .andExpect(jsonPath("$._embedded.circuitList[0].name", is("Albert Park Grand Prix Circuit")))
                .andExpect(jsonPath("$._embedded.circuitList[0].location", is("Melbourne")))
                .andExpect(jsonPath("$._embedded.circuitList[0].country", is("Australia")))
                .andExpect(jsonPath("$._embedded.circuitList[0].lat", is(-37.8497)))
                .andExpect(jsonPath("$._embedded.circuitList[0].lng", is(144.968)))
                .andExpect(jsonPath("$._embedded.circuitList[0].alt", is(10)))
                .andExpect(jsonPath("$._embedded.circuitList[0].url", is("http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.self[0].href", is("http://localhost/api/circuits/1")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.self[1].href", is("http://localhost/api/circuits/?ref=albert_park")))
                .andExpect(jsonPath("$._embedded.circuitList[0]._links.circuits.href", is("http://localhost/api/circuits")))
                .andReturn();
    }

    @Test
    public void testGetCircuitsByCountryThrowsException() throws Exception {
        given(circuitRepository.findCircuitsByCountry("Australia")).willReturn(Collections.singletonList(melbourneCircuit));

        assertRequestThrowsExeption("/api/circuits/country/Romania", "Circuit not found for country Romania");
    }

    @Test
    public void testGetCircuitByCountryThrowsException() throws Exception {
        given(circuitRepository.findCircuitsByCountry(any()))
                .willReturn(Collections.emptyList());

        assertRequestThrowsExeption("/api/circuits/country/Romania", "Circuit not found for country Romania");
    }

    private void assertRequestThrowsExeption(String path, String msg) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().is(404))
                .andExpect(content().string(msg))
                .andReturn();
    }

    private void assertRequestIsValid(String path) throws Exception {
        mockMvc.perform(get(path).accept(MediaTypes.HAL_JSON_VALUE))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaTypes.HAL_JSON_VALUE))
                .andExpect(jsonPath("id", is(1)))
                .andExpect(jsonPath("circuitRef", is("albert_park")))
                .andExpect(jsonPath("name", is("Albert Park Grand Prix Circuit")))
                .andExpect(jsonPath("location", is("Melbourne")))
                .andExpect(jsonPath("country", is("Australia")))
                .andExpect(jsonPath("lat", is(-37.8497)))
                .andExpect(jsonPath("lng", is(144.968)))
                .andExpect(jsonPath("alt", is(10)))
                .andExpect(jsonPath("url", is("http://en.wikipedia.org/wiki/Melbourne_Grand_Prix_Circuit")))
                .andExpect(jsonPath("_links.self[0].href", is("http://localhost/api/circuits/1")))
                .andExpect(jsonPath("_links.self[1].href", is("http://localhost/api/circuits/?ref=albert_park")))
                .andExpect(jsonPath("_links.circuits.href", is("http://localhost/api/circuits")));
    }
}
