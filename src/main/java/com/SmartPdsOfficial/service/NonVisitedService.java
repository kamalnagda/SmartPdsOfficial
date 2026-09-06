package com.SmartPdsOfficial.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.SmartPdsOfficial.model.Beneficiary;

@Service
public class NonVisitedService {
    public List<Beneficiary> getNonVisitedBeneficiaries(
            List<Beneficiary> allBeneficiaries,
            List<String> visitedRcNumbers) {

        List<Beneficiary> nonVisited = new ArrayList<>();

        for (Beneficiary beneficiary : allBeneficiaries) {

            if (!visitedRcNumbers.contains(beneficiary.getFamilyId())) {
                nonVisited.add(beneficiary);
            }
        }

        return nonVisited;
    }
}
