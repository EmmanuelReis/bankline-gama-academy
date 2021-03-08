package com.app.gamaacademy.cabrasdoagrest.bankline.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.app.gamaacademy.cabrasdoagrest.bankline.models.Usuario;

public class UsuarioRepository implements DefaultRepository<Usuario> {

	private Connection connection;

	private static final String INSERT_SQL = "INSERT INTO bankline.usuario (login,senha,nome,cpf) VALUES (?,?,?,?)";
	private static final String UPDATE_SQL = "UPDATE bankline.usuario SET senha=?, nome=?, cpf=? WHERE login=?";
	private static final String DELETE_SQL = "DELETE FROM bankline.usuario WHERE id=?";

	private static final String SELECT_ALL_FIELDS_BY_ID_SQL = "SELECT id, login, senha, nome, cpf FROM bankline.usuario WHERE id = ?";
	private static final String SELECT_ALL_FIELDS_SQL = "SELECT id, login, senha, nome, cpf FROM bankline.usuario";

	public UsuarioRepository() {
		try {
			connection = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/bankline?useTimezone=true&serverTimezone=UTC", "root", "root");
			System.out.println("Conectou");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public int salvar(Usuario entity) {

		try {
			PreparedStatement statement = connection.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS);

			statement.setString(1, entity.getLogin());
			statement.setString(2, entity.getSenha());
			statement.setString(3, entity.getNome());
			statement.setString(4, entity.getCpf());

			int affectedRows = statement.executeUpdate();

			if (affectedRows == 0)
				throw new SQLException("Usuário não foi criado corretamente.");

			try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
				if (generatedKeys.next())
					entity.setId(generatedKeys.getInt(1));
				else
					throw new SQLException("Usuário não foi criado corretamente. Não foi possível obter id.");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return entity.getId();
	}

	@Override
	public void alterar(int id, Usuario entity) {
		try {
			PreparedStatement statement = connection.prepareStatement(UPDATE_SQL);

			statement.setString(1, entity.getSenha());
			statement.setString(2, entity.getNome());
			statement.setString(3, entity.getCpf());
			statement.setDouble(4, id);

			statement.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void excluir(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(DELETE_SQL);
			statement.setInt(1, id);

			statement.execute();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	@Override
	public Usuario buscaPorId(int id) {
		try {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FIELDS_BY_ID_SQL);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Usuario u = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"),
						rs.getString("nome"), rs.getString("cpf"));
				return u;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public List<Usuario> obterTodos() {
		List<Usuario> usuarios = new ArrayList<Usuario>();

		try {
			PreparedStatement statement = connection.prepareStatement(SELECT_ALL_FIELDS_SQL);
			ResultSet rs = statement.executeQuery();

			while (rs.next()) {
				Usuario u = new Usuario(rs.getInt("id"), rs.getString("login"), rs.getString("senha"),
						rs.getString("nome"), rs.getString("cpf"));
				usuarios.add(u);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return usuarios;
	}

}
