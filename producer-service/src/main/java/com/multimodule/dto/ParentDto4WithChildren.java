package com.multimodule.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDto4WithChildren {

    private Long id;
    //    private String parentUnique;
    private String parentName;
    private String parentDescription;
//    private BigDecimal parentNumber;
//    private boolean parentStatus;

    List<ChildDto4> childDto4s = new ArrayList<>();
}
