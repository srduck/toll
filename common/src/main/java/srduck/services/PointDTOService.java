package srduck.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import srduck.dto.PointDTO;
import srduck.repositories.PointDTORepository;

import java.util.List;

@Service
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
}
