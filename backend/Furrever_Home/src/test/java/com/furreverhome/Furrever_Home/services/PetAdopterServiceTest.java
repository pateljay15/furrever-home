package com.furreverhome.Furrever_Home.services;


import com.furreverhome.Furrever_Home.dto.GenericResponse;
import com.furreverhome.Furrever_Home.dto.Pet.PetAdoptionRequestDto;
import com.furreverhome.Furrever_Home.entities.Pet;
import com.furreverhome.Furrever_Home.entities.PetAdopter;
import com.furreverhome.Furrever_Home.repository.AdopterPetRequestsRepository;
import com.furreverhome.Furrever_Home.repository.PetAdopterRepository;
import com.furreverhome.Furrever_Home.repository.PetRepository;
import com.furreverhome.Furrever_Home.services.petadopterservices.impl.PetAdopterServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.never;

@ExtendWith(MockitoExtension.class)
public class PetAdopterServiceTest {

    @Mock
    private PetRepository petRepository;

    @Mock
    private PetAdopterRepository petAdopterRepository;

    @Mock
    private AdopterPetRequestsRepository adopterPetRequestsRepository;

    @InjectMocks
    private PetAdopterServiceImpl petAdopterService;

    @Test
    void adoptPetRequestSuccess() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(1L);
        petAdoptionRequestDto.setPetID(2L);
        PetAdopter petAdopter = new PetAdopter();
        Pet pet = new Pet();
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.of(petAdopter));
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.of(pet));
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, times(1)).save(any());
        assertEquals("Adoption Request Successful.", response.getMessage());
    }

    @Test
    void adoptPetRequestPetAdopted() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(2L);
        petAdoptionRequestDto.setPetID(2L);
        Pet pet = new Pet();
        PetAdopter petAdopter = new PetAdopter();
        pet.setAdopted(true);
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.of(petAdopter));
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.of(pet));
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, never()).save(any());
        assertEquals("Pet already adopted", response.getMessage());
    }

    @Test
    void adoptPetRequestNotFound() {
        PetAdoptionRequestDto petAdoptionRequestDto = new PetAdoptionRequestDto();
        petAdoptionRequestDto.setPetAdopterID(2L);
        petAdoptionRequestDto.setPetID(2L);
        when(petAdopterRepository.findById(petAdoptionRequestDto.getPetAdopterID())).thenReturn(Optional.empty());
        when(petRepository.findById(petAdoptionRequestDto.getPetID())).thenReturn(Optional.empty());
        GenericResponse response = petAdopterService.adoptPetRequest(petAdoptionRequestDto);
        verify(petAdopterRepository, times(1)).findById(petAdoptionRequestDto.getPetAdopterID());
        verify(petRepository, times(1)).findById(petAdoptionRequestDto.getPetID());
        verify(adopterPetRequestsRepository, never()).save(any());
        assertEquals("PetAdopter or pet not found.", response.getMessage());
    }

    @Test
    public void requestExistsPetDoesNotExist() {
        long petID = 1L;
        long petAdopterID = 1L;
        given(petAdopterRepository.findById(petAdopterID)).willReturn(Optional.of(new PetAdopter()));
        given(petRepository.findById(petID)).willReturn(Optional.empty());
        boolean result = petAdopterService.requestExists(petID, petAdopterID);
        assertFalse(result);
    }

    @Test
    public void requestExistsPetAdopterDoesNotExist() {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterRepository.findById(petAdopterID)).willReturn(Optional.empty());
        given(petRepository.findById(petID)).willReturn(Optional.of(new Pet()));
        boolean result = petAdopterService.requestExists(petID, petAdopterID);
        assertFalse(result);
    }

    @Test
    public void requestExistsBothDoNotExist() {
        long petID = 2L;
        long petAdopterID = 2L;
        given(petAdopterRepository.findById(petAdopterID)).willReturn(Optional.empty());
        given(petRepository.findById(petID)).willReturn(Optional.empty());
        boolean result = petAdopterService.requestExists(petID, petAdopterID);
        assertFalse(result);
    }

}
