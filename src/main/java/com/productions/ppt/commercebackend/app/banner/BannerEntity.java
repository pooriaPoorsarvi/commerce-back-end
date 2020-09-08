package com.productions.ppt.commercebackend.app.banner;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
public class BannerEntity {

  @JsonProperty
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  Integer ID;

  @JsonProperty
  @Size(min = 1, max = 7000)
  String imageSrc;

  @JsonProperty
  @Size(min = 1, max = 7000)
  String url;
}
