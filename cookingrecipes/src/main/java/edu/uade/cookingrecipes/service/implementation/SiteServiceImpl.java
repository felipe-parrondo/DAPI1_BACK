package edu.uade.cookingrecipes.service.implementation;

import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.dto.request.SiteRequestDto;
import edu.uade.cookingrecipes.dto.response.SiteResponseDto;
import edu.uade.cookingrecipes.mapper.SiteMapper;
import edu.uade.cookingrecipes.repository.SiteRepository;
import edu.uade.cookingrecipes.service.SiteService;
import edu.uade.cookingrecipes.service.validations.SiteValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
public class SiteServiceImpl implements SiteService {

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private SiteValidator siteValidator;

    @Override
    public List<SiteResponseDto> getAllSites() {
        List<Site> sites = siteRepository.findAll();
        return sites.stream()
                .map(SiteMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public SiteResponseDto getSiteById(Long id) {
        Site site = siteRepository.findById(id).orElse(null);
        if (site == null) {
            throw new NoSuchElementException("Site not found with id: " + id);
        }
        return SiteMapper.toDto(site);
    }

    @Override
    public SiteResponseDto createSite(SiteRequestDto siteDto) {
        Site site = SiteMapper.toEntity(siteDto);
        List<Site> existingSites = siteRepository.findAll();

        siteValidator.validate(site, existingSites);

        Site savedSite = siteRepository.save(site);
        return SiteMapper.toDto(savedSite);
    }

    @Override
    public boolean deleteSite(Long id) {
        if (!siteRepository.existsById(id)) {
            return false;
        }
        siteRepository.deleteById(id);
        return true;
    }
}
