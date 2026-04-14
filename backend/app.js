const express = require("express");
const cors = require("cors");
const sequelize = require("./config/database");
const Usuario = require("./models/Usuario");
const authRoutes = require("./routes/authRoutes");
const tiendaRoutes = require("./routes/tiendaRoutes");
const productoRoutes = require("./routes/productoRoutes");
const clienteRoutes = require("./routes/clienteRoutes");
const ventaRoutes = require("./routes/ventaRoutes");
const reporteRoutes = require("./routes/reporteRoutes");

const app = express();
const PORT = 3000;

app.use(cors());
app.use(express.json());

app.get("/", (req, res) => {
  res.send("Backend de Venta Casa por Casa funcionando correctamente");
});

app.use("/api/auth", authRoutes);
app.use("/api/tienda", tiendaRoutes);
app.use("/api/productos", productoRoutes);
app.use("/api/clientes", clienteRoutes);
app.use("/api/ventas", ventaRoutes);
app.use("/api/reportes", reporteRoutes);

const iniciarServidor = async () => {
  try {
    await sequelize.authenticate();
    console.log("Conexión a SQLite establecida correctamente.");

    await sequelize.sync();
    console.log("Base de datos sincronizada correctamente.");

    const adminExistente = await Usuario.findOne({
      where: { usuario: "admin" }
    });

    if (!adminExistente) {
      await Usuario.create({
        usuario: "admin",
        password: "admin123"
      });
      console.log("Usuario admin creado correctamente.");
    } else {
      console.log("El usuario admin ya existe.");
    }

    app.listen(PORT, "0.0.0.0", () => {
      console.log(`Servidor corriendo en http://localhost:${PORT}`);
    });

  } catch (error) {
    console.error("Error al iniciar el servidor:", error);
  }
};

iniciarServidor();