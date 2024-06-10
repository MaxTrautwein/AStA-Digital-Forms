package de.PSWTM.DigitalForms.Attchments;

import de.PSWTM.DigitalForms.controller.FormsController;
import de.PSWTM.DigitalForms.model.Attachment;
import de.PSWTM.DigitalForms.model.Form;
import de.PSWTM.DigitalForms.model.FormElement;
import de.PSWTM.DigitalForms.model.FormSection;
import lombok.Getter;
import org.apache.commons.lang3.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class AttachmentUtil {

    private Form form;

    @Getter
    private List<Attachment> attachmentsReq = new ArrayList<>();
    @Getter
    private List<Attachment> attachmentsUser = new ArrayList<>();

    // Limits the Allowed Operations
    private enum MathOperator{
        Larger(">"),
        Smaller("<"),
        Equal("="),
        Unequal("!");

        private String value;

        MathOperator(String s) {
            this.value = s;
        }

        public static MathOperator fromValue(String value) {
            for (MathOperator b : MathOperator.values()) {
                if (b.value.equals(value)) {
                    return b;
                }
            }
            throw new IllegalArgumentException("Unexpected value '" + value + "'");
        }
    }

    private Logger logger = LoggerFactory.getLogger(FormsController.class);

    public AttachmentUtil(Form form) {
        this.form = form;
        analyzeRequiredAttachments();
    }

    private boolean isMathOperator(String mathOperator){
        for (MathOperator operator : MathOperator.values()) {
            if (operator.value.equals(mathOperator)) {
                return true;
            }
        }
        return false;

    }

    // TODO: Add =< and => support
    // TODO: Then Update the ab 150 Logic Attachment
    private boolean isConditionMatch(String matchVal, FormElement target){
        if (Objects.equals(target.getValue(), "") || target.getValue() == null){
            return false; // can't compare against nothing
        }
        String mathOperator = String.valueOf(matchVal.charAt(0));
        if (isMathOperator(mathOperator)){
            MathOperator operator = MathOperator.fromValue(mathOperator);
            String ValuePart = matchVal.substring(1);
            switch ( operator){
             case Larger -> {
                 if (NumberUtils.isParsable(target.getValue()) && NumberUtils.isParsable(ValuePart)){
                     // Both Values are Numbers
                     return NumberUtils.createDouble(target.getValue()) > NumberUtils.createDouble(ValuePart);
                 }
             }
             case Smaller -> {
                 if (NumberUtils.isParsable(target.getValue()) && NumberUtils.isParsable(ValuePart)){
                     // Both Values are Numbers
                     return NumberUtils.createDouble(target.getValue()) < NumberUtils.createDouble(ValuePart);
                 }
             }
             case Equal -> {
                 return Objects.equals(target.getValue(), ValuePart);
             }
             case Unequal -> {
                 return !Objects.equals(target.getValue(), ValuePart);
             }
         }

        }else {
            // If there is no operation, test if they are equal
            return Objects.equals(target.getValue(), matchVal);
        }
        return false;
    }

    private boolean isRequired(String ref, String val){
        for (FormSection formSection : this.form.getForm()){
            for (FormElement formElement: formSection.getItems()){
                if (Objects.equals(formElement.getId(), ref)){
                    // Found the Reference
                    // Make the Check
                    return isConditionMatch(val,formElement);
                }
            }
        }


        return false;
    }

    private void analyzeRequiredAttachments(){
        for (Attachment attachment : this.form.getAttachments()){
            switch (attachment.getRequired()){
                case ALWAYS -> {
                    attachmentsReq.add(attachment);
                    break;
                }
                case USER -> {
                    attachmentsUser.add(attachment);
                    break;
                }
                case CONDITIONAL -> {
                    if (isRequired(attachment.getConditionRef(), attachment.getConditionRefVal())) {
                        attachmentsReq.add(attachment);
                    }
                    break;
                }
                default -> {
                    logger.error("attachment.Required hold Unexpected Value: " + attachment.getRequired().toString() );
                }
            }


        }



    }

}
