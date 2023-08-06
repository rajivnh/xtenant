package com.utp.persistence.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.utp.persistence.model.XUser;

@Repository
public interface XUserRepository extends JpaRepository<XUser, String> {

}
