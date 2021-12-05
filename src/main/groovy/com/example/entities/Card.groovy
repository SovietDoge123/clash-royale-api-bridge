package com.example.entities

import javax.persistence.*
import javax.validation.constraints.NotNull

@Entity
@Table(name = 'card')
class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id

    @NotNull
    @Column(name = 'name')
    String name
}
