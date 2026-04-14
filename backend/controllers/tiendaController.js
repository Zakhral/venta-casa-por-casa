const Tienda = require("../models/Tienda");

const guardarTienda = async (req, res) => {
  try {
    const { nombre_tienda, propietario, telefono, direccion, descripcion } = req.body;

    if (!nombre_tienda || !propietario || !telefono || !direccion || !descripcion) {
      return res.status(400).json({
        success: false,
        message: "Todos los campos de la tienda son obligatorios"
      });
    }

    const tiendaExistente = await Tienda.findOne();

    if (tiendaExistente) {
      return res.status(400).json({
        success: false,
        message: "Ya existe una tienda registrada. Use actualizar."
      });
    }

    const nuevaTienda = await Tienda.create({
      nombre_tienda,
      propietario,
      telefono,
      direccion,
      descripcion
    });

    return res.status(201).json({
      success: true,
      message: "Tienda registrada correctamente",
      data: nuevaTienda
    });

  } catch (error) {
    console.error("Error al guardar tienda:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const obtenerTienda = async (req, res) => {
  try {
    const tienda = await Tienda.findOne();

    if (!tienda) {
      return res.status(404).json({
        success: false,
        message: "No hay datos de tienda registrados"
      });
    }

    return res.status(200).json({
      success: true,
      message: "Tienda obtenida correctamente",
      data: tienda
    });

  } catch (error) {
    console.error("Error al obtener tienda:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

const actualizarTienda = async (req, res) => {
  try {
    const { nombre_tienda, propietario, telefono, direccion, descripcion } = req.body;

    if (!nombre_tienda || !propietario || !telefono || !direccion || !descripcion) {
      return res.status(400).json({
        success: false,
        message: "Todos los campos de la tienda son obligatorios"
      });
    }

    const tienda = await Tienda.findOne();

    if (!tienda) {
      return res.status(404).json({
        success: false,
        message: "No existe una tienda para actualizar"
      });
    }

    tienda.nombre_tienda = nombre_tienda;
    tienda.propietario = propietario;
    tienda.telefono = telefono;
    tienda.direccion = direccion;
    tienda.descripcion = descripcion;

    await tienda.save();

    return res.status(200).json({
      success: true,
      message: "Tienda actualizada correctamente",
      data: tienda
    });

  } catch (error) {
    console.error("Error al actualizar tienda:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  guardarTienda,
  obtenerTienda,
  actualizarTienda
};