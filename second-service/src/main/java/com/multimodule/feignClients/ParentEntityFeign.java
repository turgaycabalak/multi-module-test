package com.multimodule.feignClients;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;

import java.math.BigDecimal;
import java.util.List;

public record ParentEntityFeign(
         Long id,
         String parentUnique,
         String parentName,
         String parentDescription,
         BigDecimal parentNumber,
         boolean parentStatus
) {
}
