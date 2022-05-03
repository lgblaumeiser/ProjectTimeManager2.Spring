package de.lgblaumeiser.ptm.spring.backend.rest

import de.lgblaumeiser.ptm.service.UserService
import org.springframework.stereotype.Service

@Service
class UserServiceWrapper (val userService: UserService) : UserService() {

}
