package srduck.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;

import java.util.List;

public interface PointDTORepository extends CrudRepository<PointDTO,IdPointDTO> {
    public PointDTO findByTrackerIdAndTime(String trackerId, long time);
    public List<PointDTO> findByTimeGreaterThan(long time);
    public List<PointDTO> findByTrackerIdOrderByTimeDesc(String trackerId, Pageable pageable);
}
