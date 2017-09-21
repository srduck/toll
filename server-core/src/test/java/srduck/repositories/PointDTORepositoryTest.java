package srduck.repositories;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import srduck.dto.PointDTO;

import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@DataJpaTest
public class PointDTORepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    PointDTORepository pointDTORepository;


    @Before
    public void setUp() throws Exception {
    }

    @Test
    public void findByTrackerIdAndTime() throws Exception {

        PointDTO pointDTO = new PointDTO();
        pointDTO.setTrackerId("ab123cd");
        pointDTO.setTime(123456789);
        entityManager.persist(pointDTO);
        entityManager.flush();

        PointDTO result = pointDTORepository.findByTrackerIdAndTime("ab123cd",123456789);
        assertEquals(result, pointDTO);


    }

    @Test
    public void findByTimeGreaterThan() throws Exception {

        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setTrackerId("ab123cd");
        pointDTO1.setTime(123456789);
        entityManager.persist(pointDTO1);
        entityManager.flush();

        PointDTO pointDTO2 = new PointDTO();
        pointDTO2.setTrackerId("ef456gh");
        pointDTO2.setTime(223456789);
        entityManager.persist(pointDTO2);
        entityManager.flush();

        List<PointDTO> list = pointDTORepository.findByTimeGreaterThan(123456789);
        assertEquals(1,list.size());
        assertEquals(list.get(0), pointDTO2);

    }

    @Test
    public void findByTrackerIdOrderByTimeDesc() throws Exception {
        PointDTO pointDTO1 = new PointDTO();
        pointDTO1.setTrackerId("ab123cd");
        pointDTO1.setTime(223456789);
        entityManager.persist(pointDTO1);
        entityManager.flush();

        PointDTO pointDTO2 = new PointDTO();
        pointDTO2.setTrackerId("ab123cd");
        pointDTO2.setTime(323456789);
        entityManager.persist(pointDTO2);
        entityManager.flush();

        PointDTO pointDTO3 = new PointDTO();
        pointDTO3.setTrackerId("ef456gh");
        pointDTO3.setTime(223456789);
        entityManager.persist(pointDTO3);
        entityManager.flush();

        PointDTO pointDTO4 = new PointDTO();
        pointDTO4.setTrackerId("ab123cd");
        pointDTO4.setTime(123456789);
        entityManager.persist(pointDTO4);
        entityManager.flush();


        List<PointDTO> list = pointDTORepository.findByTrackerIdOrderByTimeDesc("ab123cd",  new PageRequest(0,2));
        assertTrue(list.size() == 2);
        assertEquals(list.get(0),pointDTO2);
        assertEquals(list.get(1),pointDTO1);

    }


}