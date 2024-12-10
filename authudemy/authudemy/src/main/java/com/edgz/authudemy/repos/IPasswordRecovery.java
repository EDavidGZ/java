package com.edgz.authudemy.repos;

import com.edgz.authudemy.models.PasswordRecovery;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IPasswordRecovery extends JpaRepository<PasswordRecovery, Long> {
}
