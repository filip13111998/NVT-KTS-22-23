package ftn.uns.ac.rs.NVTKTS20222023.repository;

import ftn.uns.ac.rs.NVTKTS20222023.model.LoginHistory;
import ftn.uns.ac.rs.NVTKTS20222023.model.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LoginHistoryRepository extends JpaRepository<LoginHistory, Long> {
}
