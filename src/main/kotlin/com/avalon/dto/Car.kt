package com.avalon.dto

class Car {

    var brand: String? = null
    var model: String? = null
    var edition: String? = null
    var year: Int? = null

    var exteriorColor: String? = null
    var exteriorColorStr: String? = null

    var transmission: String? = null

    // engine
    var fuelType: String? = null
    var cylinders: Int? = null
    var litres: String? = null
    var litresStr: String? = null

    var layout: String? = null

    var doors: Int? = null
    var passengers: Int? = null
    var type: String? = null
    var interiorColor: String? = null
    var interiorColorStr: String? = null

    override fun toString(): String {
        return "Car(brand=$brand, model=$model, edition=$edition, year=$year, exteriorColor=$exteriorColor, exteriorColorStr=$exteriorColorStr, transmission=$transmission, fuelType=$fuelType, cylinders=$cylinders, litres=$litres, litresStr=$litresStr, layout=$layout, doors=$doors, passengers=$passengers, type=$type, interiorColor=$interiorColor, interiorColorStr=$interiorColorStr)"
    }

}