package ru.skypro.homework.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.skypro.homework.dto.CreateOrUpdateAd;
import ru.skypro.homework.model.Ad;
import ru.skypro.homework.repository.AdRepository;

@Service
public class AdService {
    @Autowired
    private AdRepository adRepository;

    public Ad addNewAd(CreateOrUpdateAd ad) {
        Ad newAd = new Ad();
        newAd.setPrice(ad.getPrice());
        newAd.setTitle(ad.getTitle());
        newAd.setDescription(ad.getDescription());
        newAd.setPhoto(newAd.getPhoto());
        return adRepository.save(newAd);
    }

    public Ad updateInfoAd(CreateOrUpdateAd ad){
        adRepository.findByTitle(ad.getTitle());
        return adRepository.updateAd(ad);
    }

    public Ad deleteAd(long id){
        return adRepository.deleteById(id);
    }
}
