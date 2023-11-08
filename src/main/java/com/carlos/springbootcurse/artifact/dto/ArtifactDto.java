package com.carlos.springbootcurse.artifact.dto;
import com.carlos.springbootcurse.wizard.dto.WizardDto;

public record ArtifactDto (String id,
                            String name,
                            String description,
                            String imageUrl,
                            WizardDto owner ){

}
