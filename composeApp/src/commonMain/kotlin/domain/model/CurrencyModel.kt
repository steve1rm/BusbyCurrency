package domain.model


import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import kotlinx.serialization.Serializable
import org.mongodb.kbson.ObjectId

@Serializable
open class CurrencyModel : RealmObject {
    @PrimaryKey
    var _id: ObjectId = ObjectId()
    /** THB, GBP, USD, etc */
    var code: String = ""
    /** Current rate of the currency */
    var value: Double = 0.0

    /** Workaround as there is an issue with realm and */
    companion object
}