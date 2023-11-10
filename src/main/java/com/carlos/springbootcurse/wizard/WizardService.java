package com.carlos.springbootcurse.wizard;

import com.carlos.springbootcurse.system.exception.ObjectNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class WizardService {
    private final WizardRepository wizardRepository;

    public WizardService(WizardRepository wizardRepository) {
        this.wizardRepository = wizardRepository;
    }
    public Wizard findById(Integer wizardId){

        return this.wizardRepository.findById(wizardId)
                .orElseThrow(() -> new ObjectNotFoundException("wizard", wizardId));
    }
    public List<Wizard> findAll(){
        return  this.wizardRepository.findAll();
    }

    public  Wizard save(Wizard newWizard){
        return this.wizardRepository.save(newWizard);
    }

}
