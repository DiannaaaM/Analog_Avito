package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAdDTO;
import ru.skypro.homework.model.AdEntity;
import ru.skypro.homework.repository.AdRepository;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    public AdEntity addNewAd(CreateOrUpdateAdDTO ad) {
        AdEntity newAd = new AdEntity();
        newAd.setPrice(ad.getPrice());
        newAd.setTitle(ad.getTitle());
        newAd.setDescription(ad.getDescription());
        newAd.setPhoto(newAd.getPhoto());
        return adRepository.save(newAd);
    }

    public AdEntity updateInfoAd(CreateOrUpdateAdDTO ad){
        adRepository.findByTitle(ad.getTitle());
        return adRepository.updateAd(ad);
    }

    public AdEntity deleteAd(long id){
        return adRepository.deleteById(id);
    }
}
