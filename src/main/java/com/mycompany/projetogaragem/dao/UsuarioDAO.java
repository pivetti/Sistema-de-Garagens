/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.projetogaragem.dao;

import com.mycompany.projetogaragem.database.ConexaoDB;
import com.mycompany.projetogaragem.model.Usuario;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Aluno
 */
public class UsuarioDAO {
    private boolean usuarioLogado = false;
    private Connection con;
    private int id = 0;
    
    public boolean checarUsuario (String email, String senha) throws SQLException {
        con = ConexaoDB.getConexao();
        
        //criando sql
        String sql = "SELECT id, nome, email, senha "+
                "FROM usuarios WHERE email = ? AND "+
                "senha = ?";
        
        try {
            //injetando sql
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                usuarioLogado = true;
            }
        } catch(SQLException e) {
            System.out.println("ERRO: Tabela " + e + " não encontrada.");
        } finally {
            con.close();
            System.out.println("Encerrando conexão.");
        }
        return usuarioLogado;
    }
    
    public int retornarId(String email, String senha) throws SQLException {
        con = ConexaoDB.getConexao();
        String sql = "SELECT id FROM usuarios WHERE email = ? AND senha = ?";
                
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, senha);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                id = rs.getInt("id");
            }
        } catch (SQLException e) {
            System.out.println("ERRO ao retornar id -> " + e);
        } finally {
            con.close();
            System.out.println("Conexao encerrada (buscar id usuario");
        }
        return id;
    }
    
    public void salvarUsuario(Usuario u) throws SQLException{
        con = ConexaoDB.getConexao();
        
        String sql = "INSERT INTO usuarios(id, nome, email, senha) VALUES (?,?,?,?)";
        
        try {
            PreparedStatement ps = con.prepareStatement(sql);
            ps.setInt(1, u.getId());
            ps.setString(2, u.getNome());
            ps.setString(3, u.getEmail());
            ps.setString(4, u.getSenha());
            ps.executeUpdate();
            
        } catch(SQLException e){
            System.out.println("ERRO: nao foi possivel salvar no banco");
        } finally {
            con.close();
            System.out.println("Conexao com o DATABASE encerrada. (Cadastrar Usuario");
        }
    }
}
