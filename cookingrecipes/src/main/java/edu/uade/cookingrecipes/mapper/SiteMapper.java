package edu.uade.cookingrecipes.mapper;

import edu.uade.cookingrecipes.entity.Site;
import edu.uade.cookingrecipes.dto.request.SiteRequestDto;
import edu.uade.cookingrecipes.dto.response.SiteResponseDto;

public class SiteMapper {
    public static SiteResponseDto toDto(Site site){
        if (site == null) return null;

        return new SiteResponseDto(
                site.getId(),
                site.getName(),
                site.getAddress(),
                site.getPhoneNumber(),
                site.getEmail(),
                site.getPhotoUrl(),
                site.getLocation()
        );
    }

    public static Site toEntity(SiteRequestDto dto) {
        if (dto == null) return null;

        Site site = new Site();
        site.setName(dto.getName());
        site.setAddress(dto.getAddress());
        site.setPhoneNumber(dto.getPhoneNumber());
        site.setEmail(dto.getEmail());
        site.setPhotoUrl(dto.getPhotoUrl());
        site.setLocation(dto.getLocation());
        return site;
    }
}
