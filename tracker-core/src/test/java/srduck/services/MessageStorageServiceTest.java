package srduck.services;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dto.PointDTO;
import srduck.repositories.PointDTORepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
public class MessageStorageServiceTest {

    private PointDTO pointDTO;

    @TestConfiguration
    static class PointDTOServiceTestConfiguration {
        @Bean
        public PointDTOService pointDTOService(){
            return new PointDTOService();
        }
    }

    @Autowired
    private PointDTOService pointDTOService;

    @MockBean
    private PointDTORepository pointDTORepository;

    @Before
    public void setUp() throws Exception {
        pointDTO = new PointDTO();
        pointDTO.setTrackerId("ab123cd");
        pointDTO.setTime(123456789);
    }

    @Test
    public void put() throws Exception {
        when(pointDTORepository.save(pointDTO)).thenReturn(pointDTO);
        PointDTO returnPoint = pointDTOService.create(pointDTO);
        assertThat(returnPoint).isEqualTo(pointDTO);
    }

    @Test
    public void getTail() throws Exception {

        List<PointDTO> listPoint = new ArrayList<>();
        for (int i = 1; i < 11; i++){
            PointDTO point = new PointDTO();
            point.setTrackerId("ab123cd");
            point.setTime(123456789 + i);
            listPoint.add(point);
        }

        when(pointDTORepository.findByTimeGreaterThan(pointDTO.getTime())).thenReturn(listPoint);
        List<PointDTO> returnList = pointDTOService.findByTimeGreaterThan(pointDTO.getTime());
        assertThat(returnList.size()).isEqualTo(10);
        assertThat(returnList.get(0).getTime()).isEqualTo(pointDTO.getTime() + 1);

    }

}