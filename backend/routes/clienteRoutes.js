const express = require("express");
const router = express.Router();
const {
  guardarCliente,
  obtenerCliente,
  actualizarCliente,
  eliminarCliente
} = require("../controllers/clienteController");

router.post("/guardar", guardarCliente);
router.get("/obtener/:id", obtenerCliente);
router.put("/actualizar/:id", actualizarCliente);
router.delete("/eliminar/:id", eliminarCliente);

module.exports = router;