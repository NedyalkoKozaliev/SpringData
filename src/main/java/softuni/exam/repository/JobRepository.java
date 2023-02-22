package softuni.exam.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import softuni.exam.models.entity.Job;

import java.util.List;

// TODO:
@Repository
public interface JobRepository extends JpaRepository<Job, Long> {

    @Query("SELECT j FROM Job j WHERE j.salary >= 5000 And j.hoursAWeek <= 30"+" ORDER BY j.salary DESC")
    List<Job> findAllJobsOrderBySalary();



}
