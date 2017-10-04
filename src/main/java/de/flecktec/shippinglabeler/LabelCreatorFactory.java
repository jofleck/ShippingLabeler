package de.flecktec.shippinglabeler;

import javafx.scene.control.Label;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by jonas on 19.08.17.
 */
public class LabelCreatorFactory {

    public static LabelCreator buildCreator(LabelType type) {
        assert type != null;
        if(type == LabelType.DHL_Private) {
            return new DHLLabelCreator();
        } else if(type == LabelType.GLS_Private) {
            return new GLSLabelCreator();
        } else if(type == LabelType.DPD_PRIVATE) {
            return new DPDLabelCreator();
        } else if(type == LabelType.AmazonDHLRetoure_Private) {
            return new AmazonRetourLabelCreator();
        } else if(type == LabelType.DHL_RETOURE) {
            return new DHLRetoureLabelCreator();
        }
        else {
            throw new NotImplementedException();
        }
    }
}
