// SPDX-FileCopyrightText: 2022 Lars Geyer-Blaumeiser <lars@lgblaumeiser.de>
// SPDX-License-Identifier: MIT
package de.lgblaumeiser.ptm.spring.backend.db

import de.lgblaumeiser.ptm.service.model.Booking
import de.lgblaumeiser.ptm.service.store.BookingStore
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.time.LocalDate
import java.time.LocalTime
import javax.persistence.*

@Service
class BookingRepo(val dbRepo: DBBookingRepo) : BookingStore {
    override fun retrieveAll(user: String): List<Booking> = dbRepo.findByUsername(user).mapNotNull{ toBooking(it) }

    override fun retrieveById(id: Long): Booking  =
        toBooking(dbRepo.findByIdOrNull(id)) ?: throw IllegalArgumentException("Booking not found")

    override fun retrieveByBookingDays(user: String, days: List<LocalDate>): List<Booking> = dbRepo.findByDate(user, days).mapNotNull{ toBooking(it) }

    override fun create(data: Booking): Booking = toBooking(dbRepo.save(toDaoBooking(data)))!!

    override fun update(data: Booking) = toBooking(dbRepo.save(toDaoBooking(data)))!!

    override fun delete(id: Long) = dbRepo.deleteById(id)
}

@Repository
interface DBBookingRepo : CrudRepository<DaoBooking, Long> {
    fun findByUsername(username: String): List<DaoBooking>

    fun findByDate(username: String, dates: List<LocalDate>): List<DaoBooking>
}

@Table(name = "booking")
@Entity
open class DaoBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open var user: String? = null

    @Column(nullable = false)
    open var bookingday: LocalDate? = null

    @Column(nullable = false)
    open var starttime: LocalTime? = null

    @Column
    open var endtime: LocalTime? = null

    @Column(nullable = false)
    open var comment: String? = null

    @Column(nullable = false)
    open var activity: Long? = null
}

private fun toDaoBooking(booking: Booking) = DaoBooking().apply {
    id = booking.id
    bookingday= booking.bookingday
    starttime = booking.starttime
    endtime = booking.endtime
    comment = booking.comment
    user = booking.user
    activity = booking.activity
}

private fun toBooking(daoBooking: DaoBooking?) = daoBooking?.let {
    Booking(
        daoBooking.id!!,
        daoBooking.bookingday!!,
        daoBooking.starttime!!,
        daoBooking.endtime,
        daoBooking.activity!!,
        daoBooking.user!!,
        daoBooking.comment!!
    )
}