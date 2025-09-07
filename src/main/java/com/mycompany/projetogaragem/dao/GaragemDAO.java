/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetogaragem.dao;

import com.mycompany.projetogaragem.database.ConexaoDB;
import com.mycompany.projetogaragem.model.Garagem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author Aluno
 */
public class GaragemDAO {

    private Connection con;

    // LISTAR TODAS AS GARAGENS DO USUÁRIO
    public ArrayList<Garagem> retornaListaDeGaragens(int idUsuario) throws SQLException {
        con = ConexaoDB.getConexao();

        String sql = "SELECT * FROM garagens WHERE idUsuario = ?";
        ArrayList<Garagem> listaDeGaragens = new ArrayList<>();

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, idUsuario);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Garagem g = new Garagem();
                g.setId(rs.getInt("id")); // PK da garagem
                g.setNome(rs.getString("nome"));
                g.setPais(rs.getString("pais"));
                g.setEstado(rs.getString("estado"));
                g.setCidade(rs.getString("cidade"));
                g.setBairro(rs.getString("bairro"));
                g.setRua(rs.getString("rua"));
                g.setNumero(rs.getString("numero"));
                g.setCep(rs.getString("cep"));
                // se quiser, pode guardar o idUsuario também:
                // g.setIdUsuario(rs.getInt("idUsuario"));

                listaDeGaragens.add(g);
            }

        } catch (SQLException e) {
            System.out.println("ERRO ao listar garagens -> " + e);
        } finally {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexão fechada (listar garagens)");
            }
        }

        return listaDeGaragens;
    }

    // SALVAR UMA NOVA GARAGEM
    public void salvar(Garagem g, int idUsuario) throws SQLException {
        con = ConexaoDB.getConexao();

        String sql = "INSERT INTO garagens (nome, pais, estado, cidade, bairro, rua, numero, cep, idUsuario) " +
                     "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, g.getNome());
            ps.setString(2, g.getPais());
            ps.setString(3, g.getEstado());
            ps.setString(4, g.getCidade());
            ps.setString(5, g.getBairro());
            ps.setString(6, g.getRua());
            ps.setString(7, g.getNumero());
            ps.setString(8, g.getCep());
            ps.setInt(9, idUsuario);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("ERRO ao salvar garagem -> " + e);
        } finally {
            if (con != null && !con.isClosed()) {
                con.close();
                System.out.println("Conexão fechada (salvar garagem)");
            }
        }
    }
}

