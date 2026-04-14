const Producto = require("../models/Producto");

const guardarProducto = async (req, res) => {
  try {
    const { nombre, descripcion, precio, stock } = req.body;

    if (!nombre || !descripcion || precio === undefined || stock === undefined) {
      return res.status(400).json({
        success: false,
        message: "Todos los campos del producto son obligatorios"
      });
    }

    const nuevoProducto = await Producto.create({
      nombre,
      descripcion,
      precio,
      stock
    });

    return res.status(201).json({
      success: true,
      message: "Producto registrado correctamente",
      data: nuevoProducto
    });
  } catch (error) {
    console.error("Error al guardar producto:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const obtenerProducto = async (req, res) => {
  try {
    const { id } = req.params;

    const producto = await Producto.findByPk(id);

    if (!producto) {
      return res.status(404).json({
        success: false,
        message: "Producto no encontrado"
      });
    }

    return res.status(200).json({
      success: true,
      message: "Producto obtenido correctamente",
      data: producto
    });
  } catch (error) {
    console.error("Error al obtener producto:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const actualizarProducto = async (req, res) => {
  try {
    const { id } = req.params;
    const { nombre, descripcion, precio, stock } = req.body;

    const producto = await Producto.findByPk(id);

    if (!producto) {
      return res.status(404).json({
        success: false,
        message: "Producto no encontrado"
      });
    }

    producto.nombre = nombre;
    producto.descripcion = descripcion;
    producto.precio = precio;
    producto.stock = stock;

    await producto.save();

    return res.status(200).json({
      success: true,
      message: "Producto actualizado correctamente",
      data: producto
    });
  } catch (error) {
    console.error("Error al actualizar producto:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const eliminarProducto = async (req, res) => {
  try {
    const { id } = req.params;

    const producto = await Producto.findByPk(id);

    if (!producto) {
      return res.status(404).json({
        success: false,
        message: "Producto no encontrado"
      });
    }

    await producto.destroy();

    return res.status(200).json({
      success: true,
      message: "Producto eliminado correctamente"
    });
  } catch (error) {
    console.error("Error al eliminar producto:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  guardarProducto,
  obtenerProducto,
  actualizarProducto,
  eliminarProducto
};