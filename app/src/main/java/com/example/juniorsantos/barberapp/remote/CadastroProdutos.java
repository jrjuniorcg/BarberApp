package com.example.juniorsantos.barberapp.remote;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.juniorsantos.barberapp.Adapter.SpinnerAdapter;
import com.example.juniorsantos.barberapp.DAO.ConfiguracaoFirebase;
import com.example.juniorsantos.barberapp.Entidades.Agendamento;
import com.example.juniorsantos.barberapp.Entidades.Serv;
import com.example.juniorsantos.barberapp.Entidades.Usuarios;
import com.example.juniorsantos.barberapp.Helper.Base64Custom;
import com.example.juniorsantos.barberapp.R;
import com.example.juniorsantos.barberapp.TelaMenu;
import com.example.juniorsantos.barberapp.core.DadosSingleton;
import com.example.juniorsantos.barberapp.dataloader.ProdutoDataloader;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;


public class CadastroProdutos extends AppCompatActivity implements DatePickerDialog.OnDateSetListener{

    private SpinnerAdapter spinnerAdapter;
    private Spinner spinnerc, spinnerd;
    private ValueEventListener valueEventListenerProdutos;
    private Button btnGravar, btnVoltarTelaInicial, edtHorario, edtServiço, btnTeste;
    private Agendamento agendamento;
    private Serv serv;
    private EditText edtTeste, edtTeste2;
    private Agendamento agendamento2;
    private Usuarios usuarios;
    private AlertDialog alerta;
    private DatabaseReference firebase;
    private int day,month,year,hour,minute;
    String nomeRecurso;
    private int dayFinal,monthFinal,yearFinal,hourFinal,minuteFinal;
    CalendarView calendario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro_produtos);







        edtHorario = (Button) findViewById(R.id.edtHorario);
        edtServiço = (Button) findViewById(R.id.edtValor);
        btnGravar = (Button) findViewById(R.id.btnGravar);
        spinnerc = (Spinner) findViewById(R.id.spinnerview2);
        spinnerd = (Spinner) findViewById(R.id.spinnerview1);
        btnVoltarTelaInicial = (Button) findViewById(R.id.btnVoltarTelaInicial);


        firebase = ConfiguracaoFirebase.getFirebase().child("barbeiros");



        valueEventListenerProdutos = new ProdutoDataloader(new ProdutoDataloader.ProdutoDataListener() {
            @Override
            public void onProdutoLoaded(List<Agendamento> teste) {

                List<Agendamento> padrao = new ArrayList<Agendamento>(); //CRIEI UMA NOVA LISTA AQUI
                List<Agendamento> serv = new ArrayList<Agendamento>();

                Agendamento serv1 = new Agendamento();
                Agendamento serv2 = new Agendamento();
                Agendamento serv3 = new Agendamento();

                Agendamento agendamento1 = new Agendamento(); //CRIEI UM NOVO OBJETO

                serv1.setNome("Selecione o Serviço");
                serv2.setNome("Barba");
                serv3.setNome("Cabelo");
                serv.add(serv1);
                serv.add(serv2);
                serv.add(serv3);


                agendamento1.setNome("Selecione o Barbeiro"); //SETEI OS DADOS;
                padrao.add(agendamento1); //ADCIONEI O DADO NA LISTA

                padrao.addAll(teste); //JUNTEI AS DUAS LISTAS


                spinnerd.setAdapter(new SpinnerAdapter(serv)); //CARREGA O SPINNER DE SERVIÇO


                spinnerc.setAdapter(new SpinnerAdapter(padrao)); //CARREGA O SPINNER DE BARBEIROS




            }



        });

        /* //METODO QUE ADCIONA SERVIÇOS
        btnTeste.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                serv = new Serv();
             String idenficadorServ = Base64Custom.codificarBase64(serv.getNomeServ());
              agendamento.setIdServ(idenficadorServ);
                serv.setNomeServ(edtTeste.getText().toString());
                serv.setIdServ(edtTeste2.getText().toString());
                if (serv.salvarServ(serv) == true){
                    Toast.makeText(CadastroProdutos.this, "Serviço salvo com Sucesso", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        });
         */
























        edtHorario.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Calendar c = Calendar.getInstance();
                year = c.get(Calendar.YEAR);
                month = c.get(Calendar.MONTH);
                day = c.get(Calendar.DAY_OF_MONTH);


                DatePickerDialog datePickerDialog = new DatePickerDialog(CadastroProdutos.this , CadastroProdutos.this , year,month,day);
                datePickerDialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
                datePickerDialog.show();

            }



        });

        edtServiço.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Calendar mcurrentTime = Calendar.getInstance();
                int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                int minute = mcurrentTime.get(Calendar.MINUTE);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(CadastroProdutos.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        edtServiço.setText( selectedHour + ":" + selectedMinute);
                    }
                }, hour, minute, true);//Yes 24 hour time
                mTimePicker.setTitle("Select Time");
                mTimePicker.show();
            }
        });






        btnGravar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                FirebaseDatabase.getInstance().getReference("agendamento").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        List<Agendamento> listaAgedametos = new ArrayList();
                        for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                             Agendamento agendamento = postSnapshot.getValue(Agendamento.class);
                            listaAgedametos.add(agendamento);
                        }



                        boolean horarioLivre = true;



                        for(Agendamento agendamento : listaAgedametos ){
                            if(agendamento.getHorario().contains(edtServiço.getText().toString())){
                                horarioLivre = false;
                            }
                        }


                        if (!horarioLivre){
                            Toast.makeText(CadastroProdutos.this, "Horário não disponível! Tente outro Horário",    Toast.LENGTH_LONG).show();
                        }

                        else{
                            agendamento = new Agendamento();
                            String nomeServ = ((Agendamento) spinnerd.getSelectedItem()).getNome();
                            agendamento.setNome(nomeServ);
                            agendamento.setHorario(edtServiço.getText().toString());
                            agendamento.setDate(edtHorario.getText().toString());
                            String nomeBarbeiro = ((Agendamento) spinnerc.getSelectedItem()).getNome();
                            agendamento.setBarbeiro(nomeBarbeiro);
                            String idenficadorUsuario = Base64Custom.codificarBase64(DadosSingleton.getInstance().getUser().getEmail());
                            agendamento.setIdUsuario(idenficadorUsuario);
                            agendamento.setStatus("AGENDADO");
                            String nomeUsuario = DadosSingleton.getInstance().getUser().getNome();
                            agendamento.setNomeCliente(nomeUsuario);
                            String imageUsuario = DadosSingleton.getInstance().getUser().getImg();
                            agendamento.setImageCliente(imageUsuario);
                            agendamento.setImgStatus("https://image.freepik.com/fotos-gratis/blur-abstract-bokeh-light-background-preto-e-branco-tom-monocromatico_7190-592.jpg");
                            String identificadorAgend = Base64Custom.codificarBase64(agendamento.getHorario());
                            agendamento.setIdAgendamento(identificadorAgend);
                            agendar();
                        }




                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




