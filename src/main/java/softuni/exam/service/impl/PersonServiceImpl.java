package softuni.exam.service.impl;

import com.google.gson.Gson;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import softuni.exam.models.dto.PersonDto;
import softuni.exam.models.entity.Country;
import softuni.exam.models.entity.Person;
import softuni.exam.repository.PersonRepository;
import softuni.exam.service.PersonService;
import softuni.exam.util.ValidationUtil;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;


import static softuni.exam.util.Paths.PERSON_JSON_PATH;

@Service
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final Gson gson;

    public PersonServiceImpl(PersonRepository personRepository, ModelMapper modelMapper, ValidationUtil validationUtil, Gson gson) {
        this.personRepository = personRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.gson = gson;
    }

    @Override
    public boolean areImported() {
        return personRepository.count() > 0;
    }

    @Override
    public String readPeopleFromFile() throws IOException {
        return Files.readString(Path.of(PERSON_JSON_PATH));
    }

    @Override
    public String importPeople() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        Arrays.stream(gson
                .fromJson(readPeopleFromFile(), PersonDto[].class))
                .filter(personDto -> {
                    boolean isValid = validationUtil.isValid(personDto);

                    boolean fName = personRepository.findPersonByFirstName(personDto.getFirstName())
                           .isEmpty();
                  if (!fName){
                        isValid = false;
                    }

                    boolean pNumber = personRepository.findPersonByPhone(personDto.getPhone())
                            .isEmpty();
                    if (!pNumber){
                        isValid = false;
                    }

                    boolean pEmail = personRepository.findPersonByEmail(personDto.getEmail())
                            .isEmpty();
                    if (!pEmail){
                        isValid = false;
                    }

                    sb.append(isValid
                            ? String.format("Successfully imported person %s %s" , personDto.getFirstName(),personDto.getLastName())
                            : "Invalid person")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(personDto -> modelMapper.map(personDto, Person.class))
                .forEach(personRepository::save);

        return sb.toString();
    }
}
