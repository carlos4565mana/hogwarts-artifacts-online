package com.carlos.springbootcurse.artifact.converter;

import com.carlos.springbootcurse.artifact.Artifact;
import com.carlos.springbootcurse.artifact.dto.ArtifactDto;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class ArtifactDtoToArtifacConverter implements Converter<ArtifactDto, Artifact> {
    @Override
    public Artifact convert(ArtifactDto source) {
        Artifact artifact = new Artifact();
        artifact.setName(source.name());
        artifact.setDescription(source.description());
        artifact.setImageUrl(source.imageUrl());

        return artifact;
    }
}
