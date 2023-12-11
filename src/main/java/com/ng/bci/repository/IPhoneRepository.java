package com.ng.bci.repository;

import com.ng.bci.domain.Phone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IPhoneRepository extends JpaRepository<Phone, Integer> {
}
