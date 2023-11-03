package com.multimodule.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChildDto4 {

    private Long id;
//    private String childUnique;
    private String childName;
//    private String childDescription;
//    private BigDecimal childNumber;
    private boolean childStatus;

}
