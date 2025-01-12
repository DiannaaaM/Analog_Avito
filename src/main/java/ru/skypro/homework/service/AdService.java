package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.AdDTO;
import ru.skypro.homework.mapper.EntityMapper;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.repository.AdRepository;

import java.util.List;


@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;
    @Autowired
    private EntityMapper mapper;


    public AdDTO getAd(Long id) {
        AdEntity adEntity = adRepository.findById(id);
        return mapper.adEntityToAdDTO(adEntity);
    }

    public long createAd(AdDTO adDTO) {
        AdEntity adEntity = mapper.adDTOToAdEntity(adDTO);
        AdEntity savedAd = adRepository.save(adEntity);
        mapper.adEntityToAdDTO(savedAd);
        return savedAd.getId();
    }

    public AdDTO updateInfoAd(long id, AdDTO ad) {
        AdEntity existingAd = adRepository.findById(id);

        existingAd.setTitle(ad.getTitle());
        existingAd.setDescription(ad.getDescription());
        existingAd.setPrice(ad.getPrice());
        existingAd.setPhoto(ad.getPhoto());
        existingAd.setComments(ad.getComments());

        AdEntity updatedAd = adRepository.save(existingAd);
        return mapper.adEntityToAdDTO(updatedAd);
    }

    public void deleteAd(Long id) {
        if (!adRepository.existsById(id)) {
            throw new RuntimeException("Ad not found with id: " + id);
        }
        adRepository.deleteById(id);
    }
    public List<AdEntity> findAllAds(){
        return adRepository.findAll();
    }
    public List<AdEntity> findAdsOfUser(long id){
        return adRepository.findByOwnerId(id);
    }
}
