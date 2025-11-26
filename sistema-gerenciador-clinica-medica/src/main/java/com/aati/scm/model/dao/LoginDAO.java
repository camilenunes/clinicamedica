package com.aati.scm.model.dao;

import com.aati.scm.model.entity.Login;
import com.aati.scm.model.enums.PapelUsario;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LoginDAO {

    private Connection conn;

    public LoginDAO(Connection conn) {
        this.conn = conn;
    }

    // ----------------------------------------------------
    // INSERT
    // ----------------------------------------------------
    public void inserir(Login login) throws SQLException {
        String sql = "INSERT INTO T_SCM_USUARIOS "
                + "(username, senha_hash, nome_completo, papel, ativo) "
                + "VALUES (?, ?, ?, ?, ?)";

        PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        stmt.setString(1, login.getUsername());
        stmt.setString(2, login.getSenha()); // já deve vir hash
        stmt.setString(3, login.getNomeCompleto());
        stmt.setString(4, login.getPapel().getValue());
        stmt.setBoolean(5, login.isAtivo());

        stmt.executeUpdate();

        ResultSet rs = stmt.getGeneratedKeys();
        if (rs.next()) login.setId(rs.getInt(1));

        stmt.close();
    }

    // ----------------------------------------------------
    // BUSCAR POR USERNAME
    // ----------------------------------------------------
    public Login buscarPorUsername(String username) throws SQLException {
        String sql = "SELECT * FROM T_SCM_USUARIOS WHERE username = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setString(1, username);

        ResultSet rs = stmt.executeQuery();

        Login login = null;

        if (rs.next()) {
            login = mapearResultado(rs);
        }

        rs.close();
        stmt.close();
        return login;
    }

    // ----------------------------------------------------
    // BUSCAR POR ID
    // ----------------------------------------------------
    public Login buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM T_SCM_USUARIOS WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        ResultSet rs = stmt.executeQuery();
        Login login = null;

        if (rs.next()) {
            login = mapearResultado(rs);
        }

        rs.close();
        stmt.close();
        return login;
    }

    // ----------------------------------------------------
    // LISTAR TODOS
    // ----------------------------------------------------
    public List<Login> listarTodos() throws SQLException {
        String sql = "SELECT * FROM T_SCM_USUARIOS ORDER BY nome_completo";

        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery();

        List<Login> lista = new ArrayList<>();

        while (rs.next()) {
            lista.add(mapearResultado(rs));
        }

        rs.close();
        stmt.close();
        return lista;
    }

    // ----------------------------------------------------
    // ATUALIZAR
    // ----------------------------------------------------
    public boolean atualizar(Login login) throws SQLException {
        String sql = """
            UPDATE T_SCM_USUARIOS
            SET username = ?, senha_hash = ?, nome_completo = ?, papel = ?, ativo = ?
            WHERE id = ?
        """;

        PreparedStatement stmt = conn.prepareStatement(sql);

        stmt.setString(1, login.getUsername());
        stmt.setString(2, login.getSenha());
        stmt.setString(3, login.getNomeCompleto());
        stmt.setString(4, login.getPapel().getValue());
        stmt.setBoolean(5, login.isAtivo());
        stmt.setInt(6, login.getId());

        int linhas = stmt.executeUpdate();
        stmt.close();

        return linhas > 0;
    }

    // ----------------------------------------------------
    // DESATIVAR USUÁRIO
    // ----------------------------------------------------
    public boolean desativar(int id) throws SQLException {
        String sql = "UPDATE T_SCM_USUARIOS SET ativo = 0 WHERE id = ?";

        PreparedStatement stmt = conn.prepareStatement(sql);
        stmt.setInt(1, id);

        int linhas = stmt.executeUpdate();
        stmt.close();

        return linhas > 0;
    }

    // ----------------------------------------------------
    // MAPEAR RESULTSET → OBJETO LOGIN
    // ----------------------------------------------------
    private Login mapearResultado(ResultSet rs) throws SQLException {
        Login login = new Login();

        login.setId(rs.getInt("id"));
        login.setUsername(rs.getString("username"));
        login.setSenha(rs.getString("senha_hash"));
        login.setNomeCompleto(rs.getString("nome_completo"));

        String papelStr = rs.getString("papel");
        login.setPapel(PapelUsario.valueOf(papelStr));

        login.setAtivo(rs.getBoolean("ativo"));

        return login;
    }
}
