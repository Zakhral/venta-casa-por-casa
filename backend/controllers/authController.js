const Usuario = require("../models/Usuario");

const login = async (req, res) => {
  try {
    const { usuario, password } = req.body;

    if (!usuario || !password) {
      return res.status(400).json({
        success: false,
        message: "Debes enviar usuario y contraseña"
      });
    }

    const usuarioEncontrado = await Usuario.findOne({
      where: { usuario, password }
    });

    if (!usuarioEncontrado) {
      return res.status(401).json({
        success: false,
        message: "Usuario o contraseña incorrectos"
      });
    }

    return res.status(200).json({
      success: true,
      message: "Inicio de sesión correcto",
      data: {
        id: usuarioEncontrado.id,
        usuario: usuarioEncontrado.usuario
      }
    });

  } catch (error) {
    console.error("Error en login:", error);
    return res.status(500).json({
      success: false,
      message: "Error interno del servidor"
    });
  }
};

module.exports = {
  login
};