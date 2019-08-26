package com.example.data.mapper

interface DataMapper<E, D> {
    fun mapFromEntity(type: E): D
    fun mapToEntity(type: D): E
}