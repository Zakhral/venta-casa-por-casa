const Venta = require("../models/Venta");
const Cliente = require("../models/Cliente");
const Producto = require("../models/Producto");

const guardarVenta = async (req, res) => {
  try {
    const { cliente_id, producto_id, cantidad, fecha } = req.body;

    if (!cliente_id || !producto_id || !cantidad || !fecha) {
      return res.status(400).json({
        success: false,
        message: "Todos los campos de la venta son obligatorios"
      });
    }

    const cliente = await Cliente.findByPk(cliente_id);
    if (!cliente) {
      return res.status(404).json({
        success: false,
        message: "Cliente no encontrado"
      });
    }

    const producto = await Producto.findByPk(producto_id);
    if (!producto) {
      return res.status(404).json({
        success: false,
        message: "Producto no encontrado"
      });
    }

    if (producto.stock < cantidad) {
      return res.status(400).json({
        success: false,
        message: "Stock insuficiente para realizar la venta"
      });
    }

    const precio_unitario = producto.precio;
    const total = precio_unitario * cantidad;

    const nuevaVenta = await Venta.create({
      cliente_id,
      producto_id,
      cantidad,
      precio_unitario,
      total,
      fecha
    });

    producto.stock = producto.stock - cantidad;
    await producto.save();

    return res.status(201).json({
      success: true,
      message: "Venta registrada correctamente",
      data: nuevaVenta
    });

  } catch (error) {
    console.error("Error al guardar venta:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const obtenerVenta = async (req, res) => {
  try {
    const { id } = req.params;

    const venta = await Venta.findByPk(id);

    if (!venta) {
      return res.status(404).json({
        success: false,
        message: "Venta no encontrada"
      });
    }

    return res.status(200).json({
      success: true,
      message: "Venta obtenida correctamente",
      data: venta
    });

  } catch (error) {
    console.error("Error al obtener venta:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  guardarVenta,
  obtenerVenta
};