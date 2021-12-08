package com.example.repository

import com.example.entities.Card
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface CardRepository extends JpaRepository<Card, Integer>{

}