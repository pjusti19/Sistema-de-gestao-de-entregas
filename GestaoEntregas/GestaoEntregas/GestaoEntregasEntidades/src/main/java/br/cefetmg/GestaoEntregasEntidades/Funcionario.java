/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasEntidades;

import java.util.Date;
import javax.persistence.*;
/**
 *
 * @author Aluno
 */
@Entity
public class Funcionario {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String senha;
    private String telefone;
    private Date dataComissao;
    
    @Enumerated(EnumType.STRING)
    private String perfil;
    
    private enum Perfil{
        ADMINISTRADOR("administrador"), ATENDENTE("atendente"), ENTREGADOR("entregador");
        
        private String perfil;
        
        Perfil(String perfil){
            this.perfil = perfil;
        }
        
        public String getPerfil(){
            return perfil;
        }
        
        public void setPerfil(String perfil){
            this.perfil = perfil;
        }
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getDataComissao() {
        return dataComissao;
    }

    public void setDataComissao(Date dataComissao) {
        this.dataComissao = dataComissao;
    }
    
    public String getPerfil(){
            return perfil;
        }
        
        public void setPerfil(String perfil){
            this.perfil = perfil;
        }
}
