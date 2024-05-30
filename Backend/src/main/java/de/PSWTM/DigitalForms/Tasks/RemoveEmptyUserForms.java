package de.PSWTM.DigitalForms.Tasks;

import de.PSWTM.DigitalForms.controller.FormsController;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.FormElement;
import de.PSWTM.DigitalForms.model.FormSection;
import de.PSWTM.DigitalForms.repository.FormRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

@Component
public class RemoveEmptyUserForms {

    private static final Logger log = LoggerFactory.getLogger(FormsController.class);

    @Autowired
    private FormRepository repository;

    @Scheduled(cron = "0 0 * * * ?")
    public void RemoveEmpty(){
        List<Form> forms = repository.findAllUserForms();
        for (Form form : forms){
            if(FormIsEmpty(form)){
                repository.delete(form);
            }
        }
    }

    private boolean FormIsEmpty(Form form){
        if (form.getForm() == null || form.getForm().isEmpty()){
            return true;
        }
        for (FormSection fs : form.getForm()){
            for (FormElement fe : fs.getItems()){
                if (!(fe.getValue() == null || Objects.equals(fe.getValue(), ""))){
                    return false;
                }
            }
        }
        return true;
    }

}
