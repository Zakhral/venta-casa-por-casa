const Cliente = require("../models/Cliente");

const guardarCliente = async (req, res) => {
  try {
    const { nombre, telefono, direccion } = req.body;

    if (!nombre || !telefono || !direccion) {
      return res.status(400).json({
        success: false,
        message: "Todos los campos del cliente son obligatorios"
      });
    }

    const nuevoCliente = await Cliente.create({
      nombre,
      telefono,
      direccion
    });

    return res.status(201).json({
      success: true,
      message: "Cliente registrado correctamente",
      data: nuevoCliente
    });
  } catch (error) {
    console.error("Error al guardar cliente:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const obtenerCliente = async (req, res) => {
  try {
    const { id } = req.params;

    const cliente = await Cliente.findByPk(id);

    if (!cliente) {
      return res.status(404).json({
        success: false,
        message: "Cliente no encontrado"
      });
    }

    return res.status(200).json({
      success: true,
      message: "Cliente obtenido correctamente",
      data: cliente
    });
  } catch (error) {
    console.error("Error al obtener cliente:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const actualizarCliente = async (req, res) => {
  try {
    const { id } = req.params;
    const { nombre, telefono, direccion } = req.body;

    const cliente = await Cliente.findByPk(id);

    if (!cliente) {
      return res.status(404).json({
        success: false,
        message: "Cliente no encontrado"
      });
    }

    cliente.nombre = nombre;
    cliente.telefono = telefono;
    cliente.direccion = direccion;

    await cliente.save();

    return res.status(200).json({
      success: true,
      message: "Cliente actualizado correctamente",
      data: cliente
    });
  } catch (error) {
    console.error("Error al actualizar cliente:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const eliminarCliente = async (req, res) => {
  try {
    const { id } = req.params;

    const cliente = await Cliente.findByPk(id);

    if (!cliente) {
      return res.status(404).json({
        success: false,
        message: "Cliente no encontrado"
      });
    }

    await cliente.destroy();

    return res.status(200).json({
      success: true,
      message: "Cliente eliminado correctamente"
    });
  } catch (error) {
    console.error("Error al eliminar cliente:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  guardarCliente,
  obtenerCliente,
  actualizarCliente,
  eliminarCliente
};