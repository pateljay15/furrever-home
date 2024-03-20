package com.furreverhome.Furrever_Home.controller;

import com.furreverhome.Furrever_Home.dto.petadopter.ShelterResponseDto;
import com.furreverhome.Furrever_Home.services.adminservices.AdminService;
import com.furreverhome.Furrever_Home.services.petadopterservices.PetAdopterService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class AdminControllerTest {

    @Mock
    private AdminService adminService;

    @Mock
    private PetAdopterService petAdopterService;

    @InjectMocks
    private AdminController adminController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testGetAllShelters() {
        ShelterResponseDto shelterResponseDto = new ShelterResponseDto();
        List<ShelterResponseDto> shelterResponseDtoList = Collections.singletonList(shelterResponseDto);
        when(petAdopterService.getAllShelter()).thenReturn(shelterResponseDtoList);

        ResponseEntity<List<ShelterResponseDto>> responseEntity = adminController.getAllShelters();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(shelterResponseDtoList, responseEntity.getBody());
    }

    @Test
    public void testChangeVerifiedStatus_Success() {

        String email = "example@example.com";
        String status = "verified";
        when(adminService.changeVerifiedStatus(email, status)).thenReturn(true);

        ResponseEntity<?> responseEntity = adminController.changeVerifiedStatus(email, status);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(adminService, times(1)).changeVerifiedStatus(email, status);
    }

    @Test
    public void testChangeVerifiedStatus_Failure() {

        String email = "example@example.com";
        String status = "verified";
        when(adminService.changeVerifiedStatus(email, status)).thenReturn(false);

        ResponseEntity<?> responseEntity = adminController.changeVerifiedStatus(email, status);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        verify(adminService, times(1)).changeVerifiedStatus(email, status);
    }
}