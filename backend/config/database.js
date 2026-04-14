const { Sequelize } = require("sequelize");
const path = require("path");

const sequelize = new Sequelize({
  dialect: "sqlite",
  storage: path.join(__dirname, "../database/ventas_casa_casa.sqlite"),
  logging: false
});

module.exports = sequelize;