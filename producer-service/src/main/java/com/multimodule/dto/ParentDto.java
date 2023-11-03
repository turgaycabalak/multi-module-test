package com.multimodule.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.aspectj.runtime.internal.Conversions.longValue;

@Data
public class ParentDto {

    public static final String ID_ALIAS = "parent_id";
    public static final String PARENT_NAME_ALIAS = "parent_name";


    private Long parentId;
    private String parentName;
    private List<ChildDto> childDtos = new ArrayList<>();

    public ParentDto(Object[] tuples, Map<String, Integer> aliasToIndexMap) {
        this.parentId = longValue(tuples[aliasToIndexMap.get(ID_ALIAS)]);
        this.parentName = tuples[aliasToIndexMap.get(PARENT_NAME_ALIAS)].toString();
    }

    public ParentDto(Long parentId, String parentName) {
        this.parentId = parentId;
        this.parentName = parentName;
    }

    public ParentDto(Object[] tuples) {
        //TODO: tüm array'i dön ve value'leri ilgili field'lara set'le.
    }

    public ParentDto() {
    }
}
