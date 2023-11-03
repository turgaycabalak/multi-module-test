package com.multimodule.dto;

import lombok.Data;

import java.util.Map;

import static org.aspectj.runtime.internal.Conversions.longValue;

@Data
public class ChildDto {

    public static final String ID_ALIAS = "child_id";
    public static final String CHILD_NAME_ALIAS = "child_name";


    private Long childId;
    private String childName;

    public ChildDto(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        this.childId = longValue(tuples[aliasToIndexMap.get(ID_ALIAS)]);
        this.childName = tuples[aliasToIndexMap.get(CHILD_NAME_ALIAS)].toString();
    }

    public ChildDto(Long childId, String childName) {
        this.childId = childId;
        this.childName = childName;
    }

    public ChildDto(Object[] tuples) {
    }
}