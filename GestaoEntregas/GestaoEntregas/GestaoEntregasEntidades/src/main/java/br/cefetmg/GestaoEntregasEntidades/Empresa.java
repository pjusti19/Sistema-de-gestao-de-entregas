/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.cefetmg.GestaoEntregasEntidades;

import javax.persistence.*;
/**
 *
 * @author Aluno
 */
@Entity
public class Empresa {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private String cnpj;
    private String cpf;
    private double PorcentagemComissaoEntregador;

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public double getPorcentagemComissaoEntregador() {
        return PorcentagemComissaoEntregador;
    }

    public void setPorcentagemComissaoEntregador(double PorcentagemComissaoEntregador) {
        this.PorcentagemComissaoEntregador = PorcentagemComissaoEntregador;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    
}
