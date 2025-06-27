package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.entity.SiteAttendance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SiteAttendanceRepository extends JpaRepository<SiteAttendance, Long> {
    List<SiteAttendance> findByUserIdAndSiteId(Long userId, Long siteId);
}
