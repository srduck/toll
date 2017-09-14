package srduck.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;
import srduck.repositories.PointDTORepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class PointDTOService {


    @Autowired
    PointDTORepository pointDTORepository;

    public PointDTO create(PointDTO pointDTO){
        return pointDTORepository.save(pointDTO);
    }

    public void delete(PointDTO pointDTO){
        pointDTORepository.delete(pointDTO);
    }

    public PointDTO update(PointDTO pointDTO){
        return pointDTORepository.save(pointDTO);
    }

    public List<PointDTO> read(){
        return (List<PointDTO>)pointDTORepository.findAll();
    }

    public PointDTO findById(IdPointDTO idPointDTO){
        return pointDTORepository.findByTrackerIdAndTime(idPointDTO.getTrackerId(), idPointDTO.getTime());
    }

    public List<PointDTO> findByTimeGreaterThan(long time){
        return pointDTORepository.findByTimeGreaterThan(time);
    }

    public List<PointDTO> getLastNRecords(String trackerId, int n){
        return pointDTORepository.findByTrackerIdOrderByTimeDesc(trackerId, new PageRequest(0,n));
    }

   }
