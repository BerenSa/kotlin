package com.example.examen

/**
 * Project: IntroBere
 * From: com.example.IntroBere
 * Created by: BereSn
 * On: 25/6/26
 * All rights reserved: 2026
 */

val participantes = HashMap<String, Int>()

fun main(){
    var opcion: Int
    do {
        mostrarMenu2()
        opcion = readln().toIntOrNull() ?: 0
        println()

        if (opcion == 1) {
            registrarParticipante()
        }
        else if (opcion == 2) {
            registrarPuntos()
        }
        else if (opcion == 3) {
            consultarParticipante()
        }
        else if (opcion == 4) {
            mostrarEstadisticas()
        }
        else if (opcion == 5) {
            mostrarParticipantesSobrePromedio()
        }
        else if (opcion == 6) {
            println("Finalizando programa...")
        }
        else {
            println("Opción no valida")
        }
        println()

    } while (opcion != 6)
}

fun mostrarMenu2() {
    println("""
        === MENU DEL TORNEO ===
        1. Registrar participante
        2. Registrar puntos
        3. Consultar participante
        4. Mostrar estadisticas del torneo
        5. Participantes sobre el promedio
        6. Finalizar programa
        Elige una opcion: 
    """.trimIndent())
}

fun registrarParticipante() {
    print("Escribe el nombre del participante: ")
    val nombre = readln().trim()

    if (nombre.isEmpty()) {
        println("No se permiten datos vacios")
        return
    }

    if (participantes.containsKey(nombre.lowercase())) {
        println("El participante $nombre ya esta registrado")
        return
    }

    participantes[nombre.lowercase()] = 0
    println("Participante $nombre registrado")
}

fun registrarPuntos() {
    if (participantes.isEmpty()) {
        println("No hay participantes registrados en el torneo.")
        return
    }

    print("Ingrese el nombre del participante: ")
    val nombre = readln().trim()

    if (!participantes.containsKey(nombre.lowercase())) {
        println("El participante $nombre no existe.")
        return
    }

    print("Ingrese los puntos de $nombre ")
    val puntos = readln().toIntOrNull()

    if (puntos == null || puntos <= 0) {
        println("Los puntos deben ser un numero mayor a cero")
        return
    }

    val puntosActuales = participantes[nombre.lowercase()] ?: 0
    participantes[nombre.lowercase()] = puntosActuales + puntos
    println("Se añadieron $puntos puntos a $nombre Total: ${puntosActuales + puntos}")
}

fun consultarParticipante(){
    if (participantes.isEmpty()) {
        println("No hay participantes registrados")
        return
    }

    print("Ingrese el nombre del participante ")
    val nombre = readln().trim()

    if (!participantes.containsKey(nombre.lowercase())) {
        println("El participante $nombre no se encuentra registrado ")
        return
    }

    val puntos = participantes[nombre.lowercase()] ?: 0
    val categoria = obtenerCategoria(puntos)

    println("""
        --- Informacion del Participante ---
        Nombre: ${nombre.replaceFirstChar { it.uppercase() }}
        Puntos Acumulados: $puntos
        Categoria: $categoria
    """.trimIndent())
}

fun mostrarEstadisticas(){
    if (participantes.isEmpty()) {
        println("El torneo no tiene participantes registrados ")
        return
    }

    val totalParticipantes = participantes.size
    var totalPuntos = 0

    var maxParticipante = ""
    var maxPuntos = Int.MIN_VALUE
    var minParticipante = ""
    var minPuntos = Int.MAX_VALUE
    var novatos = 0
    var competidores = 0
    var expertos = 0
    var leyendas = 0

    for ((nombre, puntos) in participantes) {
        totalPuntos += puntos

        if (puntos > maxPuntos) {
            maxPuntos = puntos
            maxParticipante = nombre
        }

        if (puntos < minPuntos) {
            minPuntos = puntos
            minParticipante = nombre
        }

        when (obtenerCategoria(puntos)) {
            "Novato" -> novatos++
            "Competidor" -> competidores++
            "Experto" -> expertos++
            "Leyenda" -> leyendas++
        }
    }

    val promedio = participantes.values.average()

    println("""
        
        =====================================
               ESTADÍSTICAS DEL TORNEO       
        =====================================
         Cantidad total de participantes: $totalParticipantes
         Total de puntos acumulados: $totalPuntos
         Promedio de puntos por participante: ${String.format("%.2f", promedio)}
         Mayor puntaje (Líder): ${maxParticipante.uppercase()} ($maxPuntos pts)
         Menor puntaje: ${minParticipante.uppercase()} ($minPuntos pts)
        
        --- Cantidad por Categorías ---
         Novatos: $novatos
         Competidores: $competidores
         Expertos: $expertos
         Leyendas: $leyendas
        =====================================
    """.trimIndent())
}

fun mostrarParticipantesSobrePromedio() {

    if (participantes.isEmpty()) {
        println("No hay participantes registrados")
        return
    }

    val promedio = participantes.values.average()

    println("Promedio general: ${"%.2f".format(promedio)}")
    println("Participantes con puntaje superior al promedio:")

    var encontrados = false

    for ((nombre, puntos) in participantes) {
        if (puntos > promedio) {
            println("${nombre.replaceFirstChar { it.uppercase() }} - $puntos puntos")
            encontrados = true
        }
    }

    if (!encontrados) {
        println("Ningún participante supera el promedio.")
    }
}

fun obtenerCategoria(puntos: Int): String {
    return when {
        puntos >= 1000 -> "Leyenda"
        puntos in 500..999 -> "Experto"
        puntos in 200..499 -> "Competidor"
        else -> "Novato"
    }
}