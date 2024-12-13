package com.FitMeet.service;

import com.FitMeet.dto.CoacheDto;
import com.FitMeet.model.Coache;

import java.util.List;

public interface CoacheService {
    List<Coache> findAll();

    Coache findById(Long id);

    void deteleById(Long id);

    Coache save(Coache attachment);

    Coache update(Coache attachment);
    Coache logincoach(CoacheDto coache);
}
