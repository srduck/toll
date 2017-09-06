package srduck.repositories;

import org.springframework.data.repository.CrudRepository;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;

public interface PointDTORepository extends CrudRepository<PointDTO,IdPointDTO> {
}