/*
                FirebaseDatabase.getInstance().getReference("agendamento").child(agendamento.getIdAgendamento()).child("horario").addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        if (dataSnapshot.exists()){
                            Toast.makeText(CadastroProdutos.this, "Horário não disponível! Tente outro Horário",    Toast.LENGTH_LONG).show();
                        }

                        else{
                            agendar();
                        }

                    }
                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });




                agendamento = new Agendamento();

                String nomeServ = ((Agendamento) spinnerd.getSelectedItem()).getNome();
                agendamento.setNome(nomeServ);
                agendamento.setHorario(edtServiço.getText().toString());
                agendamento.setDate(edtHorario.getText().toString());
                String nomeBarbeiro = ((Agendamento) spinnerc.getSelectedItem()).getNome();
                agendamento.setBarbeiro(nomeBarbeiro);
                String idenficadorUsuario = Base64Custom.codificarBase64(DadosSingleton.getInstance().getUser().getEmail());
                agendamento.setIdUsuario(idenficadorUsuario);
                agendamento.setStatus("AGENDADO");
                String nomeUsuario = DadosSingleton.getInstance().getUser().getNome();
                agendamento.setNomeCliente(nomeUsuario);
                String imageUsuario = DadosSingleton.getInstance().getUser().getImg();
                agendamento.setImageCliente(imageUsuario);
                agendamento.setImgStatus("https://image.freepik.com/fotos-gratis/blur-abstract-bokeh-light-background-preto-e-branco-tom-monocromatico_7190-592.jpg");
                String identificadorAgend = Base64Custom.codificarBase64(agendamento.getHorario());
                agendamento.setIdAgendamento(identificadorAgend);




*/





            }
        });









        btnVoltarTelaInicial.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                voltarTelaInicial();
            }
        });
    }



    @Override
    protected void onStop() {
        super.onStop();
        firebase.removeEventListener(valueEventListenerProdutos);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerProdutos);
    }



    private void voltarTelaInicial() {
        Intent intent = new Intent(CadastroProdutos.this, TelaMenu.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {


        if (month > 9){
            String date1 = dayOfMonth + "/" + (month + 1) + "/" + year;
            edtHorario.setText(date1);
        }

        else{

            String date = dayOfMonth + "/" + "0"+(month + 1) + "/" + year;
            edtHorario.setText(date);

        }


    }

    private void agendar() {
        //Cria o gerador do AlertDialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //define o titulo
        builder.setTitle("Agendamento");
        //define a mensagem
        builder.setMessage("Data:  " + agendamento.getDate() + " \n\rServiço: " + agendamento.getNome() + "\n\rBarbeiro: " + agendamento.getBarbeiro() + "\n\rHorário: " + agendamento.getHorario());

        //define um botão como positivo
        builder.setPositiveButton("Agendar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {

                if (agendamento.salvar(agendamento) == true) {

                    Toast.makeText(CadastroProdutos.this, "Agendamento com Sucesso", Toast.LENGTH_SHORT).show();
                }




                //  if (agendamento.getDate() == ""  || agendamento.getNome() == "Selecione o Serviço"  || agendamento.getBarbeiro() == "Selecione o Barbeiro" ||
                //                      agendamento.getHorario() == ""){

                //               Toast.makeText(CadastroProdutos.this, "Algum dado não foi preenchido!", Toast.LENGTH_LONG).show();
                //           }
                //         else{

                //           Toast.makeText(CadastroProdutos.this, "Agendamento com Sucesso", Toast.LENGTH_SHORT).show();

                //         }






            }
        });
        //define um botão como negativo.
        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                Toast.makeText(CadastroProdutos.this, "", Toast.LENGTH_SHORT).show();
            }
        });
        //cria o AlertDialog
        alerta = builder.create();
        //Exibe
        alerta.show();
    }


}