package info.adavis.sampleapp.models

import io.realm.RealmObject

open class Ward : RealmObject() {

    open var name: String? = null

    override fun toString(): String {
        return "Ward(name=$name)"
    }

}

// todo: uncomment to test out the migration issue
//open class UserWard : RealmObject() {
//
//    open var ward: Ward? = null
//
//    open var agentId: Long? = null
//
//    override fun toString(): String {
//        return "UserWard(ward=$ward, agentId=$agentId)"
//    }
//
//}
