package com.example.domoycoursework.services

import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import com.example.domoycoursework.models.VerificationRequest
import com.example.domoycoursework.repos.FlatRepository
import com.example.domoycoursework.repos.HouseRepository
import org.springframework.stereotype.Service

@Service
class HouseAndFlatService(
    private var flatRepository: FlatRepository,
    private var houseRepository: HouseRepository
) {

    fun createHouse(address: String): House {
        return houseRepository.findHouseByAddress(address)
            ?.let { throw InvalidUserDataException("House already exist in data base") } ?: houseRepository.save(
            House(
                id = 0,
                address = address
            )
        )
    }

    fun createFlat(verificationRequest: VerificationRequest): Flat {
        return houseRepository.findHouseByAddress(verificationRequest.address)?.let {
            flatRepository.findFlatByHouseAndFlatNumber(
                it,
                verificationRequest.flatNumber
            )?.let { throw InvalidUserDataException("Flat already owned") } ?: flatRepository.save(
                Flat(
                    id = 0,
                    house = it,
                    flatNumber = verificationRequest.flatNumber,
                    cadastralNumber = verificationRequest.cadastralNumber,
                    owner = verificationRequest.user
                )
            )
        } ?: throw InvalidUserDataException("Address not found")
    }

    fun getFlatsByHouse(id:Long): List<Int> {
        return houseRepository.findHouseById(id)?.flats?.map { it.flatNumber } ?: throw InvalidUserDataException("House not found")
    }
}