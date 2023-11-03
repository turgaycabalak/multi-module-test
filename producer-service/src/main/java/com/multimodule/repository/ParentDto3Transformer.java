package com.multimodule.repository;

import com.multimodule.dto.ParentDto3;
import org.hibernate.query.TupleTransformer;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Component
public class ParentDto3Transformer implements TupleTransformer<ParentDto3> {

    @Override
    public ParentDto3 transformTuple(Object[] tuple, String[] aliases) {
//        Long id = Objects.isNull(tuple[0]) ? null : Long.valueOf(tuple[0].toString());

        return new ParentDto3((Long) tuple[0], (String) tuple[1], (String) tuple[2]);
    }

}
