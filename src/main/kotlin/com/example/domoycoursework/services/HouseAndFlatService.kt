package com.example.domoycoursework.services

import com.example.domoycoursework.exceptions.InvalidUserDataException
import com.example.domoycoursework.exceptions.NotFoundException
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
            ?.let { throw InvalidUserDataException("House already exist in data base") } ?: houseRepository.save(address
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
                    houseId = it.id,
                    flatNumber = verificationRequest.flatNumber,
                    cadastralNumber = verificationRequest.cadastralNumber,
                    //TODO: change to real user id
                    ownerId = 1
                )
            )
        } ?: throw InvalidUserDataException("Address not found")
    }

    fun getFlatsByHouse(id: Int): List<Int> {
        return houseRepository.findFlatsByHouseId(id)
    }

    fun getHouseById(id: Int): House {
        return houseRepository.findHouseById(id) ?: throw NotFoundException("House not found")
    }
}