package net.lesscoding.model.dto;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class WeaponBuff {

    private Integer combo;

    private Double appendFixedDamage;

    private Double maxPercentageOfHpDamage;

    private Double currentPercentageOfHpDamage;
}
