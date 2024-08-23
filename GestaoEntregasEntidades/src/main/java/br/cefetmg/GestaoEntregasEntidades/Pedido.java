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
public class Pedido {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome_produto;
    private int quantidade;
    private double valorUnitario;
    private double valorTotal;
    private String marca;
    private String formaPagamento;
    
    @Enumerated(EnumType.STRING) 
    private Status status;
     
    public enum Status {
        EM_PREPARAÇÃO("em preparação"), SAIU_PARA_ENTREGA("saiu para entrega"), ENTREGUE("entregue");
        
        private String status;
        
        Status(String status){
            this.status = status;
        }
        
        public String getStatus(){
            return status;
        }
        
        public void setStatus(String status){
            this.status = status;
        }
    }
    private Date data;
    
    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }

    public double getValorUnitario() {
        return valorUnitario;
    }

    public void setValorUnitario(double valorUnitario) {
        this.valorUnitario = valorUnitario;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public void setValorTotal(double valorTotal) {
        this.valorTotal = valorTotal;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getFormaPagamento() {
        return formaPagamento;
    }

    public void setFormaPagamento(String formaPagamento) {
        this.formaPagamento = formaPagamento;
    }

    public String getNomeProduto() {
        return nome_produto;
    }

    public void setNomeProduto(String nome_produto) {
        this.nome_produto = nome_produto;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
    
    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
