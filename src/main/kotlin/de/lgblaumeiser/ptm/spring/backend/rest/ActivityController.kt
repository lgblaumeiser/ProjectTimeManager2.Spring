package de.lgblaumeiser.ptm.spring.backend.rest

import de.lgblaumeiser.ptm.service.ActivityService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.RestController

@RestController
class ActivityController(val activityService: ActivityService) : ActivitiesApi {
    override fun addActivity(newActivityData: NewActivityData): ResponseEntity<Activity> =
        ResponseEntity(
            toRestActivity(
                activityService.addActivity(
                    user(),
                    newActivityData.projectName,
                    newActivityData.projectId,
                    newActivityData.activityName,
                    newActivityData.activityId
                )
            ), HttpStatus.CREATED
        )

    override fun changeActivity(activityId: Long, changeActivityData: ChangeActivityData): ResponseEntity<Activity> =
        ResponseEntity(
            toRestActivity(
                activityService.changeActivity(
                    user(),
                    changeActivityData.projectName,
                    changeActivityData.projectId,
                    changeActivityData.activityName,
                    changeActivityData.activityId,
                    changeActivityData.hidden,
                    activityId
                )
            ), HttpStatus.OK
        )

    override fun getActivities(hidden: Boolean?): ResponseEntity<List<Activity>> {
        val activities = activityService.getActivities(user(), hidden ?: false).map { toRestActivity(it) }
        return if (activities.isNotEmpty())
            ResponseEntity(activities, HttpStatus.OK)
        else
            ResponseEntity(HttpStatus.NO_CONTENT)
    }

    override fun getActivity(activityId: Long): ResponseEntity<Activity> =
        try {
            ResponseEntity(toRestActivity(activityService.getActivityById(user(), activityId)), HttpStatus.OK)
        } catch (ex: Exception) {
            when (ex) {
                is IllegalArgumentException, is IllegalAccessException -> ResponseEntity(HttpStatus.NO_CONTENT)
                else -> throw ex
            }
        }
}

fun user(): String = SecurityContextHolder.getContext().authentication.name.takeIf { it.isNotBlank() }!!

fun toRestActivity(activity: de.lgblaumeiser.ptm.service.model.Activity) = Activity(
    activity.id,
    activity.projectname,
    activity.activityname,
    activity.projectid,
    activity.activityid,
    activity.hidden
)