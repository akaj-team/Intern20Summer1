package com.asiantech.intern20summer1.week12.data.source.datasource

interface LocalDataSource {
    fun putToken(token: String)
    fun getToken(): String
    fun putIdUser(idUser: Int)
    fun getIdUser(): Int
    fun putIsLogin(isLogin: Boolean)
    fun getIsLogin(): Boolean
}
