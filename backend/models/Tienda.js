const { DataTypes } = require("sequelize");
const sequelize = require("../config/database");

const Tienda = sequelize.define("Tienda", {
  id: {
    type: DataTypes.INTEGER,
    autoIncrement: true,
    primaryKey: true
  },
  nombre_tienda: {
    type: DataTypes.STRING,
    allowNull: false
  },
  propietario: {
    type: DataTypes.STRING,
    allowNull: false
  },
  telefono: {
    type: DataTypes.STRING,
    allowNull: false
  },
  direccion: {
    type: DataTypes.STRING,
    allowNull: false
  },
  descripcion: {
    type: DataTypes.STRING,
    allowNull: false
  }
}, {
  tableName: "tienda",
  timestamps: false
});

module.exports = Tienda;