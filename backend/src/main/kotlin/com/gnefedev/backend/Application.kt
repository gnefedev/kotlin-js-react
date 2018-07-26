package com.gnefedev.backend

import com.gnefedev.common.Car
import kotlinx.serialization.internal.StringSerializer
import kotlinx.serialization.json.JSON
import kotlinx.serialization.list
import kotlinx.serialization.serializer
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Repository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.*

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java)
}

@SpringBootApplication
class Application {
    @Bean
    fun serializer(): JSON = JSON()
}

@RestController
class CarsController(
    private val serializer: JSON,
    private val carsRepository: CarsRepository
) {
    @GetMapping("/api/cars")
    fun cars(
        @RequestParam("color", required = false) color: String?,
        @RequestParam("brand", required = false) brand: String?
    ): ResponseEntity<String> = ResponseEntity.ok(
        serializer.stringify(
            Car::class.serializer().list,
            carsRepository.getCars(color, brand)
        )
    )

    @GetMapping("/api/brands")
    fun brands(): ResponseEntity<String> = ResponseEntity.ok(
        serializer.stringify(
            StringSerializer.list, carsRepository.allBrands()
        )
    )

    @GetMapping("/api/colors")
    fun colors(): ResponseEntity<String> = ResponseEntity.ok(
        serializer.stringify(
            StringSerializer.list, carsRepository.allColors()
        )
    )

    @PostMapping("/add/car")
    fun addCar(@RequestBody carJson: String) {
        carsRepository.addCar(serializer.parse(carJson))
    }
}

@Repository
class CarsRepository {
    private var cars: List<Car> = emptyList()

    fun allBrands() = cars.map { it.brand }.distinct()
    fun allColors() = cars.map { it.color }.distinct()

    fun addCar(car: Car) {
        cars += car
    }

    fun getCars(color: String?, brand: String?): List<Car> {
        var result = cars
        if (!color.isNullOrBlank()) {
            result = result.filter { it.color == color }
        }
        if (!brand.isNullOrBlank()) {
            result = result.filter { it.brand == brand }
        }
        return result
    }

    init {
        val brands = listOf(
            "Acura", "Audi", "BMW", "Fiat", "Renault", "Mercedes", "Jaguar", "Honda", "Volvo"
        )
        val colors = listOf(
            "Orange", "Black", "Blue", "White", "Green", "Brown", "Red", "Silver", "Yellow"
        )
        val random = Random()
        cars = (0..100).map {
            Car(
                random.sample(brands),
                random.sample(colors),
                random.nextInt(50) + 2018 - 50
            )
        }
    }

    private fun Random.sample(brands: List<String>) =
        brands[nextInt(brands.size)]

}
