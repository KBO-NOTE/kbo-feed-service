package com.kbonote.kbofeedservice.domain.player.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "player")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name_ko")
    private String nameKo;

    @Column(name = "name_en")
    private String nameEn;

    @Column(name = "birth_date")
    private LocalDate birthDate;

    @Column(name = "nationality")
    private String nationality;

    @Enumerated(EnumType.STRING)
    @Column(name = "bats")
    private PlayerBatType bats;

    @Enumerated(EnumType.STRING)
    @Column(name = "throws")
    private PlayerThrowType throwsType;

    @Column(name = "debut_date")
    private LocalDate debutDate;

    @Column(name = "retire_date")
    private LocalDate retireDate;

    @Column(name = "hitter_stat_link")
    private String hitterStatLink;

    @Column(name = "pitcher_stat_link")
    private String pitcherStatLink;
}
