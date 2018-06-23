package com.example.juniorsantos.barberapp.Entidades;

import com.example.juniorsantos.barberapp.DAO.ConfiguracaoFirebase;
import com.example.juniorsantos.barberapp.core.DadosSingleton;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class Agendamento {


    private String idAgendamento; //OB
    private String idUsuario; //OB
    private String idBarbeiro; //OB
    private String idServ;
    private String nome;
    private String cliente;
    private String horario; //OB
    private String status; //OB
    private String barbeiro;
    private String date; //OB


    public Agendamento() {

    }

    public boolean salvar(Agendamento agendamento){
        try {

            DatabaseReference referenciaFirebase = ConfiguracaoFirebase.getFirebase();
            referenciaFirebase.child("agendamento").child(String.valueOf(getIdAgendamento())).setValue(this);

            referenciaFirebase = ConfiguracaoFirebase.getFirebase().child("agendamento");
            referenciaFirebase.child(agendamento.getNome()).setValue(agendamento);
            return true;

        }

        catch (Exception e) {
            e.printStackTrace();
            return false;

        }




    }


    @Exclude

    public Map<String, Object> toMap(){
        HashMap<String, Object> hashMapUsuario = new HashMap<>();

        hashMapUsuario.put("idAgendamento", getIdAgendamento());
        hashMapUsuario.put("idUsuario", getIdUsuario());
        hashMapUsuario.put("idBarbeiro", getIdBarbeiro());
        hashMapUsuario.put("idServ", getIdServ());
        hashMapUsuario.put("horario", getHorario());
        hashMapUsuario.put("status", getStatus());
        hashMapUsuario.put("date", getDate());

        return hashMapUsuario;

    }


    public String getIdAgendamento() {
        return idAgendamento;
    }

    public void setIdAgendamento(String idAgendamento) {
        this.idAgendamento = idAgendamento;
    }

    public String getIdUsuario() {
       // return DadosSingleton.getInstance().getUser().getNome().toString();
        return idUsuario;
    }

    public void setIdUsuario(String idUsuario) {
        this.idUsuario = idUsuario;
    }

    public String getIdBarbeiro() {
        return idBarbeiro;
    }

    public void setIdBarbeiro(String idBarbeiro) {
        this.idBarbeiro = idBarbeiro;
    }

    public String getIdServ() {
        return idServ;
    }

    public void setIdServ(String idServ) {
        this.idServ = idServ;
    }

    public String getCliente() {
        return DadosSingleton.getInstance().getUser().getEmail().toString();
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }



    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getHorario() {
        return horario;
    }
    public void setHorario(String serviço) {
        this.horario = serviço;
    }

    public String getBarbeiro() {
        return barbeiro;
    }

    public void setBarbeiro(String barbeiro) {

        this.barbeiro = barbeiro;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public HashMap<String,String> toFirebaseObject() {
        HashMap<String,String> produtos =  new HashMap<String,String>();
        produtos.put("nome", nome);
        produtos.put("horario", horario);
        produtos.put("date", date);

        return produtos;
    }


}