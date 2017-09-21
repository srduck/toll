package srduck.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import srduck.dto.IdPointDTO;
import srduck.dto.PointDTO;
import srduck.requests.ConstraintTrack;
import srduck.services.PointDTOService;
import srduck.services.RoleService;
import srduck.services.UserService;

import java.util.ArrayList;
import java.util.List;


import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by main on 21.09.17.
 */
@RunWith(SpringRunner.class)
@WebMvcTest(TrackerController.class)
public class TrackerControllerTest {

    private PointDTO pointDTO;
    private String jsonString;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PointDTOService pointDTOService;

    @MockBean
    private RoleService roleService;

    @MockBean
    private UserService userService;

    @Before
    public void setUp() throws Exception {
        pointDTO = new PointDTO();
        pointDTO.setTrackerId("ab123cd");
        pointDTO.setTime(123456789L);
        ObjectMapper mapper = new ObjectMapper();
        jsonString = mapper.writeValueAsString(pointDTO);
    }

    @Test
    public void getCoordinates() throws Exception {

        given(pointDTOService.create(pointDTO)).willReturn(pointDTO);

        mockMvc.perform(post("/coords")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(content().string("true"));
    }

    @Test
    public void getLastNRecords() throws Exception {

        ConstraintTrack testTrack = new ConstraintTrack();
        testTrack.setTrackerId(pointDTO.getTrackerId());
        testTrack.setPointConunt(10);
        ObjectMapper mapper = new ObjectMapper();
        String jsonTrack = mapper.writeValueAsString(testTrack);

        List<PointDTO> listPoint = new ArrayList<PointDTO>();
        for (int i = 0; i < 9; i++){
            listPoint.add(new PointDTO());
        }
        listPoint.add(pointDTO);

        given(pointDTOService.getLastNRecords(testTrack.getTrackerId(), testTrack.getPointCount())).willReturn(listPoint);

        mockMvc.perform(post("/track")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonTrack))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(10)))
                .andExpect(jsonPath("$[0].trackerId", is(pointDTO.getTrackerId())))
                .andExpect(jsonPath("$[0].time", is((int)pointDTO.getTime())));
    }

    @Test
    public void deleteRecord() throws Exception {

        IdPointDTO idPoint = new IdPointDTO(pointDTO.getTrackerId(), pointDTO.getTime());

        given(pointDTOService.findById(idPoint)).willReturn(pointDTO);

        ObjectMapper mapper = new ObjectMapper();
        String jsonString = mapper.writeValueAsString(idPoint);

        mockMvc.perform(post("/deletePoint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message",is("Запись успешно удалена")));
    }

    @Test
    public void createRecord() throws Exception {

        given(pointDTOService.create(pointDTO)).willReturn(pointDTO);

        mockMvc.perform(post("/createPoint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                //.andDo(print())
                .andExpect(jsonPath("$.trackerId", is(pointDTO.getTrackerId())))
                .andExpect(jsonPath("$.time", is((int)pointDTO.getTime())));
    }

    @Test
    public void editRecord() throws Exception {

        given(pointDTOService.update(pointDTO)).willReturn(pointDTO);

        mockMvc.perform(post("/editPoint")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonString))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackerId", is(pointDTO.getTrackerId())))
                .andExpect(jsonPath("$.time", is((int)pointDTO.getTime())));
    }

}