package com.multimodule.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ParentDto3 {

    private Long id;
//    private String parentUnique;
    private String parentName;
    private String parentDescription;
//    private BigDecimal parentNumber;
//    private boolean parentStatus;


}
