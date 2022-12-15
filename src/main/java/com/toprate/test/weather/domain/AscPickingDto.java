package com.toprate.test.weather.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AscPickingDto {
    private Long id;

    private String instructionNo;

    private Long categories;

    private String locationGroup;

    private String storageLocation;

    private String partNo;

    private String user;

    private Long totalQuantity;

    private Long numberOfOrder;

    private String deliveryDate;

    private String deliveryTime;

    private String packageCode;

    private String supplier;

    private String supplyDestination;

    private String containerMolecule;

    private Long quantity;

    private String assemblyLineCode;

    private String attentionComment;
}
