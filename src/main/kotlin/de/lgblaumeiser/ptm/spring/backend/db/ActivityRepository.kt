package de.lgblaumeiser.ptm.spring.backend.db

import de.lgblaumeiser.ptm.service.model.Activity
import de.lgblaumeiser.ptm.service.store.Store
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import javax.persistence.*

@Service
class ActivityRepo(val dbRepo: DBActivityRepo) : Store<Activity> {
    override fun retrieveAll(user: String): List<Activity> = dbRepo.findByUsername(user).mapNotNull{ toActivity(it) }

    override fun retrieveById(id: Long) : Activity =
        toActivity(dbRepo.findByIdOrNull(id)) ?: throw IllegalArgumentException("Activity not found")

    override fun create(data: Activity): Activity = toActivity(dbRepo.save(toDaoActivity(data)))!!

    override fun update(data: Activity): Activity = toActivity(dbRepo.save(toDaoActivity(data)))!!

    override fun delete(id: Long) = dbRepo.deleteById(id)
}

@Repository
interface DBActivityRepo : CrudRepository<DaoActivity, Long> {
    fun findByUsername(user: String): List<DaoActivity>
}

@Table(name = "activity")
@Entity
open class DaoActivity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    open var id: Long? = null

    @Column(nullable = false)
    open var user: String? = null

    @Column(nullable = false)
    open var projectid: String? = null

    @Column(nullable = false)
    open var projectname: String? = null

    @Column(nullable = false)
    open var activityid: String? = null

    @Column(nullable = false)
    open var activityname: String? = null

    @Column
    open var hidden: Boolean = false
}

private fun toDaoActivity(activity: Activity) = DaoActivity().apply {
    id = activity.id
    user = activity.user
    projectid = activity.projectid
    projectname = activity.projectname
    activityid = activity.activityid
    activityname = activity.activityname
    hidden = activity.hidden
}

private fun toActivity(daoActivity: DaoActivity?) = daoActivity?.let {
    Activity(
        daoActivity.id!!,
        daoActivity.user!!,
        daoActivity.projectname!!,
        daoActivity.projectid!!,
        daoActivity.activityname!!,
        daoActivity.activityid!!,
        daoActivity.hidden)
}