package ftn.uns.ac.rs.NVTKTS20222023.repository;

import ftn.uns.ac.rs.NVTKTS20222023.model.Route;
import ftn.uns.ac.rs.NVTKTS20222023.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RouteRepository extends JpaRepository<Route,Long> {
}
