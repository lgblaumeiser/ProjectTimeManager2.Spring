// SPDX-FileCopyrightText: 2022 Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
// SPDX-License-Identifier: MIT
package de.lgblaumeiser.ptm.spring.backend.rest

import de.lgblaumeiser.ptm.service.ActivityService
import de.lgblaumeiser.ptm.service.BookingService
import de.lgblaumeiser.ptm.service.UserService
import de.lgblaumeiser.ptm.spring.backend.db.ActivityRepo
import de.lgblaumeiser.ptm.spring.backend.db.BookingRepo
import de.lgblaumeiser.ptm.spring.backend.db.UserRepo
import org.springframework.stereotype.Service

@Service
class ActivityServiceWrapper(store: ActivityRepo) : ActivityService(store)

@Service
class BookingServiceWrapper(store: BookingRepo) : BookingService(store)

@Service
class UserServiceWrapper(store: UserRepo, activityService: ActivityService, bookingService: BookingService) : UserService(store, activityService, bookingService)