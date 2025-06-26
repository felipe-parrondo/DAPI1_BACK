package edu.uade.cookingrecipes.repository;

import edu.uade.cookingrecipes.Entity.Site;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SiteRepository extends JpaRepository<Site, Long> {
}
