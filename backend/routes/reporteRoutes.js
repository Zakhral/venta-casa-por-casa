const express = require("express");
const router = express.Router();
const { obtenerVentasPorPeriodo } = require("../controllers/reporteController");

router.get("/ventas-periodo", obtenerVentasPorPeriodo);

module.exports = router;