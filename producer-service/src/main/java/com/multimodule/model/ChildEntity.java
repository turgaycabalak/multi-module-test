package com.multimodule.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Entity
@NoArgsConstructor
@Table(name = "children")
//NOTE: If you want to use JsonIdentityInfo, then you should use FetchType.EAGER
@JsonIdentityInfo(
        generator = ObjectIdGenerators.PropertyGenerator.class,
        property = "id"
)
public class ChildEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String childUnique;
    private String childName;

//    @Lob
    @Column(columnDefinition = "TEXT")
    private String childDescription;

    private BigDecimal childNumber;
    private boolean childStatus;

//    @JsonIgnore
//    @JsonBackReference
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parent_id", nullable = false)
    private ParentEntity parentEntity;

//    public ChildEntity(String childName, ParentEntity parentEntity) {
//        this.childName = childName;
//        this.parentEntity = parentEntity;
//    }


        public ChildEntity(String childUnique,
                       String childName,
                       String childDescription,
                       BigDecimal childNumber,
                       boolean childStatus,
                       ParentEntity parentEntity) {
        this.childUnique = childUnique;
        this.childName = childName;
        this.childDescription = childDescription;
        this.childNumber = childNumber;
        this.childStatus = childStatus;
        this.parentEntity = parentEntity;
    }
}
