package com.example.demo.repository;

import com.example.demo.model.Participation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticiptionRepository extends MongoRepository<Participation, String> {

}
