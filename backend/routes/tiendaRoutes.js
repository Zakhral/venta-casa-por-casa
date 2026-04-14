const express = require("express");
const router = express.Router();
const {
  guardarTienda,
  obtenerTienda,
  actualizarTienda
} = require("../controllers/tiendaController");

router.post("/guardar", guardarTienda);
router.get("/obtener", obtenerTienda);
router.put("/actualizar", actualizarTienda);

module.exports = router;