package com.groupal.ecommerce.auth.models

import java.time.Instant
import javax.persistence.*

@Entity(name = "refreshtoken")
class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    var id: Long = 0

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    var user: User? = null

    @Column(nullable = false, unique = true)
    var token: String? = null

    @Column(nullable = false)
    var expiryDate: Instant? = null

    constructor() {}
    constructor(user: User?, token: String?, expiryDate: Instant?) : super() {
        this.user = user
        this.token = token
        this.expiryDate = expiryDate
    }

}