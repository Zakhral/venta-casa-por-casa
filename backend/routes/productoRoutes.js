const express = require("express");
const router = express.Router();
const {
  guardarProducto,
  obtenerProducto,
  actualizarProducto,
  eliminarProducto
} = require("../controllers/productoController");

router.post("/guardar", guardarProducto);
router.get("/obtener/:id", obtenerProducto);
router.put("/actualizar/:id", actualizarProducto);
router.delete("/eliminar/:id", eliminarProducto);

module.exports = router;