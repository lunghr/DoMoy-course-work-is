package com.example.domoycoursework.services

import com.example.domoycoursework.dto.HouseDto
import com.example.domoycoursework.dto.ResidentialComplexDto
import com.example.domoycoursework.models.Address
import com.example.domoycoursework.models.Flat
import com.example.domoycoursework.models.House
import com.example.domoycoursework.models.ResidentialComplex
import com.example.domoycoursework.repos.FlatRepository
import com.example.domoycoursework.repos.HouseRepository
import com.example.domoycoursework.repos.ResidentialComplexRepository
import com.example.domoycoursework.repos.UserRepository
import jakarta.persistence.EntityNotFoundException
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
@Transactional
class ResidentialComplexService(
    private val houseRepository: HouseRepository,
    private val flatRepository: FlatRepository,
    private val userRepository: UserRepository
) {

    @Transactional
    fun createHouse(houseDto: HouseDto): House {
        val house = House(
            address = Address(
                street = houseDto.street,
                city = houseDto.city,
                zipCode = houseDto.zipCode,
                houseNumber = houseDto.houseNumber
            ),
            totalFloors = houseDto.totalFloors
        )
        return houseRepository.save(house)
    }

    fun getHouseById(houseId: Long): House =
        houseRepository.findById(houseId)
            ?: throw EntityNotFoundException("House with id $houseId not found")

    @Transactional
    fun updateHouseAddress(houseId: Long, newAddress: Address): House {
        val house = getHouseById(houseId)
        house.address = newAddress
        return houseRepository.save(house)
    }


    @Transactional
    fun addFlatToHouse(
        houseId: Long,
        flatNumber: Int,
        floor: Int,
        cadastralNumber: String? = null
    ): Flat {
        val house = getHouseById(houseId)

        if (flatRepository.existsByHouseAndFlatNumber(house, flatNumber)) {
            throw IllegalArgumentException("Flat number $flatNumber already exists in this house")
        }

        val flat = Flat(
            flatNumber = flatNumber,
            floor = floor,
            cadastralNumber = cadastralNumber?.trim(),
            house = house
        )

        house.flats.add(flat)
        return flatRepository.save(flat)
    }

    fun getFlatsByHouse(houseId: Long): List<Flat> =
        flatRepository.findByHouseId(houseId)

    fun getFlatById(flatId: Long): Flat =
        flatRepository.findById(flatId)
            .orElseThrow { EntityNotFoundException("Квартира с id $flatId не найдена") }

    @Transactional
    fun addOwnerToFlat(flatId: Long, userId: Long): Flat {
        val flat = getFlatById(flatId)
        val user = userRepository.findById(userId)
            .orElseThrow { EntityNotFoundException("Пользователь с id $userId не найден") }

        if (flat.owners.any { it.id == user.id }) {
            throw IllegalArgumentException("Пользователь уже является собственником этой квартиры")
        }

        flat.owners.add(user)
        return flatRepository.save(flat)
    }

    @Transactional
    fun removeOwnerFromFlat(flatId: Long, userId: Long): Flat {
        val flat = getFlatById(flatId)
        val removed = flat.owners.removeIf { it.id == userId }

        if (!removed) {
            throw IllegalArgumentException("Пользователь не является собственником этой квартиры")
        }

        return flatRepository.save(flat)
    }

    fun findFlatsByUser(userId: Long): List<Flat> =
        flatRepository.findByOwnersId(userId)

    fun findComplexByFlatCadastralNumber(cadastral: String): ResidentialComplex? =
        flatRepository.findByCadastralNumber(cadastral.trim())
            .house.complex
}