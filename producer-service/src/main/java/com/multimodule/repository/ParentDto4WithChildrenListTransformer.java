package com.multimodule.repository;

import org.hibernate.query.ResultListTransformer;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ParentDto4WithChildrenListTransformer implements ResultListTransformer {
    @Override
    public List transformList(List resultList) {
        return null;
    }
}
