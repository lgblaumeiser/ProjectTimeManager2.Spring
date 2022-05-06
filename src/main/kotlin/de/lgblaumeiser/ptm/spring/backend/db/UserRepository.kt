package de.lgblaumeiser.ptm.spring.backend.db

import de.lgblaumeiser.ptm.service.model.User
import de.lgblaumeiser.ptm.service.store.Store
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.*

@Service
class UserRepo(val dbRepo: DBUserRepo) : Store<User> {
    override fun retrieveAll(user: String): List<User> = listOfNotNull(toUser(dbRepo.findByUsername(user)))

    override fun retrieveById(id: Long) = throw NotImplementedError("Not needed for this type of object")

    override fun create(data: User) = toUser(dbRepo.save(toDaoUser(data)))!!

    override fun update(data: User) = toUser(dbRepo.save(toDaoUser(data)))!!

    override fun delete(id: Long) = dbRepo.deleteById(id)
}

@Repository
interface DBUserRepo : CrudRepository<DaoUser, Long> {
    fun findByUsername(username: String): DaoUser?
}

@Table(name = "user")
@Entity
open class DaoUser {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

    @Column(nullable = false, unique = true)
    open var username: String? = null

    @Column(nullable = false)
    open var password: String? = null

    @Column
    open var admin = false
}

private fun toDaoUser(user: User) = DaoUser().apply {
    username = user.username
    password = user.password
    id = user.id
    admin = user.admin
}

private fun toUser(daoUser: DaoUser?) = daoUser?.let { User(
    it.id!!,
    it.username!!,
    it.password!!,
    it.admin
)}
