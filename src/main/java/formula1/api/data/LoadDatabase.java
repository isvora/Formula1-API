package formula1.api.data;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import formula1.api.entities.*;
import formula1.api.repositories.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


@Configuration
public class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);
    private final Gson gson = new Gson();

    @Bean
    CommandLineRunner initDatabase(CircuitRepository circuitRepository,
                                   ConstructorRepository constructorRepository,
                                   ConstructorResultsRepository constructorResultsRepository,
                                   ConstructorStandingsRepository constructorStandingsRepository,
                                   DriverRepository driverRepository) {
        return args -> {
            log.info("Loading database...");
            loadCircuitData(circuitRepository);
            loadConstructorData(constructorRepository);
            loadConstructorResults(constructorResultsRepository);
            loadConstructorStandings(constructorStandingsRepository);
            loadDriverData(driverRepository);
        };
    }

    private void loadCircuitData(CircuitRepository circuitRepository) {
        String json = parseFile("./resources/json/circuits.json");
        Type circuit = new TypeToken<ArrayList<Circuit>>(){}.getType();

        ArrayList<Circuit> circuits = gson.fromJson(json, circuit);

        if (circuits != null) {
            circuitRepository.saveAll(circuits);
        }
    }

    private void loadConstructorData(ConstructorRepository constructorRepository) {
        String json = parseFile("./resources/json/constructors.json");
        Type constructor = new TypeToken<ArrayList<Constructor>>(){}.getType();

        ArrayList<Constructor> circuits = gson.fromJson(json, constructor);

        if (circuits != null) {
            constructorRepository.saveAll(circuits);
        }
    }

    private void loadConstructorResults(ConstructorResultsRepository constructorResultsRepository) {
        String json = parseFile("./resources/json/constructor_results.json");
        Type constructorResults = new TypeToken<ArrayList<ConstructorResults>>(){}.getType();

        ArrayList<ConstructorResults> constructorResultsList = gson.fromJson(json, constructorResults);

        if (constructorResultsList != null) {
            constructorResultsRepository.saveAll(constructorResultsList);
        }
    }

    private void loadConstructorStandings(ConstructorStandingsRepository constructorStandingsRepository) {
        String json = parseFile("./resources/json/constructor_standings.json");
        Type constructorStandings = new TypeToken<ArrayList<ConstructorStandings>>(){}.getType();

        ArrayList<ConstructorStandings> constructorStandingsList = gson.fromJson(json, constructorStandings);

        if (constructorStandingsList != null) {
            constructorStandingsRepository.saveAll(constructorStandingsList);
        }
    }

    private void loadDriverData(DriverRepository driverRepository) {
        String json = parseFile("./resources/json/drivers.json");
        Type driver = new TypeToken<ArrayList<Driver>>(){}.getType();

        ArrayList<Driver> drivers = gson.fromJson(json, driver);

        if (drivers != null) {
            driverRepository.saveAll(drivers);
        }
    }

    private String parseFile(String path) {
        try {
            return new String (Files.readAllBytes(Paths.get(path)));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
