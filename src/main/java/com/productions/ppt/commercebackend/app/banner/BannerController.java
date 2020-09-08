package com.productions.ppt.commercebackend.app.banner;

import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BannerController {

    BannerRepository bannerRepository;

    public BannerController(BannerRepository bannerRepository) {
        this.bannerRepository = bannerRepository;
    }

    @GetMapping("/banner/{ID}")
    BannerEntity getBannerById(@PathVariable Integer ID){
        return this.bannerRepository.findById(ID).<BusinessErrorException>orElseThrow(
                () -> {
                    throw new BusinessErrorException("Banner ID not available.");
                }
        );
    }

    @CrossOrigin()
    @GetMapping("/banner/all")
    List<BannerEntity> getAllBanners(){
        return this.bannerRepository.findAll();
    }

//  TODO turn all these type of void returns into response entities by returning the resulting address
    @PostMapping("/banner/add")
    void createBanner(@RequestBody BannerEntity bannerEntity){
        this.bannerRepository.save(bannerEntity);
    }


}
