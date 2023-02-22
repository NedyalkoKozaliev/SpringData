package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.CompanyDtoWrapper;
import softuni.exam.models.entity.Company;

import softuni.exam.models.entity.Country;
import softuni.exam.repository.CompanyRepository;
import softuni.exam.repository.CountryRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.CountryService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Optional;

import static softuni.exam.util.Paths.COMPANY_XML_PATH;


@Service
public class CompanyServiceImpl implements CompanyService {
    private final CompanyRepository companyRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CountryService countryService;
    private final CountryRepository countryRepository;

    @Autowired
    public CompanyServiceImpl(CompanyRepository companyRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CountryService countryService, CountryRepository countryRepository) {
        this.companyRepository = companyRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.countryService = countryService;
        this.countryRepository = countryRepository;
    }

    @Override
    public boolean areImported() {
        return companyRepository.count() > 0;
    }

    @Override
    public String readCompaniesFromFile() throws IOException {
        return Files.readString(Path.of(COMPANY_XML_PATH));
    }

    @Override
    public String importCompanies() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        CompanyDtoWrapper companyImport = xmlParser
                .fromFile(COMPANY_XML_PATH, CompanyDtoWrapper.class);

        companyImport.getCompaniesList()
                .stream()
                .filter(companyDto-> {
                    boolean isValid = validationUtil.isValid(companyDto);

                   boolean doesntExist = companyRepository.findCompanyByName(companyDto.getName())
                          .isEmpty();
                    if (!doesntExist){
                       isValid = false;
                    }

                    sb.append(isValid
                            ? String.format("Successfully imported company %s - %s ", companyDto.getName(),countryRepository.getCountryById(companyDto.getCountry()).get().getName())
                            : "Invalid company")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(companyDto -> {
                    Company company = modelMapper.map(companyDto, Company.class);
                    company.setCountry(countryService.findCountryById(companyDto.getCountry()));
                    return company;
                })
                .forEach(companyRepository::save);

        return sb.toString();
    }

    @Override
    public Company findCompanyById(Long company) {
        Optional<Company> CompanyById = companyRepository.getCompanyById(company);
        if (CompanyById.isEmpty()) {
            return null;
        }
        return CompanyById.get();
    }
}
