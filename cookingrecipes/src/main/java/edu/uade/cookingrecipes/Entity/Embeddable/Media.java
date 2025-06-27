package edu.uade.cookingrecipes.Entity.Embeddable;

import java.io.Serializable;

public record Media (
        Long id,
        String url,
        String mediaType
) implements Serializable {}
