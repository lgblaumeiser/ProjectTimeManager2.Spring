// SPDX-FileCopyrightText: 2022 Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
// SPDX-License-Identifier: MIT
package de.lgblaumeiser.ptm.spring.backend.rest

import de.lgblaumeiser.ptm.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserService) : UsersApi {
    override fun authenticateUser(authenticationData: AuthenticationData): ResponseEntity<String> {
        return super.authenticateUser(authenticationData)
    }

    override fun changePassword(authenticationData: AuthenticationData): ResponseEntity<Unit> {
        return super.changePassword(authenticationData)
    }

    override fun deleteUser(): ResponseEntity<Unit> {
        return super.deleteUser()
    }

    override fun registerUser(authenticationData: AuthenticationData): ResponseEntity<Unit> {
        return super.registerUser(authenticationData)
    }
}