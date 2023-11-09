package com.carlos.springbootcurse.artifact;

import com.carlos.springbootcurse.artifact.converter.ArtifactDtoToArtifacConverter;
import com.carlos.springbootcurse.artifact.converter.ArtifactToArtifactDtoConverter;
import com.carlos.springbootcurse.artifact.dto.ArtifactDto;
import com.carlos.springbootcurse.system.Result;
import com.carlos.springbootcurse.system.StatusCode;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ArtifactController {
    private final ArtifactService artifactService;
    private  final ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter;
    private final ArtifactDtoToArtifacConverter artifactDtoToArtifacConverter;

    public ArtifactController(ArtifactService artifactService, ArtifactToArtifactDtoConverter artifactToArtifactDtoConverter, ArtifactDtoToArtifacConverter artifactDtoToArtifacConverter) {
        this.artifactService = artifactService;
        this.artifactToArtifactDtoConverter = artifactToArtifactDtoConverter;
        this.artifactDtoToArtifacConverter = artifactDtoToArtifacConverter;
    }

    @GetMapping("/api/v1/artifacts/{artifactId}")
    public Result findArtifactById(@PathVariable String artifactId){
       Artifact foundArtifact =  this.artifactService.findById((artifactId));
        ArtifactDto artifactDto = this.artifactToArtifactDtoConverter.convert(foundArtifact);

        return new Result(true, StatusCode.SUCCESS, "Find One Success", artifactDto);
    }
    @GetMapping("/api/v1/artifacts")
    public Result findAllArtifacts(){
        List<Artifact> foundArtifacts = this.artifactService.findAll();
        //Convert foundArtifacts to a list of the artifactsDtos
        List<ArtifactDto> artifactDtos = foundArtifacts.stream().map(
                        this.artifactToArtifactDtoConverter::convert)
                .collect(Collectors.toList());


        return new Result(true, StatusCode.SUCCESS, "Find All Success", artifactDtos);
    }

    @PostMapping("/api/v1/artifacts")
    public Result addArtifact(@Valid @RequestBody ArtifactDto artifactDto){
        //Convert artifactDto to artifact
        Artifact newArtifact = this.artifactDtoToArtifacConverter.convert(artifactDto);
        Artifact savedArtifact = this.artifactService.save(newArtifact);
        ArtifactDto savedArtifactDto = this.artifactToArtifactDtoConverter.convert(savedArtifact);
        return new Result(true, StatusCode.SUCCESS,"Add Success", savedArtifactDto);
    }
    @PutMapping("/api/v1/artifacts/{artifactId}")
    public Result updateArtifact(@PathVariable String artifactId, @Valid @RequestBody ArtifactDto artifactDto ){
       Artifact update = this.artifactDtoToArtifacConverter.convert(artifactDto);
       Artifact updatedArtifact = this.artifactService.update(artifactId, update);
       ArtifactDto updatedArtifactDto = this.artifactToArtifactDtoConverter.convert(updatedArtifact);
        return  new Result(true, StatusCode.SUCCESS, "Updated Success", updatedArtifactDto);
    }


}










