package com.productions.ppt.commercebackend.app.banner;

import com.productions.ppt.commercebackend.app.user.models.UserEntity;
import com.productions.ppt.commercebackend.app.user.services.UserService;
import com.productions.ppt.commercebackend.exceptions.BusinessErrorException;
import com.productions.ppt.commercebackend.exceptions.EntityNotFoundInDBException;
import com.sun.org.apache.bcel.internal.generic.ACONST_NULL;
import com.sun.org.apache.bcel.internal.generic.DCONST;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class BannerController {

    BannerRepository bannerRepository;
    UserService userService;

    public BannerController(BannerRepository bannerRepository, UserService userService) {
        this.bannerRepository = bannerRepository;
        this.userService = userService;
    }

    @CrossOrigin()
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
    @CrossOrigin()
    @PostMapping("/banner/add")
    void createBanner(@RequestBody BannerEntity bannerEntity){
        this.bannerRepository.save(bannerEntity);
    }


    @CrossOrigin()
    @DeleteMapping("/banner/{ID}")
    void deleteBanner(@PathVariable Integer ID){

//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
//        UserEntity userEntity =
//                userService
//                        .findByEmail(userDetails.getUsername())
//                        .<EntityNotFoundInDBException>orElseThrow(
//                                () -> {
//                                    throw new EntityNotFoundInDBException("User not found");
//                                });
//
//        System.out.println(userEntity);

        BannerEntity bannerEntity = this.bannerRepository.findById(ID).<BusinessErrorException>orElseThrow(
                () -> {
                    throw new BusinessErrorException("Banner ID not available.");
                }
        );

        this.bannerRepository.delete(bannerEntity);

    }

}
