package ftn.uns.ac.rs.NVTKTS20222023.repository;

import ftn.uns.ac.rs.NVTKTS20222023.model.MarkDriver;
import ftn.uns.ac.rs.NVTKTS20222023.model.Ride;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarkDriveRepository extends JpaRepository<MarkDriver,Long> {
}
