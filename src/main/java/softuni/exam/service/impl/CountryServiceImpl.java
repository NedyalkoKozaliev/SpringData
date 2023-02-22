package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CountryDto;
import softuni.exam.models.entity.Country;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Optional;


import static softuni.exam.util.Paths.COUNTRY_JSON_PATH;

@Service
public class CountryServiceImpl implements CountryService {
    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public CountryServiceImpl(CountryRepository countryRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.countryRepository = countryRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return countryRepository.count() > 0;
    }

    @Override
    public String readCountriesFileContent() throws IOException {
        return Files.readString(Path.of(COUNTRY_JSON_PATH));
    }

    @Override
    public String importCountries() throws IOException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readCountriesFileContent(), CountryDto[].class))
                .filter(countryDto -> {
                    boolean isValid = validationUtil.isValid(countryDto);

                    sb.append(isValid
                            ? String.format("Successfully imported country %s - %s" , countryDto.getName(),countryDto.getCountryCode())
                            : "Invalid country")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(countryDto -> modelMapper.map(countryDto, Country.class))
                .forEach(countryRepository::save);

        return sb.toString();
    }

    @Override
    public Country findCountryById(Long country) {
        Optional<Country> CountryById = countryRepository.getCountryById(country);
        if (CountryById.isEmpty()) {
            return null;
        }
        return CountryById.get();
    }
}
