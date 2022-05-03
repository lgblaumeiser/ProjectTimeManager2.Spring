package de.lgblaumeiser.ptm.spring.backend.rest

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(val userService: UserServiceWrapper): UsersApi {
    override fun getUser(): ResponseEntity<User> {
        return super.getUser()
    }

    override fun authenticateUser(authenticationData: AuthenticationData): ResponseEntity<String> {
        return super.authenticateUser(authenticationData)
    }

    override fun changeUser(changeUserData: ChangeUserData): ResponseEntity<Unit> {
        return super.changeUser(changeUserData)
    }

    override fun deleteUser(): ResponseEntity<Unit> {
        return super.deleteUser()
    }

    override fun registerUser(newUserData: NewUserData): ResponseEntity<Unit> {
        return super.registerUser(newUserData)
    }

    override fun resetPassword(authenticationData: AuthenticationData): ResponseEntity<String> {
        return super.resetPassword(authenticationData)
    }
}