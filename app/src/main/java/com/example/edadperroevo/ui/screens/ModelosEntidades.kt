package com.example.edadperroevo.ui.screens

// 1. Entidad Usuario
data class UsuarioEntity(
    val id: String,
    val nombre: String,
    val correo: String,
    val tokenAutenticacion: String
)

// 2. Entidad Perro / Mascota
data class PerroEntity(
    val id: String,
    val usuarioId: String,
    val nombre: String,
    val raza: String,
    val edadCronologica: Int,
    val equivalenciaHumana: Int
)

// 3. Entidad Categoría (Exclusiva de alimentos caninos)
data class CategoriaEntity(
    val id: Int,
    val nombre: String, // Seco, Húmedo, Snacks
    val descripcion: String
)

// 4. Entidad Producto (Alimento canino)
data class ProductoEntity(
    val id: Int,
    val categoriaId: Int,
    val nombre: String,
    val marca: String,
    val precio: Double,
    val presentacionKg: Double,
    val descripcion: String
)

// 5. Entidad Inventario
data class InventarioEntity(
    val productoId: Int,
    val stockDisponible: Int,
    val alertaMinima: Int
)

// 6. Entidad Carrito de Compras (Ítem)
data class CarritoItemEntity(
    val producto: ProductoEntity,
    var cantidad: Int
)

// 7. Entidad Pedido / Orden de Compra
data class PedidoEntity(
    val id: String,
    val usuarioId: String,
    val direccionEntrega: String,
    val subtotal: Double,
    val costoEnvio: Double,
    val total: Double,
    val estado: String // Pendiente, Pagado, Despachado
)

// 8. Entidad Detalle de Pedido
data class DetallePedidoEntity(
    val pedidoId: String,
    val productoId: Int,
    val cantidad: Int,
    val precioUnitario: Double
)

// 9. Entidad Pago (Pasarela Sandbox)
data class PagoEntity(
    val transaccionId: String,
    val pedidoId: String,
    val pasarela: String, // Wompi / PayU
    val monto: Double,
    val estadoTransaccion: String // Aprobado, Rechazado
)

// 10. Entidad Conversación y Mensajes (IA y Comunidad)
data class MensajeConversacionEntity(
    val mensajeId: String,
    val canalId: String, // "IA" o "Comunidad"
    val emisor: String,
    val contenido: String,
    val esUrgenciaVeterinaria: Boolean,
    val fechaHora: String
)