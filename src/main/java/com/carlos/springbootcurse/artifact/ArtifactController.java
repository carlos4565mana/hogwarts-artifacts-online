package com.carlos.springbootcurse.artifact;

import com.carlos.springbootcurse.artifact.converter.ArtifactToArtifactDtoConverter;
import com.carlos.springbootcurse.artifact.dto.ArtifactDto;
import com.carlos.springbootcurse.system.Result;
import com.carlos.springbootcurse.system.StatusCode;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ArtifactController {
    private final ArtifactService artifactService;
    private  final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
    }

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
       Artifact foundArtifact =  this.artifactService.findById((artifactId));
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDto);
    }
}










