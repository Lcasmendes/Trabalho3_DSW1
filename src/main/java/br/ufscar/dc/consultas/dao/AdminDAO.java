package br.ufscar.dc.consultas.dao;

import br.ufscar.dc.consultas.domain.Admin;
import jakarta.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AdminDAO extends JpaRepository<Admin, Long> {;
  

	@Query("SELECT p FROM Admin p WHERE p.Email = :Email")
	Admin findByEmail(String Email);
}
