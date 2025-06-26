package edu.uade.cookingrecipes.service;

import edu.uade.cookingrecipes.dto.Request.SiteRequestDto;
import edu.uade.cookingrecipes.dto.Response.SiteResponseDto;

import java.util.List;

public interface SiteService {
    List<SiteResponseDto> getAllSites();
    SiteResponseDto getSiteById(Long id);
    SiteResponseDto createSite(SiteRequestDto siteDto);
    boolean deleteSite(Long id);
}
