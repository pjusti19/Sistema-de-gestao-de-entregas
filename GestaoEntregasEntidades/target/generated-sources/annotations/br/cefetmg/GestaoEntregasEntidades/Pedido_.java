package br.cefetmg.GestaoEntregasEntidades;

import br.cefetmg.GestaoEntregasEntidades.Pedido.Status;
import java.util.Date;
import javax.annotation.processing.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="org.eclipse.persistence.internal.jpa.modelgen.CanonicalModelProcessor", date="2024-08-23T00:12:38", comments="EclipseLink-2.7.12.v20230209-rNA")
@StaticMetamodel(Pedido.class)
public class Pedido_ { 

    public static volatile SingularAttribute<Pedido, String> formaPagamento;
    public static volatile SingularAttribute<Pedido, String> marca;
    public static volatile SingularAttribute<Pedido, String> nome_produto;
    public static volatile SingularAttribute<Pedido, Date> data;
    public static volatile SingularAttribute<Pedido, Double> valorTotal;
    public static volatile SingularAttribute<Pedido, Integer> id;
    public static volatile SingularAttribute<Pedido, Integer> quantidade;
    public static volatile SingularAttribute<Pedido, Double> valorUnitario;
    public static volatile SingularAttribute<Pedido, Status> status;

}