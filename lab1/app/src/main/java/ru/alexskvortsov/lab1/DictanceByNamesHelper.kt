package ru.alexskvortsov.lab1

import android.content.Context
import android.location.Geocoder
import android.location.Location
import java.util.*

fun getDistanceBetweenCitiesInMeters(
    context: Context,
    firstCityName: String,
    secondCityName: String
): Int? {
    val firstLocation = getCoordinatesOfCityByName(context, firstCityName) ?: return null
    val secondLocation = getCoordinatesOfCityByName(context, secondCityName) ?: return null
    return firstLocation.distanceTo(secondLocation).toInt()
}

private fun getCoordinatesOfCityByName(context: Context, cityName: String): Location? {
    if (!Geocoder.isPresent()) return null

    val geocoder = Geocoder(context, Locale.getDefault())
    val address = geocoder.getFromLocationName(cityName, 3)
        .firstOrNull() ?: return null

    return Location("").apply {
        latitude = address.latitude
        longitude = address.longitude
    }
}