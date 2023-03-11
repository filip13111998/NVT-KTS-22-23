package ftn.uns.ac.rs.NVTKTS20222023.repository;

import ftn.uns.ac.rs.NVTKTS20222023.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LocationRepository extends JpaRepository<Location , Long> {
}
