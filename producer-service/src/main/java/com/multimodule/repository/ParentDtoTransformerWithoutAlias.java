package com.multimodule.repository;

import com.multimodule.dto.ChildDto;
import com.multimodule.dto.ParentDto;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Component;

import java.util.*;

import static org.aspectj.runtime.internal.Conversions.longValue;

@Component
public class ParentDtoTransformerWithoutAlias implements TupleTransformer/*, ResultListTransformer*/ {

    private Map<Long, ParentDto> parentDtoMap = new LinkedHashMap<>();
    private ParentDto parentDto = new ParentDto();


    /**
     * @param tuple   The result elements
     *                1 Long
     *                "parent-name-1" String
     *                1 Long
     *                "child-name-1" String
     * @param aliases The result aliases ("parallel" array to tuple)
     * @return
     */
    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        System.out.println(String.format("Parent Name: %s,   Child Name: %s", tuple[1], tuple[3]));
        /**
         * parent_id -> 0,
         * parent_name -> 1,
         * child_id -> 2,
         * child_name -> 3
         */
//        Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);

        /**
         *  Set parent object
         */
        Object parentIdObject = tuple[0];
        Long parentId = (Long) parentIdObject;

        Object parentNameObject = tuple[1];
        String parentName = (String) parentNameObject;

//        ParentDto parentDto = parentDtoMap.computeIfAbsent(parentId, id -> new ParentDto(parentId, parentName));
        this.parentDto.setParentId(parentId);
        this.parentDto.setParentName(parentName);

        /**
         * Set children of parent object
         */
        Object childIdObject = tuple[2];
        Long childId = (Long) childIdObject;

        Object childNameObject = tuple[3];
        String childName = (String) childNameObject;

        this.parentDto.getChildDtos().add(new ChildDto(childId, childName));

        return this.parentDto;
    }

//    @Override
//    public List transformList(List resultList) {
//        return new ArrayList<>(parentDtoMap.values());
//    }

    public Map<String, Integer> aliasToIndexMap(String[] aliases) {
        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }

        return aliasToIndexMap;
    }

}
