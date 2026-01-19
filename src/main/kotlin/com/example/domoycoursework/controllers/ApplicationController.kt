//package com.example.domoycoursework.controllers
//
//import com.example.domoycoursework.dto.ApplicationResponseDto
//import com.example.domoycoursework.dto.ApplicationResponsesHistoryDto
//import com.example.domoycoursework.models.Ticket
//import com.example.domoycoursework.models.TicketComment
//import com.example.domoycoursework.services.ApplicationService
//import org.springframework.web.bind.annotation.CrossOrigin
//import org.springframework.web.bind.annotation.GetMapping
//import org.springframework.web.bind.annotation.PathVariable
//import org.springframework.web.bind.annotation.PostMapping
//import org.springframework.web.bind.annotation.RequestBody
//import org.springframework.web.bind.annotation.RequestHeader
//import org.springframework.web.bind.annotation.RequestMapping
//import org.springframework.web.bind.annotation.RequestPart
//import org.springframework.web.bind.annotation.RestController
//import org.springframework.web.multipart.MultipartFile
//
//@RestController
//@RequestMapping("/application")
//@CrossOrigin(origins = ["*"])
//class ApplicationController(
//    private var applicationService: ApplicationService
//) {
//
//    @PostMapping("/create")
//    fun createApplication(
//        @RequestHeader("Authorization") token: String,
//        @RequestPart("applicationDto") applicationDto: String,
//        @RequestPart("files") files: List<MultipartFile>?
//    ): Ticket {
//        return applicationService.createApplication(
//            applicationService.convertRequestToDto(applicationDto),
//            token,
//            files
//        )
//    }
//
//    @PostMapping("/admin/response/{id}")
//    fun createResponse(
//        @RequestHeader("Authorization") token: String,
//        @RequestBody responseDto: ApplicationResponseDto,
//        @PathVariable("id") id: Int
//    ): ApplicationResponsesHistoryDto {
//        return applicationService.createResponse(responseDto, token, id)
//    }
//
//    @GetMapping("/{id}")
//    fun getResponse(
//        @PathVariable("id") id: Int,
//        @RequestHeader("Authorization") token: String
//    ): Ticket {
//        return applicationService.getApplication(id, token)
//    }
//
//    @GetMapping("/all")
//    fun getAllApplications(): List<Ticket> {
//        return applicationService.getAllApplications()
//    }
//
//    @GetMapping("/{id}/responses")
//    fun getResponses(
//        @PathVariable("id") id: Int,
//        @RequestHeader("Authorization") token: String
//    ): List<TicketComment> {
//        return applicationService.getResponses(id, token)
//    }
//
//    @GetMapping("/al-by-user")
//    fun getAllApplicationsByUser(
//        @RequestHeader("Authorization") token: String
//    ): List<Ticket> {
//        return applicationService.getAllApplicationsByUser(token)
//    }
//
//    @GetMapping("/image/{filename}")
//    fun getImage(@PathVariable filename: String): String {
//        return applicationService.getImageByFilename(filename)
//    }
//
//}