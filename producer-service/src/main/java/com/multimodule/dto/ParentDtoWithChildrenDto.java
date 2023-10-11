package com.multimodule.dto;

public record ParentDtoWithChildrenDto(
        Long parentId,
        String parentName,
        String childName
) {
}
