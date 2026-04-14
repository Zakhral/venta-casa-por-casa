const express = require("express");
const router = express.Router();
const {
  guardarVenta,
  obtenerVenta
} = require("../controllers/ventaController");

router.post("/guardar", guardarVenta);
router.get("/obtener/:id", obtenerVenta);

module.exports = router;