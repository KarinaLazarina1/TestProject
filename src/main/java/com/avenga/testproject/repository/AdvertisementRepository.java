package com.avenga.testproject.repository;

import com.avenga.testproject.entity.Advertisement;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.stream.Stream;

@Repository
public interface AdvertisementRepository extends JpaRepository<Advertisement, Long> {
    Stream<Advertisement> findAdvertisementBySenderName(String senderName);

}
