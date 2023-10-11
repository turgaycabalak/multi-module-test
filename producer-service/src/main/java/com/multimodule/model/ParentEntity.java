package com.multimodule.model;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;

@Data
@Entity
@NoArgsConstructor
@Table(name = "parents")
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class ParentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String parentUnique;
    private String parentName;

//    @Lob
    @Column(columnDefinition = "TEXT")
    private String parentDescription;

    private BigDecimal parentNumber;
    private boolean parentStatus;

//    @JsonManagedReference
    @OneToMany(mappedBy = "parentEntity", cascade = CascadeType.ALL)
    private List<ChildEntity> childEntities;

//    public ParentEntity(String parentName) {
//        this.parentName = parentName;
//    }

        public ParentEntity(String parentUnique,
                        String parentName,
                        String parentDescription,
                        BigDecimal parentNumber,
                        boolean parentStatus) {
        this.parentUnique = parentUnique;
        this.parentName = parentName;
        this.parentDescription = parentDescription;
        this.parentNumber = parentNumber;
        this.parentStatus = parentStatus;
    }
}
