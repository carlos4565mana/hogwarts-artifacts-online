package com.carlos.springbootcurse.wizard;

import com.carlos.springbootcurse.system.Result;
import com.carlos.springbootcurse.system.StatusCode;
import com.carlos.springbootcurse.wizard.converter.WizardDtoToWizardConverter;
import com.carlos.springbootcurse.wizard.converter.WizardToWizardDtoConverter;
import com.carlos.springbootcurse.wizard.dto.WizardDto;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/wizards")
public class WizardController {

    private final WizardService wizardService;
    private final WizardDtoToWizardConverter wizardDtoToWizardConverter;
    private final WizardToWizardDtoConverter wizardToWizardDtoConverter;

    public WizardController(WizardService wizardService, WizardDtoToWizardConverter wizardDtoToWizardConverter, WizardToWizardDtoConverter wizardToWizardDtoConverter) {
        this.wizardService = wizardService;
        this.wizardDtoToWizardConverter = wizardDtoToWizardConverter;
        this.wizardToWizardDtoConverter = wizardToWizardDtoConverter;
    }

    @GetMapping("/{wizardId}")
    public Result findWizardById(@PathVariable  Integer wizardId){
        Wizard wizard = this.wizardService.findById(wizardId);
        WizardDto wizardDto = this.wizardToWizardDtoConverter.convert(wizard);

        return new Result(true, StatusCode.SUCCESS, "Find One Success",wizardDto);
    }
    @GetMapping
    public Result findAllWizards(){
        List<Wizard> foundWizards = this.wizardService.findAll();

        List<WizardDto> wizardDtos = foundWizards.stream()
                .map(this.wizardToWizardDtoConverter::convert)
                .collect(Collectors.toList());
        return new Result(true, StatusCode.SUCCESS, "Find All Success", wizardDtos);
    }
    @PostMapping
    public Result addWizard(@Valid @RequestBody WizardDto wizardDto){
        Wizard newWizard = this.wizardDtoToWizardConverter.convert(wizardDto);
        Wizard savedWizard = this.wizardService.save(newWizard);
        WizardDto savedWizardDto = this.wizardToWizardDtoConverter.convert(savedWizard);
        return new Result(true, StatusCode.SUCCESS, "Add Success", savedWizardDto);
    }
}
