package softuni.exam.service.impl;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import softuni.exam.models.dto.JobDtoWrapper;
import softuni.exam.models.entity.Job;
import softuni.exam.repository.JobRepository;
import softuni.exam.service.CompanyService;
import softuni.exam.service.JobService;
import softuni.exam.util.ValidationUtil;
import softuni.exam.util.XmlParser;

import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static softuni.exam.util.Paths.JOB_XML_PATH;

@Service
public class JobServiceImpl implements JobService {
    private final JobRepository jobRepository;
    private final ModelMapper modelMapper;
    private final ValidationUtil validationUtil;
    private final XmlParser xmlParser;
    private final CompanyService companyService;

    public JobServiceImpl(JobRepository jobRepository, ModelMapper modelMapper, ValidationUtil validationUtil, XmlParser xmlParser, CompanyService companyService) {
        this.jobRepository = jobRepository;
        this.modelMapper = modelMapper;
        this.validationUtil = validationUtil;
        this.xmlParser = xmlParser;
        this.companyService = companyService;
    }

    @Override
    public boolean areImported() {
        return jobRepository.count() > 0;
    }

    @Override
    public String readJobsFileContent() throws IOException {
        return Files.readString(Path.of(JOB_XML_PATH));
    }

    @Override
    public String importJobs() throws IOException, JAXBException {
        StringBuilder sb = new StringBuilder();

        JobDtoWrapper jobImport = xmlParser
                .fromFile(JOB_XML_PATH, JobDtoWrapper.class);

        jobImport.getJobsList()
                .stream()
                .filter(jobDto-> {
                    boolean isValid = validationUtil.isValid(jobDto);



                    sb.append(isValid
                            ? String.format("Successfully imported job %s", jobDto.getTitle())
                            : "Invalid job")
                            .append(System.lineSeparator());

                    return isValid;
                })
                .map(jobDto -> {
                    Job job = modelMapper.map(jobDto, Job.class);
                    job.setCompany(companyService.findCompanyById(jobDto.getCompany()));
                    return job;
                })
                .forEach(jobRepository::save);

        return sb.toString();
    }

    @Override
    public String getBestJobs() {
        StringBuilder sb = new StringBuilder();

        jobRepository
                .findAllJobsOrderBySalary()
                .forEach(job->{
                    sb
                            .append(String.format("Job title %s\n" +
                                            "\t-Salary: %.2f$\n" +
                                            "\t--Hours a week: %dh.\n",job.getTitle(),job.getSalary(),job.getHoursAWeek()));

                });
        return sb.toString().trim();
    }
}
