package de.flecktec.shippinglabeler;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonas on 19.08.17.
 */
public enum LabelType {
    DHL_Private(1), GLS_Private(2), DPD_PRIVATE(3), AmazonDHLRetoure_Private(4), DHL_RETOURE(5);

    private static final Map<Integer,LabelType> lookup
            = new HashMap<Integer,LabelType>();

    static {
        for(LabelType w : EnumSet.allOf(LabelType.class))
            lookup.put(w.getCode(), w);
    }

    private int code;

    LabelType(int code) {
        this.code = code;
    }

    private int getCode() {
        return code;
    }

    public static LabelType get(int code) {
        return lookup.get(code);
    }
}
