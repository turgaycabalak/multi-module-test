package com.multimodule.repository;

import com.multimodule.dto.ChildDto4;
import com.multimodule.dto.ParentDto4WithChildren;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Component
@Getter
@Setter
public class ParentDto4WithChildrenTransformer implements TupleTransformer<ParentDto4WithChildren> {

    private ParentDto4WithChildren parentDto = new ParentDto4WithChildren();

    @Override
    public ParentDto4WithChildren transformTuple(Object[] tuple, String[] aliases) {


//        ParentDto4WithChildren parentDto4WithChildren = new ParentDto4WithChildren();
        System.out.println(String.format("Parent Name: %s,   Child Name: %s", tuple[1], tuple[4]));

//        if (Objects.nonNull(parentDto) && Objects.nonNull(parentDto.getId()) && Objects.nonNull(parentDto.getParentName()) && Objects.nonNull(parentDto.getParentDescription())) {
//        }
            parentDto.setId((Long) tuple[0]);
            parentDto.setParentName((String) tuple[1]);
            parentDto.setParentDescription((String) tuple[2]);


        parentDto.getChildDto4s()
                .add(new ChildDto4((Long) tuple[3], (String) tuple[4], (Boolean) tuple[5]));


        return parentDto;
    }
}
