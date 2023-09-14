package com.goalddae.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.goalddae.dto.soccerField.SoccerFieldDTO;
import com.goalddae.entity.SoccerField;
import com.goalddae.repository.SoccerFieldRepository;
import com.goalddae.service.SoccerFieldService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
public class SoccerFieldControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private SoccerFieldService soccerFieldService;

    @MockBean
    private SoccerFieldRepository soccerFieldRepository;

    @Test
    @Transactional
    @DisplayName("검색 조건에 맞는 축구장을 찾을 수 있는지 테스트")
    public void searchFieldsTest() throws Exception {
        // Field 객체 생성
        SoccerField sampleField = SoccerField.builder()
                .id(1L)
                .region("성남")
                .fieldName("테스트구장")
                .build();

        when(soccerFieldService.searchSoccerFields(any(String.class)))
                .thenReturn(Arrays.asList(sampleField));

        mockMvc.perform(get("/field/search")
                        .param("region", "성남")
                        .param("fieldName", "모란 풋살장"))
                .andExpect(status().isOk())
                .andExpect(result -> print());
    }


    @Test
    @DisplayName("구장 객체 생성 테스트")
    void saveSoccerFieldTest() throws Exception {
        SoccerField soccerField = SoccerField.builder()
                .fieldName("테스트 풋살장")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("14x16")
                .fieldImg1("테스트이미지1")
                .inOutWhether("실외")
                .grassWhether("인조")
                .region("서울")
                .build();

        when(soccerFieldService
                .save(any(SoccerField.class)))
                .thenReturn(soccerField);

        // 구장 등록
        mockMvc.perform(post("/field/save")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(soccerField)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("fieldName", is(soccerField.getFieldName())));
    }

    @Test
    @DisplayName("구장 객체 수정 테스트")
    public void updateSoccerFieldTest() throws Exception {
        String jsonContent = "{ " +
                "\"fieldImg1\" : \"img1\", " +
                "\"fieldImg2\" : \"img2\", " +
                "\"fieldImg3\" : \"img3\", " +
                "\"fieldName\" : \"변경된 구장\", " +
                "\"fieldSize\" : \"14x15\", " +
                "\"grassWhether\" : \"인조\", " +
                "\"inOutWhether\" : \"실내\", " +
                "\"toiletStatus\" : 1, " +
                "\"region\" : \"변경된 지역\", " +
                "\"showerStatus\" : 1, " +
                "\"parkingStatus\" : 1, " +
                "\"deleteStatus\" : 0 " +
                "}";

        mockMvc.perform(put("/field/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonContent))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("구장 객체 삭제 테스트")
    public void deleteSoccerFieldTest() throws Exception {
        // Given
        Long soccerFieldId = 1L;
        SoccerField soccerField = SoccerField.builder()
                .id(soccerFieldId)
                .fieldName("테스트 풋살장")
                .toiletStatus(true)
                .showerStatus(true)
                .parkingStatus(true)
                .fieldSize("14x16")
                .fieldImg1("테스트이미지1")
                .inOutWhether("실외")
                .grassWhether("인조")
                .province("서울")
                .region("서울")
                .reservationFee(8000)
                .build();

        // When
        mockMvc.perform(post("/field/delete")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(soccerField)))
                .andExpect(status().isOk());

        // Then
        verify(soccerFieldService, times(1)).delete(anyLong());
    }

    @Test
    @Transactional
    @DisplayName("구장 정보 가져오기")
    public void getFieldInfo() throws Exception{
        String url = "/field/getFieldInfo/1";

        ResultActions result = mockMvc.perform(get(url).accept(MediaType.APPLICATION_JSON));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.fieldName").value("테스트 구장"));
    }

    @Test
    @DisplayName("예약할 구장 찾아보기 테스트")
    public void findReservationFieldTest() throws Exception {
        // Given
        Long userId = 1L;
        String province = "서울";
        String inOutWhether = "실내";
        String grassWhether = "잔디";
        LocalDate reservationDate = LocalDate.now();
        String reservationPeriod = "오전";

        SoccerFieldDTO mockSoccerFieldDTO = new SoccerFieldDTO();

        List<SoccerFieldDTO> mockResultList = Arrays.asList(mockSoccerFieldDTO);

        when(soccerFieldService.findAvailableField(Optional.ofNullable(userId), province, inOutWhether, grassWhether, reservationDate, reservationPeriod))
                .thenReturn(mockResultList);

        // When & Then
        mockMvc.perform(get("/field/reservation/list")
                        .param("userId", String.valueOf(userId))
                        .param("province", province)
                        .param("inOutWhether", inOutWhether)
                        .param("grassWhether", grassWhether)
                        .param("reservationDate", reservationDate.toString())
                        .param("reservationPeriod", reservationPeriod))
                .andExpect(status().isOk());
    }
}
