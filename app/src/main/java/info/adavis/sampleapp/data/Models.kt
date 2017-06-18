package info.adavis.sampleapp.data

data class User(
        val id: Long,
        val name: String,
        val username: String,
        val email: String,
        val address: Address
)

data class Address(
        val street: String,
        val suite: String,
        val city: String,
        val zipcode: String
)

data class Post(
        val id: Long,
        val userId: Long,
        val title: String,
        val body: String
)

