package com.example.repository

import com.example.entities.Match
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface MatchRepository extends JpaRepository<Match, Integer>{

}
