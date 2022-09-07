package org.example.domain.HasXYZ;

import com.fasterxml.jackson.core.JsonProcessingException;
import org.example.BaseCondition;
import org.example.domain.HasXYZ.model.Dolphin;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HasXYZTest extends BaseCondition {

    @Test
    @DisplayName("Has나 Is가 직렬화에 어떤 영향")
    /**
     * isXYZ는 primitive boolean에 쓰는데,
     * hasXYZ는 안쓰는디..
     */
    void test() throws JsonProcessingException {

        Dolphin dolphin = new Dolphin("dol",true,false,false);

        String s = objectWriter.writeValueAsString(dolphin);

        System.out.println(s);

    }
}
