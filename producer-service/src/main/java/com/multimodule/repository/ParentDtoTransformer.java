package com.multimodule.repository;

import com.multimodule.dto.ChildDto;
import com.multimodule.dto.ParentDto;
import org.hibernate.query.ResultListTransformer;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.aspectj.runtime.internal.Conversions.longValue;

@Component
public class ParentDtoTransformer implements TupleTransformer, ResultListTransformer {

    private Map<Long, ParentDto> parentDtoMap = new LinkedHashMap<>();

    @Override
    public Object transformTuple(Object[] tuple, String[] aliases) {
        /**
         * parent_id -> 0,
         * parent_name -> 1,
         * child_id -> 2,
         * child_name -> 3
         */
        Map<String, Integer> aliasToIndexMap = aliasToIndexMap(aliases);

        /**
         *
         */
        Long parentId = longValue(tuple[aliasToIndexMap.get(ParentDto.ID_ALIAS)]);

        ParentDto parentDto = parentDtoMap.computeIfAbsent(parentId, id -> new ParentDto(tuple, aliasToIndexMap));

        parentDto.getChildDtos().add(new ChildDto(tuple, aliasToIndexMap));

        return parentDto;
    }

    @Override
    public List transformList(List resultList) {
        return new ArrayList<>(parentDtoMap.values());
    }

    public Map<String, Integer> aliasToIndexMap(String[] aliases) {
        Map<String, Integer> aliasToIndexMap = new LinkedHashMap<>();

        for (int i = 0; i < aliases.length; i++) {
            aliasToIndexMap.put(aliases[i], i);
        }

        return aliasToIndexMap;
    }

}
