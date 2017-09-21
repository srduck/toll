package srduck.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;
import srduck.exceptions.MyTestException;
import srduck.repositories.PointDTORepository;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class PointDTOServiceTest {

    private PointDTO pointDTO;
    private List<PointDTO> list;
    private final String id = "ab123cd";
    private final long time = 123456789;

    @TestConfiguration
    static class PointDTOServiceTestConfiguration {
        @Bean
        public PointDTOService pointDTOService(){
            return new PointDTOService();
        }
    }

    @Autowired
    PointDTOService pointDTOService;

    @MockBean
    PointDTORepository pointDTORepository;

    @Before
    public void setUp() throws Exception {
        pointDTO = new PointDTO();
        pointDTO.setTrackerId(id);
        pointDTO.setTime(time);

        list = new ArrayList<>();
        list.add(pointDTO);

        when(pointDTORepository.findByTrackerIdAndTime(id, time)).thenReturn(pointDTO);
        when(pointDTORepository.save(pointDTO)).thenReturn(pointDTO);
        when(pointDTORepository.findByTrackerIdOrderByTimeDesc(id, new PageRequest(0,10)))
                .thenReturn(list);
        when(pointDTORepository.findByTimeGreaterThan(1)).thenReturn(list);
        when(pointDTORepository.findAll()).thenReturn(list);
        doThrow(MyTestException.class).when(pointDTORepository).delete(pointDTO);
    }

    @Test
    public void create() throws Exception {
        PointDTO resultPoint = pointDTOService.create(this.pointDTO);
        assertEquals(resultPoint,this.pointDTO);
    }

    @Test(expected = MyTestException.class)
    public void delete() throws Exception {
        pointDTOService.delete(pointDTO);
    }

    @Test
    public void update() throws Exception {
        PointDTO resultPoint = pointDTOService.update(this.pointDTO);
        assertEquals(resultPoint,this.pointDTO);
    }

    @Test
    public void read() throws Exception {
        List<PointDTO> resultList = pointDTOService.read();
        assertTrue(resultList.size() == 1);
        assertEquals(resultList.get(0),pointDTO);
    }

    @Test
    public void findById() throws Exception {

        IdPointDTO idPointDTO = new IdPointDTO(id,time);
        PointDTO resultPoint = pointDTOService.findById(idPointDTO);

        assertEquals(resultPoint.getTrackerId(), idPointDTO.getTrackerId());
        assertEquals(resultPoint.getTime(), idPointDTO.getTime());
    }

    @Test
    public void findByTimeGreaterThan() throws Exception {
        List<PointDTO> resultList = pointDTOService.findByTimeGreaterThan(1);
        assertTrue(resultList.size() == 1);
        assertEquals(resultList.get(0),pointDTO);
    }

    @Test
    public void getLastNRecords() throws Exception {
        List<PointDTO> resultList = pointDTOService.getLastNRecords(id, 10);
        assertTrue(resultList.size() == 1);
        assertEquals(resultList.get(0),pointDTO);
    }

}