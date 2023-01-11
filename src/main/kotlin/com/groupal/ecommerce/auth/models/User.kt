package com.groupal.ecommerce.auth.models

import javax.persistence.*
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@Entity
@Table(
    name = "users",
    uniqueConstraints = [ UniqueConstraint(columnNames = ["user_name", "email"])]
)
class User{
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null
    @Column(name = "first_name")
    var firstname: @NotBlank @Size(max = 50) String? = null
    @Column(name = "last_name")
    var lastname: @NotBlank @Size(max = 50) String? = null
    @Column(name = "user_name")
    var username: @NotBlank @Size(max = 50) String? = null
    var email: @NotBlank @Size(max = 50) String? = null
    var password: @NotBlank @Size(max = 20) String? = null
    var gender: @NotBlank @Size(max = 20) String? = null
    @Column(name = "birth_date")
    var birthDate: @NotBlank @Size(max = 20) String? = null
    var phone: @NotBlank @Size(max = 20) String? = null
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_roles",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "role_id")]
    )
    var roles: Set<Role> = HashSet()

    constructor() {}
    constructor(firstname: String?, lastname: String?, username: String?, email: String?, password: String?, gender: String?, birthDate: String?, phone: String?) : super() {
        this.firstname = firstname
        this.lastname = lastname
        this.username = username
        this.email = email
        this.password = password
        this.gender = gender
        this.birthDate = birthDate
        this.phone = phone
    }
}
