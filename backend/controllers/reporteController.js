const Venta = require("../models/Venta");

const obtenerVentasPorPeriodo = async (req, res) => {
  try {
    const { inicio, fin } = req.query;

    if (!inicio || !fin) {
      return res.status(400).json({
        success: false,
        message: "Debes enviar fecha de inicio y fecha de fin"
      });
    }

    const ventas = await Venta.findAll();

    const ventasFiltradas = ventas.filter((venta) => {
      return venta.fecha >= inicio && venta.fecha <= fin;
    });

    const totalVendido = ventasFiltradas.reduce((acumulado, venta) => {
      return acumulado + parseFloat(venta.total);
    }, 0);

    return res.status(200).json({
      success: true,
      message: "Reporte generado correctamente",
      data: {
        cantidad_ventas: ventasFiltradas.length,
        total_vendido: totalVendido,
        ventas: ventasFiltradas
      }
    });

  } catch (error) {
    console.error("Error al generar reporte:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  obtenerVentasPorPeriodo
};