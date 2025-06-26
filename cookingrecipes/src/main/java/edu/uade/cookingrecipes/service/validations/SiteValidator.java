package edu.uade.cookingrecipes.service.validations;

import edu.uade.cookingrecipes.Entity.Site;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class SiteValidator {

    public void validate(Site site, List<Site> sites) {
        for (Site existingSite : sites) {
            if (existingSite.getName().equalsIgnoreCase(site.getName())) {
                throw new IllegalArgumentException("A site with the same name already exists: " + site.getName());
            }
        }
        if (site.getAddress() == null || site.getAddress().isEmpty()) {
            throw new IllegalArgumentException("Address cannot be empty.");
        }
        if (site.getPhoneNumber() == null || site.getPhoneNumber().isEmpty()) {
            throw new IllegalArgumentException("Phone number cannot be empty.");
        }
        if (site.getEmail() == null || site.getEmail().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be empty.");
        }
    }

}
